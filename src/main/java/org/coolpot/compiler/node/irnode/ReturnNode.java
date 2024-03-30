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

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[return: \n");
        node.getString(trace + 1,sb);
        sb.append("]\n");
    }
}
