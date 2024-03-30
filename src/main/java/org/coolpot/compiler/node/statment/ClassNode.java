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

    public String getName() {
        return name;
    }

    public ASTNode getNode() {
        return node;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[class:").append(name).append('\n');
        node.getString(trace + 1,sb);
        //sb.append(" ".repeat(Math.max(0, trace)));sb.append("]\n");
    }

    @Override
    public String toString() {
        return "{Class:"+node.toString()+"}";
    }
}
