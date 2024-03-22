package org.coolpot.compiler.node.irnode;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.tokens.Token;

public class PushNode implements ASTNode {
    Token token;

    public PushNode(Token token){
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
