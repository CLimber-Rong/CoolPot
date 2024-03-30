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

    public FuncNode(GroupNode node){
        this.node = node;
    }

    public GroupNode getNode() {
        return node;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[func:").append(name).append('\n');
        node.getString(trace + 1,sb);
        /*
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("]\n");
         */
    }

    @Override
    public String toString() {
        return "{<"+name+">:["+node.toString()+"]}";
    }
}
