package org.coolpot.runtime.obj;

//FIXME 无法确定是否有boolean类型
public class StamonBoolean extends StamonBase<Boolean> {
    boolean data;

    public StamonBoolean(boolean data) {
        this.data = data;
    }

    @Override
    public Boolean getData() {
        return data;
    }
}
