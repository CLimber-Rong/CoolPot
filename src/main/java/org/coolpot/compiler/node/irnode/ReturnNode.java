package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;

public class ReturnNode implements ASTNode {
    ASTNode node;
    public ReturnNode(ASTNode node){
        this.node = node;
    }
    @Override
    public String toString() {
        return "[return:"+node+"]";
    }
}
