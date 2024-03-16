package org.coolpot.compiler.parser;

import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.ir.STIR;
import org.coolpot.compiler.tokens.Token;

public interface SubParser {

    Token getMatchToken();
    STIR eval(SymbolTable table);
}
