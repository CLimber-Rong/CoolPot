package org.coolpot.bytecode.consts.objects;

import org.coolpot.bytecode.consts.ConstTable;

public class ConstInteger extends ConstObject<Integer>{

    int data;
    int index;

    public ConstInteger(int data){
        this.data = data;
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public Integer getData() {
        return data;
    }

    @Override
    public byte getType() {
        return ConstTable.INT;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
