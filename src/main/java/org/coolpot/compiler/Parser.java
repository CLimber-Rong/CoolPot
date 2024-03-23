package org.coolpot.compiler;

import org.coolpot.bytecode.ir.STIR;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.parser.*;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

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
        this.parsers.add(new SfnParser(file,this));
        this.parsers.add(new SugarClassParser(file,this));
        this.parsers.add(new SugarFuncParser(file,this));
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
        buffer = head;
        return null;
    }

    public void parser(SymbolTable table){
        SubParser parser;

        while (true){
            parser = parserStatement();
            if(parser instanceof NullParser) break;
            if(parser != null){
                ASTNode ir = parser.eval(table);
                file.nodes.add(ir);
            }else {
                Token token = getToken();
                if(token.getType().equals(Token.Type.NAM)){

                }else throw new SyntaxException(token,"Not a statement.");
            }
        }

        System.out.println(table);
    }
}
