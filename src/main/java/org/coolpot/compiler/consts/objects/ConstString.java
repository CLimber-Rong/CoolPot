package org.coolpot.compiler.consts.objects;

import org.coolpot.compiler.consts.ConstTable;

public class ConstString extends ConstObject<String>{

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

    @Override
    public String getData() {
        return data;
    }

    @Override
    public byte getType() {
        return ConstTable.STRING;
    }
}