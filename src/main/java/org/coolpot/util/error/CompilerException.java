package org.coolpot.util.error;

import java.io.PrintStream;

public class CompilerException extends RuntimeException{

    Throwable e;
    String message;

    public CompilerException(){
    }

    public CompilerException(Throwable e,String message){
        this.e = e;
        this.message = message;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        s.println("Error ["+e.getClass().getSimpleName()+"]: "+message);
    }

    public void printSuperInfo(PrintStream s){
        super.printStackTrace(s);
    }
}
