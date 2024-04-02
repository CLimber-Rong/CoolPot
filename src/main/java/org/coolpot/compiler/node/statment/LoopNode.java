package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;

public class LoopNode implements ASTNode {
    ASTNode bool;
    ASTNode block;

    public LoopNode(ASTNode bool, ASTNode block) {
        this.bool = bool;
        this.block = block;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[while:").append('\n');
        bool.getString(trace+1,sb);
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("block:").append('\n');
        block.getString(trace+1,sb);
    }
}
