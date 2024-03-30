package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;

public class BreakNode implements ASTNode {
    @Override
    public String toString() {
        return "[break]";
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[break]\n");
    }
}
