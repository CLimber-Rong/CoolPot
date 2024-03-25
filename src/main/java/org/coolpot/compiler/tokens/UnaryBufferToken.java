package org.coolpot.compiler.tokens;

public class UnaryBufferToken extends Token{
    Token token;
    public UnaryBufferToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String getData() {
        return token.data;
    }

    @Override
    public Type getType() {
        return Type.SEM;
    }

    @Override
    public String toString() {
        return "[U:"+ token.toString()+"]";
    }
}
