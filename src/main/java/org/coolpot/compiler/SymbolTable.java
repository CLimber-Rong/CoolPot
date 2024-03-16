package org.coolpot.compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    List<String> library = new ArrayList<>();

    public List<String> getLibrary() {
        return library;
    }

    @Override
    public String toString() {
        return "SymbolTable: {\n" +
                "    Librarys: "+library.toString()+"\n" +
                "}";
    }
}
