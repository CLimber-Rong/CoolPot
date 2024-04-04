package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;

import java.util.ArrayList;
import java.util.List;

public class ArrayNode implements ASTNode {

    ASTNode length;
    List<ASTNode> nodes;

    public ArrayNode(ASTNode length,ASTNode... nodes){
        this.length = length;
        this.nodes = new ArrayList<>(List.of(nodes));
    }

    public ArrayNode(ASTNode length, List<ASTNode> nodes) {
        this.length = length;
        this.nodes = nodes;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[array:").append('\n');
        sb.append(" ".repeat(Math.max(0, trace))).append(" length:\n");
        length.getString(trace+1, sb);
        sb.append(" ".repeat(Math.max(0, trace))).append(" block:\n");
        new GroupNode(nodes).getString(trace+1, sb);

    }
}
