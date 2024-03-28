package org.coolpot.bytecode.consts.objects;

public abstract class ConstObject<T> {
    public abstract int getIndex();
    public abstract T getData();
    public abstract byte getType();
    public abstract void setIndex(int index);

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ConstObject<?>){
            if(this.getType() == ((ConstObject<?>) obj).getType()){
                return this.getData().equals(((ConstObject<?>) obj).getData());
            }else return false;
        }else return false;
    }
}
