package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;

public class MemberNode implements ASTNode {
    Token value;
    public MemberNode(Token value){
        this.value = value;
    }
}
