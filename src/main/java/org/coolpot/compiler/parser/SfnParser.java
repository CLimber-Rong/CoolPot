package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.ir.SFN_IR;
import org.coolpot.compiler.ir.STIR;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

public class SfnParser implements SubParser{

    Token match;
    Parser parser;
    SourceFile file;

    public SfnParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"sfn",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public STIR eval(SymbolTable table) {
        if(!file.isSFN())
            System.out.println("[Warn]: It is not recommended to call SFN commands directly in user scripts");
        Token token = parser.getToken();
        if(token.getType().equals(Token.Type.NUM)){
            int port = Integer.valueOf(token.getData());
            token = parser.getToken();
            if(!(token.getType().equals(Token.Type.SEM) && token.getData().equals(",")))
                throw new SyntaxException(token,"',' expected.");
            token = parser.getToken();
            if(token.getType().equals(Token.Type.NAM)){
                if(table.getThisScope().getInDefine().contains(token.getData())){
                    return new SFN_IR(port);
                }else throw new SyntaxException(token,"Unable to resolve symbols.");
            }else throw new SyntaxException(token,"Type name is not valid.");
        }else if(token.getType().equals(Token.Type.NAM)) {
            if(table.getThisScope().getInDefine().contains(token.getData())) {

                //TODO 端口变量提取指令未定义

                return new SFN_IR(0x00);
            }else throw new SyntaxException(token,"Unable to resolve symbols.");
        }else throw new SyntaxException(token,"Type name is not valid.");
    }
}
