package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;

public class FuncNode implements ASTNode {
    GroupNode node;
    String name;

    public FuncNode(String name,GroupNode node){
        this.node = node;
        this.name = name;
    }

    public GroupNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "{<"+name+">:["+node.toString()+"]}";
    }
}
