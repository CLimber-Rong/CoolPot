package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.node.irnode.PushNode;
import org.coolpot.compiler.node.statment.DefNode;
import org.coolpot.compiler.node.statment.FuncNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.runtime.obj.StamonFunc;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class SugarFuncParser implements SubParser {

    Token match;
    Parser parser;
    List<Token> tokens;
    int index;
    SourceFile file;

    public SugarFuncParser(SourceFile file, Parser parser) {
        this.match = new Token(Token.Type.KEY, "func", 0, file);
        this.parser = parser;
        this.file = file;
        this.tokens = null;
    }

    public SugarFuncParser(SourceFile file, List<Token> tokens) {
        this.tokens = tokens;
        this.file = file;
    }

    protected Token getToken() {
        if (tokens == null) {
            return parser.getToken();
        } else {
            if (index >= tokens.size()) return null;
            Token token = tokens.get(index);
            index++;
            return token;
        }
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        List<ASTNode> nodes = new ArrayList<>();
        Token token = getToken();
        String name = "";
        if (token.getType().equals(Token.Type.NAM)) {
            if (!table.isDefine(token.getData())) {
                if(table.hasScope(SymbolTable.ScopeType.IF) || table.hasScope(SymbolTable.ScopeType.WHILE) || table.hasScope(SymbolTable.ScopeType.FUNC))
                    throw new SyntaxException(token,"Illegal statement.");
                table.getThisScope().getInDefine().add(token.getData());
                table.createNewScope(new SymbolTable.Scope("func:"+token.getData(), SymbolTable.ScopeType.FUNC));
                name = token.getData();
                nodes.add(parserFuncArg(table));
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

                nodes.add(subParser(table,block));
            } else throw new SyntaxException(token, "Type is already defined.");
        } else throw new SyntaxException(token, "<identifier> expected.");
        table.popScope();

        return new DefNode(name,new GroupNode(new PushNode(new StamonFunc(new FuncNode(name,new GroupNode(nodes))))));
    }

    public ASTNode subParser(SymbolTable table,List<Token> tokens){
        return new BlockParser(file,tokens).eval(table);
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
                    nodes.add(new DefNode(token.getData(),new GroupNode(ASTNode.empty)));
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
}
