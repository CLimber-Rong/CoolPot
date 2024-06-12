package org.coolpot.bytecode.ir;

import java.io.DataOutputStream;
import java.io.IOException;

public class OpIR extends STIR{

    @Override
    public byte getOpcode() {
        return 0;
    }

    @Override
    public void dump(DataOutputStream out) throws IOException {

    }
}
