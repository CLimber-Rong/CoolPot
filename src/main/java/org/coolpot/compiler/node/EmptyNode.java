package org.coolpot.compiler.node;

public class EmptyNode implements ASTNode{
    @Override
    public String toString() {
        return "empty";
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[empty]\n");
    }
}
