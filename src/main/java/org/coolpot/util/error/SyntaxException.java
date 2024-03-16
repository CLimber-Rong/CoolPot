package org.coolpot.util.error;

import org.coolpot.compiler.tokens.Token;
import org.coolpot.compiler.tokens.UnknownToken;

import java.io.PrintStream;

public class SyntaxException extends CompilerException{

    Token token;
    String message;

    public SyntaxException(Token token, String message) {
        this.token = token;
        this.message = message;
    }

    public SyntaxException(UnknownToken token,String message){
        this.token = token;
        this.message = message;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        s.println("SyntaxError: "+message);
        if(token instanceof UnknownToken)
            s.println("    "+token);
        else {
            s.println("    at ("+token.getSourceFile().getFileName()+") lines:"+token.getLine()+" | token:"+token.getData());
        }
    }
}
