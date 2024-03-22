package org.coolpot.compiler.parser;

import org.coolpot.CompilerManager;
import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
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
    public ASTNode eval(SymbolTable table) {
        Token token = parser.getToken();

        if(token.getType().equals(Token.Type.NAM)){
            for(SourceFile file : CompilerManager.compiling_files)
                if(file.getFileName().split("\\.")[0].equals(token.getData())){
                    table.getLibrary().add(token.getData());
                    return ASTNode.empty;
                }
            throw new SyntaxException(token,"Cannot found import library.");
        }else throw new SyntaxException(token,"Type name is not valid.");
    }
}
