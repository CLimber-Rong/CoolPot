package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;

public class ContinueNode implements ASTNode {
    @Override
    public String toString() {
        return "[continue]";
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[continue]\n");
    }
}
