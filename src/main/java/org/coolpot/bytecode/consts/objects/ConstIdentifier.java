package org.coolpot.bytecode.consts.objects;

import org.coolpot.bytecode.consts.ConstTable;

public class ConstIdentifier extends ConstObject<String>{
    int index;
    String identifier;

    public ConstIdentifier(String identifier){
        this.identifier = identifier;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getData() {
        return identifier;
    }

    @Override
    public byte getType() {
        return ConstTable.IDENTIFIER;
    }

    @Override
    public void setIndex(int index) {
    }
}
