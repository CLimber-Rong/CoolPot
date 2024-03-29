package org.coolpot.bytecode.consts.objects;

import org.coolpot.bytecode.consts.ConstTable;

public class ConstString extends ConstObject<String>{

    String data;
    int index;

    public ConstString(String data){
        this.data = data;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public byte getType() {
        return ConstTable.STRING;
    }

}
