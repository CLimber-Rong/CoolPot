package org.coolpot.compiler.parser;

import org.coolpot.compiler.ExpressionParser;
import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LoggingPermission;

public class ForParser implements SubParser{
    Token match;
    SourceFile file;
    Parser parser;

    public ForParser(SourceFile file, Parser parser){
        this.file = file;
        this.parser = parser;
        this.match = new Token(Token.Type.KEY,"for",0,file);
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        Token token = parser.getToken();
        if(token.getType().equals(Token.Type.NAM)){
            if(!table.isDefine(token.getData())){
                token = parser.getToken();
                if(token.getType().equals(Token.Type.KEY)&&token.getData().equals("in")){
                    List<Token> exp = new ArrayList<>();
                    do{
                        exp.add(token);
                        token = parser.getToken();
                    }while(token.getType().equals(Token.Type.LP)&&token.getData().equals("{"));
                    exp.remove(0);
                    ASTNode list;
                    if(exp.isEmpty())throw new SyntaxException(token,"<identifier> expected.");
                    else list = new ExpressionParser(parser,exp).eval(table);



                }else throw new SyntaxException(token,"'in' expected.");
            }else throw new SyntaxException(token,"Unable to resolve symbols.");
        }else throw new SyntaxException(token,"<identifier> expected.");
        return null;
    }
}
