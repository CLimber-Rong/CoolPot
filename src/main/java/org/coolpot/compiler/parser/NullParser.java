package org.coolpot.compiler.parser;

import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;

public class NullParser implements SubParser{

    @Override
    public Token getMatchToken() {
        return null;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        return ASTNode.empty;
    }
}
