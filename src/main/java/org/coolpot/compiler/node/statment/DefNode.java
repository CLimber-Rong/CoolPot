package org.coolpot.compiler.node.statment;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;

public class DefNode implements ASTNode {
    String name;
    GroupNode vars;

    public DefNode(String name,GroupNode vars){
        this.name = name;
        this.vars = vars;
    }

    public String getName() {
        return name;
    }

    public GroupNode getVars() {
        return vars;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("[def:").append(name).append('\n');
        vars.getString(trace + 1,sb);
        /*
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("]\n");
        */

    }

    @Override
    public String toString() {
        return "[def:"+name+" | "+vars+"]";
    }
}
