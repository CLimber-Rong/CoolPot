package org.coolpot.compiler.consts;

import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.consts.objects.ConstObject;

import java.util.HashMap;
import java.util.Map;

public class ConstTable {

    SourceFile file;
    Map<Integer, ConstObject> const_objects;
    int index;

    public ConstTable(SourceFile file){
        this.file = file;
        this.const_objects = new HashMap<>();
    }

    public void putObject(ConstObject object){
        const_objects.put(index,object);
        index++;
    }
}
