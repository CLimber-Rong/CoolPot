package org.coolpot.compiler.parser;

import org.coolpot.compiler.ExpressionParser;
import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.node.irnode.PushNode;
import org.coolpot.compiler.node.statment.ArrayNode;
import org.coolpot.compiler.node.statment.DefNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.runtime.obj.StamonBase;
import org.coolpot.runtime.obj.StamonBoolean;
import org.coolpot.runtime.obj.StamonInteger;
import org.coolpot.runtime.obj.StamonNull;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DefParser implements SubParser {
    Token match;
    Parser parser;
    SourceFile file;

    public DefParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"def",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        Token token = parser.getToken();
        if(token.getType().equals(Token.Type.NAM)){
            if(!table.getThisScope().getInDefine().contains(token.getData())){
                table.getThisScope().getInDefine().add(token.getData());
                String name = token.getData();
                token = parser.getToken();
                if(token.getType().equals(Token.Type.SEM)&&token.getData().equals("=")) {
                    token = parser.getToken();
                    if(token.getType().equals(Token.Type.LP)&&token.getData().equals("[")){
                        int ibl = 1;
                        List<Token> ts = new ArrayList<>();
                        do {
                            token = parser.getToken();
                            if (token.getType().equals(Token.Type.LP) && token.getData().equals("[")) ibl += 1;
                            if (token.getType().equals(Token.Type.LR) && token.getData().equals("]")) ibl -= 1;
                            if (ibl <= 0) break;
                            ts.add(token);
                        } while (true);
                        ExpressionParser ep = new ExpressionParser(parser,ts);
                        return new DefNode(name,new GroupNode(new ArrayNode(ep.eval(table),ASTNode.empty)));
                    }else if(token.getType().equals(Token.Type.LP)&&token.getData().equals("{")){
                        boolean isend = true, isf = true;
                        List<ASTNode> b = new LinkedList<>();
                        do {
                            List<Token> tds = new LinkedList<>();
                            do {
                                token = parser.getToken();
                                if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) {
                                    isend = false;
                                    if (tds.size() == 0 && isf) {
                                        return new DefNode(name,new GroupNode(new ArrayNode(new PushNode(new StamonInteger(0)),
                                                new PushNode(StamonBase.value_null))));
                                    }
                                    break;
                                }
                                if (token.getType().equals(Token.Type.SEM) && token.getData().equals(",")) break;
                                tds.add(token);
                            } while (true);
                            ExpressionParser p = new ExpressionParser(parser,tds);
                            b.add(new GroupNode(p.eval(table)));
                            isf = false;
                        } while (isend);
                        return new DefNode(name,new GroupNode(new ArrayNode(new PushNode(new StamonInteger(b.size())),b)));
                    }else {
                        parser.setBuffer(token);
                        List<Token> expression = new ArrayList<>();
                        int i = 0;
                        do{
                            token = parser.getToken();
                            if (token.getType().equals(Token.Type.LP) && token.getData().equals("{")) i += 1;
                            if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) i -= 1;
                            if (i == 0 && token.getType().equals(Token.Type.END)) break;
                            expression.add(token);
                        }while (true);
                        ExpressionParser ep = new ExpressionParser(parser,expression);
                        return new DefNode(name, (GroupNode) ep.eval(table));
                    }
                }else if(token.getType().equals(Token.Type.LP)&&token.getData().equals("[")){
                    int ibl = 1;
                    List<Token> ts = new ArrayList<>();
                    do {
                        token = parser.getToken();
                        if (token.getType().equals(Token.Type.LP) && token.getData().equals("[")) ibl += 1;
                        if (token.getType().equals(Token.Type.LR) && token.getData().equals("]")) ibl -= 1;
                        if (ibl <= 0) break;
                        ts.add(token);
                    } while (true);
                    ExpressionParser ep = new ExpressionParser(parser,ts);
                    return new DefNode(name,new GroupNode(new ArrayNode(ep.eval(table),ASTNode.empty)));
                }else if(token.getType().equals(Token.Type.END)){
                    return new DefNode(name,new GroupNode(new PushNode(StamonBase.value_null)));
                }else{
                    throw new SyntaxException(token,"'=' expected.");
                }
            }else throw new SyntaxException(token,"Type is already defined.");
        }else throw new SyntaxException(token,"<identifier> expected.");
    }
}
