package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;

public class OpNode implements ASTNode {
    NodeType type;

    public OpNode(NodeType type){
        this.type = type;
    }

    public enum NodeType{
        ADD,
        SUB,
        MUL,
        DIV,
        CPL,
        MOD,
        LSH,
        RSH,
        BIG,
        LESS,
        BEQ,
        LEQ,
        EQU,
        IEQ,
        BAND,
        BXOR,
        BOR,
        LAND,
        LOR,
        CALL,
        NEW,
        ASSIGN
    }

    @Override
    public String toString() {
        return "["+type.name()+"]";
    }
}
