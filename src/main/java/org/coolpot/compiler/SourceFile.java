package org.coolpot.compiler;

import org.coolpot.compiler.tokens.Token;

import java.io.File;
import java.util.*;

public class SourceFile {
    File file;
    List<Token> tokens;
    SymbolTable tables;
    LexicalAnalysis lexical;
    Parser parser;

    public SourceFile(File file){
        this.file = file;
        this.tables = new SymbolTable();
        this.lexical = new LexicalAnalysis(this);
        this.parser = new Parser(this);
    }

    public void compilerFile(){
        try {
            this.tokens = removeText(lexical.getTokens());
            this.parser.parser(tables);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return file.getName();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private List<Token> removeText(Collection<Token> tokens){
        List<Token> token_1 = new ArrayList<>(),token_2 = new ArrayList<>();
        for(Token token:tokens){
            if(token.getType().equals(Token.Type.TXT)) continue;
            token_1.add(token);
        }
        for (Iterator<Token> iterator = token_1.iterator(); iterator.hasNext(); ) {
            Token token = iterator.next();
            if(token.getType().equals(Token.Type.LITX)){
                do{
                    token = iterator.next();
                }while (!token.getType().equals(Token.Type.LINE));
            }else if(!token.getType().equals(Token.Type.LINE)) {
                token_2.add(token);
            }
        }
        return token_2;
    }
}
