package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.runtime.obj.StamonBase;

public class PushNode implements ASTNode {
    StamonBase<?> value;

    public PushNode(StamonBase value){
        this.value = value;
    }

    public StamonBase<?> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[push:"+value.toString()+"]";
    }
}
