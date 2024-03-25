package org.coolpot.runtime.obj;

public class StamonDouble extends StamonBase<Double>{
    double data;
    public StamonDouble(double data){
        this.data = data;
    }

    @Override
    public Double getData() {
        return data;
    }
}
