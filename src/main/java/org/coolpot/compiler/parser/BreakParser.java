package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

public class BreakParser implements SubParser{
    Token match;
    Parser parser;
    SourceFile file;

    public BreakParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"break",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        if(table.hasScope(SymbolTable.ScopeType.WHILE)){
            return ASTNode.break_node;
        }else throw new SyntaxException(parser.getCurrentToken(),"back outside loop.");

    }
}
