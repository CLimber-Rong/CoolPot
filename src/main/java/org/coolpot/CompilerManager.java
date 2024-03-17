package org.coolpot;

import joptsimple.OptionSet;
import org.coolpot.compiler.SourceFile;
import org.coolpot.util.error.CompilerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CompilerManager {

    public static List<SourceFile> compiling_files = new ArrayList<>();

    public static void compilers(OptionSet set, Collection<String> files){
        loadLibrary((File) set.valueOf("lib"));
        for(String file : files){
            SourceFile sourceFile = new SourceFile(new File(file));
            compiling_files.add(sourceFile);
        }

        for(SourceFile file: compiling_files)
            createCompilerTask(file);
    }

    public static void loadLibrary(File dir){
        if((!dir.exists())||(!dir.isDirectory())){
            throw new CompilerException(new FileNotFoundException(),"Cannot found library directory.");
        }
        for(File file : Objects.requireNonNull(dir.listFiles())){
            SourceFile sourceFile = new SourceFile(file);
            compiling_files.add(sourceFile);
        }
    }

    public static void createCompilerTask(SourceFile file){
        file.compilerFile();
    }
}
