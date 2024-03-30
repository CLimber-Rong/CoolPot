package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;

public class MemberNode implements ASTNode {
    @Override
    public String toString() {
        return "[member]";
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[member]\n");
    }
}
