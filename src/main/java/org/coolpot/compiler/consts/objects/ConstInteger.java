package org.coolpot.compiler.consts.objects;

public class ConstInteger extends ConstObject{

    int data;
    int index;

    public ConstInteger(int data,int index){
        this.data = data;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
