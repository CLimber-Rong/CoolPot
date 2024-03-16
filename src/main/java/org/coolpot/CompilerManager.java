package org.coolpot;

import org.coolpot.compiler.LexicalAnalysis;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.tokens.Token;

import java.io.File;
import java.util.Collection;

public class CompilerManager {

    public static void compilers(Collection<String> files){
        for(String file : files){
            SourceFile sourceFile = new SourceFile(new File(file));
            createCompilerTask(sourceFile);
        }
    }

    public static void createCompilerTask(SourceFile file){
        try {
            LexicalAnalysis lexical = new LexicalAnalysis(file);
            Collection<Token> tokens = lexical.getTokens();

            System.out.println(tokens);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
