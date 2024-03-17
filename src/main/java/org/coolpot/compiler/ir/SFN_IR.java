package org.coolpot.compiler.ir;

public class SFN_IR extends STIR{
    int port;
    public SFN_IR(int port){
        this.port = port;
    }

    @Override
    public byte getOpcode() {
        return 0;
    }
}
