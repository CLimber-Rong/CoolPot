package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;

public class DecideNode implements ASTNode {

    ASTNode bool;
    ASTNode block;
    ASTNode decides;

    public DecideNode(ASTNode bool,ASTNode block,ASTNode decides){
        this.bool = bool;
        this.block = block;
        this.decides = decides;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[if:").append('\n');
        bool.getString(trace+1,sb);
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("block:").append('\n');
        block.getString(trace+1,sb);
        decides.getString(trace+1,sb);
    }

    public static class SubDecide implements ASTNode{

        ASTNode block;

        public SubDecide(ASTNode block){
            this.block = block;
        }

        @Override
        public void getString(int trace, StringBuilder sb) {
            sb.append(" ".repeat(Math.max(0, trace)));
            sb.append("[else:").append('\n');
            block.getString(trace+1,sb);
        }
    }
}
