package org.coolpot.bytecode.consts.objects;

public abstract class ConstObject<T> {
    public abstract int getIndex();
    public abstract T getData();
    public abstract byte getType();
}
