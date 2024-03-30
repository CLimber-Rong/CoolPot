package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.runtime.obj.StamonBase;

public class PushNode implements ASTNode {
    StamonBase<?> value;

    public PushNode(StamonBase<?> value){
        this.value = value;
    }

    public StamonBase<?> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[push:"+value.toString()+"]";
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[push:").append('\n');
        value.getString(trace + 1,sb);
        /*
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("]\n");
        */
    }
}
