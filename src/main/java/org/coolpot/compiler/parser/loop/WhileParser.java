package org.coolpot.compiler.parser.loop;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.parser.SubParser;
import org.coolpot.compiler.tokens.Token;

public class WhileParser implements SubParser {
    Token match;
    Parser parser;

    public WhileParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"while",0,file);
        this.parser = parser;
    }
    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        return null;
    }
}
