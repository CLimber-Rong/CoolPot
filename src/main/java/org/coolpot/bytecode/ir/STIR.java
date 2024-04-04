package org.coolpot.bytecode.ir;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class STIR {
    public static final STIR nol_ir = new NolIR();

    public static final byte ADD = 0x01,
    SUB = 0X02,
    CPL = 0X03, // ~
    NOT = 0X04,
    NEW = 0X05,
    MUL = 0X06,
    DIV = 0X07,
    MOD = 0X08, // %
    LSH = 0X09, // <<
    RSH = 0X0A, // >>
    LESS = 0X0B,// <
    LEQ = 0X0C, // <=
    BIG = 0X0D, // >
    BEQ = 0X0E, // >=
    EQU = 0X0F, // ==
    IEQ = 0X10, // !=
    BAND = 0X11,// &
    BXOR = 0X12,// ^
    BOR = 0X13, // |
    LAND = 0X14,// &&
    LOR = 0X15, // ||
    ASSIGN = 0X16, // =
    MEMBER = 0X17,
    DEF = 0x18,
    FUNC = 0x19, END = 0x1A,
    IF = 0x1B,
    GOTO = 0X1C,
    PUSH = 0X1D,
    CALL = 0x1E,
    CLASS = 0x1F,
    SFN = 0x20,
    POS = 0x21, // +
    NEG = 0x22, // -
    RET = 0x23,
    ARRAY = 0x24,
    BRK = 0x25,
    CTN = 0x26;

    public abstract byte getOpcode();
    public abstract void dump(DataOutputStream out) throws IOException;

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + ": 0x"+Integer.toHexString(getOpcode());
    }
}
