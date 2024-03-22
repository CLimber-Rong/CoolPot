package org.coolpot.compiler.node;

import java.util.ArrayList;
import java.util.List;

public class GroupNode implements ASTNode{
    List<ASTNode> nodes;
    public GroupNode(ASTNode... nodes){
        this.nodes = new ArrayList<>(List.of(nodes));
    }

    public List<ASTNode> getNodes() {
        return nodes;
    }
}
