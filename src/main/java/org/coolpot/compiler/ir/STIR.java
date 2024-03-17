package org.coolpot.compiler.ir;

public abstract class STIR {
    public static final STIR nol_ir = new Nol_IR();

    public abstract byte getOpcode();

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + ": 0x"+Integer.toHexString(getOpcode());
    }
}
