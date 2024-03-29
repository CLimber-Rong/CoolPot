package org.coolpot.compiler;

import org.coolpot.compiler.parser.*;
import org.coolpot.compiler.tokens.Token;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BParser extends Parser{
    Set<SubParser> parsers;
    int index;
    Token buffer;
    List<Token> tokens;
    SourceFile file;
    BlockParser parser;

    public BParser(SourceFile file,List<Token> tokens,BlockParser parser){
        super(file);
        this.file = file;
        this.tokens = tokens;
        this.buffer = null;
        this.index = 0;
        this.parsers = new HashSet<>();
        this.parser = parser;

        this.parsers.add(new DefParser(file,this));
        this.parsers.add(new SfnParser(file,this));
        this.parsers.add(new SugarFuncParser(file,this));
        this.parsers.add(new BreakParser(file,this));
        this.parsers.add(new ContinueParser(file,this));
        this.parsers.add(new ReturnParser(file,this));
    }

    public SubParser getParser(){
        Token head = getToken();
        if(head == null) return null;
        for(SubParser parser : parsers){
            if(head.equals(parser.getMatchToken())){
                return parser;
            }
        }
        parser.buffer = head;
        return Parser.nul;
    }

    @Override
    public Token getToken() {
        return parser.getToken();
    }
}
