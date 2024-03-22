package org.coolpot.compiler.parser;

import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;

public interface SubParser {

    Token getMatchToken();
    ASTNode eval(SymbolTable table);
}
