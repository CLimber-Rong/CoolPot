package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;

public class DefNode implements ASTNode {
    String name;
    public DefNode(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[def:"+name+"]";
    }
}
