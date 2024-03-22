package org.coolpot.bytecode.ir;

import java.io.DataOutputStream;
import java.io.IOException;

public class SfnIR extends STIR{

    public SfnIR(){
    }

    @Override
    public byte getOpcode() {
        return STIR.SFN;
    }

    @Override
    public void dump(DataOutputStream out) throws IOException {
        out.writeByte(getOpcode());
    }
}
