package org.coolpot.compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    SourceFile file;
    List<String> library;
    Scope scope;

    public SymbolTable(SourceFile file){
        this.file = file;
        this.scope = new Scope("Boot");
        this.library = new ArrayList<>();
    }

    public Scope getThisScope() {
        return getSubScope(scope);
    }

    private Scope getSubScope(Scope scope){
        Scope this_scope;
        if(scope.scope != null){
            this_scope = getThisScope();
            return this_scope;
        }
        return scope;
    }

    public List<String> getLibrary() {
        return library;
    }

    @Override
    public String toString() {
        return "SymbolTable: ("+file.getFileName()+"){\n" +
                "    Librarys: "+library.toString()+"\n" +
                "    Scope: "+scope.toString()+"\n" +
                "}";
    }

    public static class Scope {
        String name;
        Scope scope;
        List<String> in_define;

        public Scope(String name){
            this.name = name;
            this.in_define = new ArrayList<>();
        }

        public List<String> getInDefine() {
            return in_define;
        }

        public void setSubScope(Scope scope) {
            this.scope = scope;
        }

        public Scope getSubScope() {
            return scope;
        }

        @Override
        public String toString() {
            return name+": {\n" +
                    "        Define: "+in_define.toString()+"\n" +
                    "    }";
        }
    }
}
