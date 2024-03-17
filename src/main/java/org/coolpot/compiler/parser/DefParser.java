package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.ir.Member_IR;
import org.coolpot.compiler.ir.STIR;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

public class DefParser implements SubParser {
    Token match;
    Parser parser;
    SourceFile file;

    public DefParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"def",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public STIR eval(SymbolTable table) {
        Token name = parser.getToken();
        if(name.getType().equals(Token.Type.NAM)){
            if(!table.getThisScope().getInDefine().contains(name.getData())){
                table.getThisScope().getInDefine().add(name.getData());
                return new Member_IR();
            }else throw new SyntaxException(name,"Type is already defined.");
        }else throw new SyntaxException(name,"should be: <identifier>");
    }
}
