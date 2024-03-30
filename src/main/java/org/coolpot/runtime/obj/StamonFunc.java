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
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<func:\n");
        node.getString(trace+1,sb);
        //sb.append(" ".repeat(Math.max(0, trace))).append(">\n");
    }

    @Override
    public String toString() {
        return "(func:" + node.toString()+")";
    }
}
