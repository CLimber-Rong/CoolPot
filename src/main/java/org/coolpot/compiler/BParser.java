package org.coolpot.compiler;

import org.coolpot.compiler.parser.BlockParser;
import org.coolpot.compiler.parser.DefParser;
import org.coolpot.compiler.parser.SfnParser;
import org.coolpot.compiler.parser.SubParser;
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
    }

    public SubParser getParser(){
        Token head = getToken();
        if(head == null) return null;
        for(SubParser parser : parsers){
            if(head.equals(parser.getMatchToken())){
                return parser;
            }
        }
        return Parser.nul;
    }

    @Override
    public Token getToken() {
        return parser.getToken();
    }
}
