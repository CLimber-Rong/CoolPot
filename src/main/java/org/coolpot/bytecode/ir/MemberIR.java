package org.coolpot.bytecode.ir;

import java.io.DataOutputStream;
import java.io.IOException;

public class MemberIR extends STIR{

    @Override
    public byte getOpcode() {
        return STIR.MEMBER;
    }

    @Override
    public void dump(DataOutputStream out) throws IOException {

    }
}
