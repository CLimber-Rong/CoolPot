package org.coolpot.compiler.node;

import java.util.ArrayList;
import java.util.List;

public class GroupNode implements ASTNode{
    List<ASTNode> nodes;

    public GroupNode(ASTNode... nodes){
        this.nodes = new ArrayList<>(List.of(nodes));
    }

    public GroupNode(List<ASTNode> nodes){
        this.nodes = nodes;
    }

    public List<ASTNode> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "{"+nodes.toString()+"}";
    }
}
