package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;

public class SfnNode implements ASTNode {
    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[sfn]\n");
    }
}
