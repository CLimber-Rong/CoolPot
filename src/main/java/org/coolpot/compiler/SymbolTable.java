package org.coolpot.compiler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SymbolTable {
    SourceFile file;
    List<String> library;
    Deque<Scope> scope_stack;
    Scope scope;

    public SymbolTable(SourceFile file){
        this.file = file;
        this.scope = new Scope("Boot");
        this.library = new ArrayList<>();
        this.scope_stack = new LinkedList<>();
        createNewScope(scope);
    }

    public void createNewScope(Scope scope){
        scope_stack.push(scope);
    }

    public void popScope(){
        scope_stack.pop();
    }

    public Scope getBootScope(){
        return scope;
    }

    public Scope getThisScope() {
        return scope_stack.peek();
    }

    public boolean isDefine(String name){
        for(Scope scopes : scope_stack){
            if(scopes.in_define.contains(name)) return true;
        }
        return false;
    }

    public List<String> getLibrary() {
        return library;
    }

    @Override
    public String toString() {
        return "SymbolTable: ("+file.getFileName()+"){\n" +
                "    Librarys: "+library.toString()+"\n" +
                "    Scope: "+scope.toString()+"\n" +
                "    Nodes: "+file.nodes.toString()+"\n" +
                "}";
    }

    public static class Scope {
        String name;
        List<String> in_define;
        boolean isFunc;

        public Scope(String name){
            this.name = name;
            this.in_define = new ArrayList<>();
        }


        public List<String> getInDefine() {
            return in_define;
        }

        @Override
        public String toString() {
            return name+": {\n" +
                    "        Define: "+in_define.toString()+"\n" +
                    "    }";
        }
    }
}
