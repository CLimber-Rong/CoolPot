package org.coolpot.compiler.parser.decide;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.statment.DecideNode;
import org.coolpot.compiler.parser.BlockParser;
import org.coolpot.compiler.parser.SubParser;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class ElseParser implements SubParser {

    Token match;
    Parser parser;

    public ElseParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"else",0,file);
        this.parser = parser;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        Token token = parser.getToken();
        List<Token> block = new ArrayList<>();
        if(token.getType().equals(Token.Type.SEM)&&token.getData().equals(":")){
            int i = 0;
            do{
                token = parser.getToken();
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
                    token = parser.getToken();
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
        }else if(token.getType().equals(Token.Type.KEY)&&token.getData().equals("if")){
            IfParser ip = new IfParser(parser.getFile(),parser);
            return new DecideNode.SubDecide(ip.eval(table));
        }else throw new SyntaxException(token,"Missing statement body.");
    }
}
