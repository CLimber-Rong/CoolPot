package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;

public class ClassNode implements ASTNode {

    ASTNode node;
    String name;

    public ClassNode(String name,ASTNode node){
        this.node = node;
        this.name = name;
    }

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
