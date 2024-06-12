package org.coolpot.compiler.parser.decide;

import org.coolpot.compiler.ExpressionParser;
import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.statment.DecideNode;
import org.coolpot.compiler.parser.BlockParser;
import org.coolpot.compiler.parser.SubParser;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

public class IfParser implements SubParser {

    Token match;
    Parser parser;
    List<Token> tokens;
    int index;

    public IfParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"if",0,file);
        this.parser = parser;
    }

    public IfParser(Parser parser,List<Token> tokens){
        this.parser = parser;
        this.tokens = tokens;
        this.index = 0;
    }

    private Token getToken(){
        if(tokens == null)
            return parser.getToken();
        else {
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
        ASTNode bool = parserBool(table);
        ASTNode block = parserBlock(table);
        Token token = getToken();
        if(token != null) {
            if (token.getType().equals(Token.Type.KEY) && token.getData().equals("else")) {
                ElseParser ep = new ElseParser(parser.getFile(),parser);
                return new DecideNode(bool,block,ep.eval(table));
            } else parser.setBuffer(token);
        }
        return new DecideNode(bool,block, new DecideNode.SubDecide(ASTNode.empty));
    }

    public ASTNode parserBlock(SymbolTable table){
        Token token = getToken();
        List<Token> block = new ArrayList<>();
        if(token.getType().equals(Token.Type.SEM)&&token.getData().equals(":")){
            int i = 0;
            do{
                token = getToken();
                if (token.getType().equals(Token.Type.LP) && token.getData().equals("{")) i += 1;
                if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) i -= 1;
                if (i == 0 && token.getType().equals(Token.Type.END)) break;
                block.add(token);
            }while (true);
            table.createNewScope(new SymbolTable.Scope("if", SymbolTable.ScopeType.IF));
            ASTNode n = new BlockParser(parser.getFile(),block).eval(table);
            table.popScope();
            return n;
        }else if(token.getType().equals(Token.Type.LP)&&token.getData().equals("{")){
            int i = 1;
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
            table.createNewScope(new SymbolTable.Scope("if", SymbolTable.ScopeType.IF));
            ASTNode n = new BlockParser(parser.getFile(),block).eval(table);
            table.popScope();
            return n;
        }else throw new SyntaxException(token,"Missing statement body.");
    }

    public ASTNode parserBool(SymbolTable table){
        List<Token> exp_row = new ArrayList<>();
        Token t;
        do{
            t = getToken();
            if(t.getType() == Token.Type.LP && t.getData().equals("{")){
                parser.setBuffer(t);
                break;
            }
            exp_row.add(t);
        }while (true);
        ExpressionParser ep = new ExpressionParser(parser, exp_row);
        return ep.eval(table);
    }
}
