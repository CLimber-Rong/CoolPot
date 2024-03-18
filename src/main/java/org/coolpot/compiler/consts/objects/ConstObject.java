package org.coolpot.compiler.consts.objects;

public abstract class ConstObject<T> {
    public abstract int getIndex();
    public abstract T getData();
    public abstract byte getType();
}
