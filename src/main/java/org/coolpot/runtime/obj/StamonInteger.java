package org.coolpot.runtime.obj;

public class StamonInteger extends StamonBase<Integer>{
    int data;
    public StamonInteger(int data){
        this.data = data;
    }

    @Override
    public Integer getData() {
        return data;
    }
}
