package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.ir.NolIR;
import org.coolpot.compiler.ir.STIR;
import org.coolpot.compiler.tokens.Token;

import java.util.Deque;

public class DefParser implements SubParser {
    Token match;
    Parser parser;

    public DefParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"def",0,file);
        this.parser = parser;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public STIR eval(SymbolTable table) {
        return new NolIR();
    }
}
