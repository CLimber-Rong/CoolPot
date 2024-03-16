package org.coolpot.compiler;

import org.coolpot.compiler.tokens.Token;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SourceFile {
    File file;
    List<Token> tokens;

    public SourceFile(File file){
        this.file = file;
        this.tokens = new ArrayList<>();
    }

    public String getFileName() {
        return file.getName();
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
