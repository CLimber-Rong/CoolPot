package org.coolpot.compiler.node;

import org.coolpot.compiler.node.irnode.BreakNode;
import org.coolpot.compiler.node.irnode.ContinueNode;

public interface ASTNode {
    static EmptyNode empty = new EmptyNode();
    BreakNode break_node = new BreakNode();
    ContinueNode continue_node = new ContinueNode();
}
