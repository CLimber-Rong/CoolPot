package org.coolpot.compiler.ir;

public class MemberIR extends STIR{

    @Override
    public byte getOpcode() {
        return STIR.MEMBER;
    }
}
