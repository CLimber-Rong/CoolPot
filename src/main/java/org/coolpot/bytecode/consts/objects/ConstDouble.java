package org.coolpot.bytecode.consts.objects;

import org.coolpot.bytecode.consts.ConstTable;

public class ConstDouble extends ConstObject<Double>{
    int index;
    double data;

    public ConstDouble(int index,double data){
        this.index = index;
        this.data = data;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Double getData() {
        return data;
    }

    @Override
    public byte getType() {
        return ConstTable.DOUBLE;
    }
}
