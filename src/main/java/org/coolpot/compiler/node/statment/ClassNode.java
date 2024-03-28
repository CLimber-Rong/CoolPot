package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;

public class ClassNode implements ASTNode {

    ASTNode node;

    public ClassNode(ASTNode node){
        this.node = node;
    }

    public ASTNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "{Class:"+node.toString()+"}";
    }
}
