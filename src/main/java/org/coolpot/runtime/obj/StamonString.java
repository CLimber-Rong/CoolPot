package org.coolpot.runtime.obj;

public class StamonString extends StamonBase<String>{
    String data;
    public StamonString(String data){
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }
}
