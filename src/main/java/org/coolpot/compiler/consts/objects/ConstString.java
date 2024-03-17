package org.coolpot.compiler.consts.objects;

public class ConstString extends ConstObject{

    String data;
    int index;

    public ConstString(String data,int index){
        this.data = data;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
