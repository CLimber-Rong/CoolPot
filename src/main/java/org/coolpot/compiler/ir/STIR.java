package org.coolpot.compiler.ir;

public interface STIR {
    STIR nol_ir = new NolIR();

    byte getOpcode();
}
