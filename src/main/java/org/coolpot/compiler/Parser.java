package org.coolpot.compiler;

import org.coolpot.compiler.parser.DefParser;
import org.coolpot.compiler.parser.ImportParser;
import org.coolpot.compiler.parser.NullParser;
import org.coolpot.compiler.parser.SubParser;
import org.coolpot.compiler.tokens.Token;

import java.util.LinkedList;
import java.util.List;

public class Parser {
    SourceFile file;
    NullParser nul;
    List<SubParser> parsers;
    int index;
    Token buffer;

    public Parser(SourceFile file){
        this.parsers = new LinkedList<>();
        this.file = file;
        this.nul = new NullParser();

        this.parsers.add(new ImportParser(file,this));
        this.parsers.add(new DefParser(file,this));
    }

    public Token getToken(){
        if(buffer != null){
            Token b = buffer;
            buffer = null;
            return b;
        }else {
            if(index >= file.tokens.size()){
                return null;
            }else {
                Token t = file.tokens.get(index);
                index++;
                return t;
            }
        }
    }

    private SubParser parserStatement(){
        Token head = getToken();
        if(head == null) return nul;
        for(SubParser parser : parsers){
            if(parser.getMatchToken().equals(head)){
                return parser;
            }else if(head.getType().equals(Token.Type.END)) return parserStatement();
        }
        return null;
    }

    public void parser(SymbolTable table){
        SubParser parser;
        try {
            while (!((parser = parserStatement()) instanceof NullParser)) {
                parser.eval(table);
            }
        }catch (NullPointerException e){
        }

        System.out.println(table);
    }
}
