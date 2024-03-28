package org.coolpot.compiler.tokens;

import org.coolpot.compiler.node.ASTNode;

public class NodeBufferToken extends Token{

    ASTNode node;

    public NodeBufferToken(ASTNode node){
        this.node = node;
    }

    public ASTNode getNode() {
        return node;
    }

    @Override
    public Type getType() {
        return Type.NBF;
    }

    @Override
    public String toString() {
        return "[Node:"+node.toString()+"]";
    }
}
