package org.coolpot.compiler;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.node.irnode.MemberNode;
import org.coolpot.compiler.node.irnode.OpNode;
import org.coolpot.compiler.node.irnode.PushNode;
import org.coolpot.compiler.node.statment.ClassNode;
import org.coolpot.compiler.node.statment.DefNode;
import org.coolpot.compiler.node.statment.FuncNode;
import org.coolpot.compiler.parser.BlockParser;
import org.coolpot.compiler.parser.SubParser;
import org.coolpot.compiler.tokens.NodeBufferToken;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.compiler.tokens.UnaryBufferToken;
import org.coolpot.runtime.obj.*;
import org.coolpot.util.MetaConfig;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ExpressionParser implements SubParser {
    Parser parser;
    List<Token> tokens;
    Token buffer = null;
    int index;

    public ExpressionParser(Parser parser,List<Token> tokens){
        this.parser = parser;
        this.tokens = tokens;
        this.index = 0;
    }

    private Token getToken() {
        Token t;
        if (buffer == null) {
            if (index >= tokens.size()) return null;
            t = tokens.get(index);
            index += 1;
        } else {
            t = buffer;
            buffer = null;
        }
        return t;
    }

    public List<Token> suffix(SymbolTable table){
        boolean isUnary = true;
        Deque<Token> op_stack = new LinkedList<>();
        List<Token> suffixList = new LinkedList<>();
        Token token = null;

        try {
            while (true) {
                token = getToken();
                if (token == null) break;

                if(isOperator(token)){
                    if (isUnaryOperator(token, isUnary)) {
                        op_stack.push(new UnaryBufferToken(token));
                    } else {
                        if (op_stack.isEmpty() || ("(".equals(op_stack.peek().getData()) && op_stack.peek().getType().equals(Token.Type.LP)) || priority(token) > priority(op_stack.peek())) {
                            op_stack.push(token);
                        } else {
                            while (!op_stack.isEmpty() && !("(".equals(op_stack.peek().getData()) && op_stack.peek().getType().equals(Token.Type.LP))) {
                                if (priority(token) <= priority(op_stack.peek())) {
                                    suffixList.add(op_stack.pop());
                                }
                                if (op_stack.isEmpty()) break;
                                if (priority(token) > priority(op_stack.peek())) break;
                            }
                            op_stack.push(token);
                        }
                    }
                    isUnary = true;
                }else if(isNumber(token)) {
                    if (isUnary) {
                        isUnary = false;
                    }
                    suffixList.add(token);
                }else if (token.getType().equals(Token.Type.KEY)) {
                    if (isUnary) {
                        isUnary = false;
                    }
                    switch (token.getData()) {
                        case "true", "false", "null", "new" -> suffixList.add(token);
                        case "func" -> {
                            ASTNode node = parserFuncArg(table);
                            table.createNewScope(new SymbolTable.Scope("func<expression>:", SymbolTable.ScopeType.FUNC));
                            List<Token> block = new ArrayList<>();
                            token = getToken();
                            int i = 1;
                            if (!(token.getType().equals(Token.Type.LP) && token.getData().equals("{")))
                                throw new SyntaxException(token,"Missing function body.");
                            try {
                                do {
                                    token = getToken();
                                    if (token.getType().equals(Token.Type.LP) && token.getData().equals("{")) i += 1;
                                    if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) i -= 1;
                                    if (i == 0) break;
                                    block.add(token);
                                } while (true);
                            } catch (NullPointerException e) {
                                throw new SyntaxException(token,"'}' expected.");
                            }
                            suffixList.add(new NodeBufferToken(new FuncNode(new GroupNode(node,subParser(table,block)))));
                            table.popScope();
                        }
                        case "class" -> {
                            List<Token> class_body = new ArrayList<>();
                            token = getToken();
                            int i = 1;
                            if (!(token.getType().equals(Token.Type.LP) && token.getData().equals("{")))
                                throw new SyntaxException(token,"Missing class body.");
                            try {
                                do {
                                    token = getToken();
                                    if (token.getType().equals(Token.Type.LP) && token.getData().equals("{")) i += 1;
                                    if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) i -= 1;
                                    if (i == 0) break;
                                    class_body.add(token);
                                } while (true);
                            } catch (NullPointerException e) {
                                throw new SyntaxException(token,"'}' expected.");
                            }

                            table.createNewScope(new SymbolTable.Scope("class:<expression>", SymbolTable.ScopeType.CLASS));
                            suffixList.add(new NodeBufferToken(new ClassNode(subParser(table,class_body))));
                            table.popScope();
                        }
                        default -> throw new SyntaxException(token, "Illegal keywords.");
                    }
                }else if ("(".equals(token.getData()) && token.getType().equals(Token.Type.LP)) {
                    op_stack.push(token);
                } else if (")".equals(token.getData()) && token.getType().equals(Token.Type.LR)) {
                    while (!op_stack.isEmpty()) {
                        if ("(".equals(op_stack.peek().getData()) && op_stack.peek().getType().equals(Token.Type.LP)) {
                            op_stack.pop();
                            break;
                        } else {
                            suffixList.add(op_stack.pop());
                        }
                    }
                }else {
                    if(isUnary){
                        isUnary = false;
                    }

                    if(token.getType().equals(Token.Type.NAM)){
                        Token nam = token;
                        token = getToken();
                        if(token.getType().equals(Token.Type.LP)&&token.getData().equals("(")){
                            ASTNode node = parserFuncArg(table);
                            suffixList.add(new NodeBufferToken(new GroupNode(node,new PushNode(new StamonIdentifier(nam.getData())),new OpNode(OpNode.NodeType.CALL))));
                        }else{
                            suffixList.add(nam);
                            buffer = token;
                        }
                    }
                }
            }
        }catch (NullPointerException e){
            throw new SyntaxException(token,"Illegal combination of expressions.");
        }

        while (!op_stack.isEmpty()) {
            suffixList.add(op_stack.pop());
        }

        return suffixList;
    }

    public ASTNode calculate(List<Token> suffix){
        List<ASTNode> nodes = new ArrayList<>();

        for(Token token : suffix){
            switch (token.getType()){
                case NUM,BIN,B16 -> nodes.add(new PushNode(new StamonInteger(Integer.parseInt(token.getData()))));
                case DBL -> nodes.add(new PushNode(new StamonDouble(Double.parseDouble(token.getData()))));
                case STR -> nodes.add(new PushNode(new StamonString(token.getData())));
                case SEM -> {
                    switch (token.getData()){
                        case "+" -> nodes.add(new OpNode(OpNode.NodeType.ADD));
                        case "-" -> nodes.add(new OpNode(OpNode.NodeType.SUB));
                        case "*" -> nodes.add(new OpNode(OpNode.NodeType.MUL));
                        case "~" -> nodes.add(new OpNode(OpNode.NodeType.CPL));
                        case "/" -> nodes.add(new OpNode(OpNode.NodeType.DIV));
                        case "%" -> nodes.add(new OpNode(OpNode.NodeType.MOD));
                        case "<<" -> nodes.add(new OpNode(OpNode.NodeType.LSH));
                        case ">>" -> nodes.add(new OpNode(OpNode.NodeType.RSH));
                        case ">" -> nodes.add(new OpNode(OpNode.NodeType.BIG));
                        case "<" -> nodes.add(new OpNode(OpNode.NodeType.LESS));
                        case ">=" -> nodes.add(new OpNode(OpNode.NodeType.BEQ));
                        case "<=" -> nodes.add(new OpNode(OpNode.NodeType.LEQ));
                        case "==" -> nodes.add(new OpNode(OpNode.NodeType.EQU));
                        case "!=" -> nodes.add(new OpNode(OpNode.NodeType.IEQ));
                        case "&" -> nodes.add(new OpNode(OpNode.NodeType.BAND));
                        case "^" -> nodes.add(new OpNode(OpNode.NodeType.BXOR));
                        case "|" -> nodes.add(new OpNode(OpNode.NodeType.BOR));
                        case "&&" -> nodes.add(new OpNode(OpNode.NodeType.LAND));
                        case "||" -> nodes.add(new OpNode(OpNode.NodeType.LOR));
                        case "=" -> nodes.add(new OpNode(OpNode.NodeType.ASSIGN));
                        case "." -> nodes.add(new MemberNode());
                    }
                }
                case KEY -> {
                    switch (token.getData()){
                        case "true" -> nodes.add(new PushNode(new StamonBoolean(true)));
                        case "false" -> nodes.add(new PushNode(new StamonBoolean(false)));
                        case "null" ->nodes.add(new PushNode(StamonBase.value_null));
                        case "new" -> nodes.add(new OpNode(OpNode.NodeType.NEW));
                    }
                }
                case NBF -> nodes.add(((NodeBufferToken)token).getNode());
                case NAM -> nodes.add(new PushNode(new StamonIdentifier(token.getData())));
            }
        }

        return new GroupNode(nodes);
    }

    public ASTNode subParser(SymbolTable table,List<Token> tokens){
        return new BlockParser(parser.file,tokens).eval(table);
    }

    private static boolean isOperator(Token op) {
        if (op.getType() != Token.Type.SEM) return false;
        return MetaConfig.OP_DATA.contains(op.getData());
    }

    private static boolean isUnaryOperator(Token token, boolean isU) {
        if (token.getType() != Token.Type.SEM) return false;
        return isU && (token.getData().equals("-") || token.getData().equals("++") || token.getData().equals("--"));
    }

    public ASTNode parserFuncArg(SymbolTable table) {
        List<ASTNode> nodes = new ArrayList<>();
        Token token = getToken();
        if(token.getType().equals(Token.Type.LP) && token.getData().equals("(")){
            do{
                token = getToken();
                if(token.getType().equals(Token.Type.NAM)){
                    if(table.getThisScope().getInDefine().contains(token.getData()))
                        throw new SyntaxException(token,"Type is already defined.");
                    table.getThisScope().getInDefine().add(token.getData());
                    nodes.add(new DefNode(token.getData()));
                    token = getToken();
                    if(token.getType().equals(Token.Type.SEM)&&token.getData().equals(",")) {
                    }
                    else if(token.getType().equals(Token.Type.LR) && token.getData().equals(")")) break;
                    else throw new SyntaxException(token,"Illegal symbol in function define.");
                }else if(token.getType().equals(Token.Type.LR) && token.getData().equals(")")) {
                    break;
                }else throw new SyntaxException(token,"<identifier> expected.");
            }while (!(token.getType().equals(Token.Type.LR) && token.getData().equals(")")));
        }else throw new SyntaxException(token,"'(' expected.");
        return new GroupNode(nodes);
    }

    private static boolean isNumber(Token token){
        return switch (token.getType()){
            case NUM,DBL,STR,BIN,B16 -> true;
            default -> false;
        };
    }

    private static int priority(Token op) {
        if (op.getType() != Token.Type.SEM) return -1;

        return switch (op.getData()) {
            case "." -> 14;
            case "++", "--" -> 13;
            case "!","~" -> 12;
            case "*","/", "%" -> 11;
            case "+","-" -> 10;
            case "<<",">>" -> 9;
            case "<=",">=","<",">" -> 8;
            case "==","!=" -> 7;
            case "&" -> 6;
            case "^" -> 5;
            case "|" -> 4;
            case "&&" -> 3;
            case "||" -> 2;
            case "+=","-=","*=","/=","%=","&=","|=","^=",">>=","<<=","=" -> 1;
            case "," -> 0;
            default -> -1;
        };
    }

    @Override
    public Token getMatchToken() {
        return null;
    }

    @Override
    public ASTNode eval(SymbolTable table){
        return calculate(suffix(table));
    }
}
