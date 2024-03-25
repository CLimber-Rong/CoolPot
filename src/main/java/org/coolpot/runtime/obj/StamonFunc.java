package org.coolpot.runtime.obj;

import org.coolpot.compiler.node.statment.FuncNode;

public class StamonFunc extends StamonBase<FuncNode> {
    FuncNode node;
    public StamonFunc(FuncNode node){
        this.node = node;
    }
    @Override
    public FuncNode getData() {
        return node;
    }

    @Override
    public String toString() {
        return "(func:" + node.toString()+")";
    }
}
