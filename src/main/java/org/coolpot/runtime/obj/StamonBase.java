package org.coolpot.runtime.obj;

public abstract class StamonBase<T> {
    public static final StamonNull value_null = new StamonNull();
    public abstract T getData();

    @Override
    public String toString() {
        return "(" + (getData() == null ? "null)" : (getData().getClass().getSimpleName() + "|" + getData()+")"));
    }
}
