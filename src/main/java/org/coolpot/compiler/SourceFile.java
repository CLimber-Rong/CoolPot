package org.coolpot.compiler;

import org.coolpot.bytecode.consts.ConstTable;
import org.coolpot.bytecode.ir.STIR;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;

import java.io.File;
import java.util.*;

public class SourceFile {
    File file;
    List<Token> tokens;
    List<ASTNode> nodes;
    List<String> line_data;
    SymbolTable tables;
    LexicalAnalysis lexical;
    Parser parser;
    boolean isSFN;

    public SourceFile(File file){
        this.file = file;
        this.line_data = new ArrayList<>();
        this.tables = new SymbolTable(this);
        this.lexical = new LexicalAnalysis(this);
        this.nodes = new ArrayList<>();
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

    public void setSFN(boolean SFN) {
        isSFN = SFN;
    }

    public boolean isSFN() {
        return isSFN;
    }

    public String getFileName() {
        return file.getName();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<String> getLineData() {
        return line_data;
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
