package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;

public class IfParser implements SubParser{

    Token match;
    Parser parser;

    public IfParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"if",0,file);
        this.parser = parser;
    }

    @Override
    public Token getMatchToken() {
        return null;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        return null;
    }
}
