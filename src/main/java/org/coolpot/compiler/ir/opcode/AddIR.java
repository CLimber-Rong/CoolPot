package org.coolpot.compiler.ir.opcode;

import org.coolpot.compiler.ir.STIR;

public class AddIR extends STIR {
    @Override
    public byte getOpcode() {
        return STIR.ADD;
    }
}
