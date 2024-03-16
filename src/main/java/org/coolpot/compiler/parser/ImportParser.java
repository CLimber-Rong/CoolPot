package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.ir.STIR;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

public class ImportParser implements SubParser{
    Token match;
    Parser parser;

    public ImportParser(SourceFile file,Parser parser){
        this.match = new Token(Token.Type.KEY,"import",0,file);
        this.parser = parser;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public STIR eval(SymbolTable table) {
        Token token = parser.getToken();

        if(token.getType().equals(Token.Type.NAM)){
            table.getLibrary().add(token.getData());
        }else throw new SyntaxException(token,"Type name is not valid.");

        return STIR.nol_ir;
    }
}
