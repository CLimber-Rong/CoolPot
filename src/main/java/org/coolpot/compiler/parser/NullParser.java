package org.coolpot.compiler.parser;

import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.ir.STIR;
import org.coolpot.compiler.tokens.Token;

public class NullParser implements SubParser{

    @Override
    public Token getMatchToken() {
        return null;
    }

    @Override
    public STIR eval(SymbolTable table) {
        return STIR.nol_ir;
    }
}
