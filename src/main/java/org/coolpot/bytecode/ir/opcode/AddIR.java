package org.coolpot.bytecode.ir.opcode;

import org.coolpot.bytecode.ir.STIR;

import java.io.DataOutputStream;
import java.io.IOException;

public class AddIR extends STIR {
    @Override
    public byte getOpcode() {
        return STIR.ADD;
    }

    @Override
    public void dump(DataOutputStream out) throws IOException {
        out.writeByte(STIR.ADD);
    }
}
