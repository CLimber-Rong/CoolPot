package org.coolpot.bytecode.consts;

import org.coolpot.bytecode.ByteCodeFile;
import org.coolpot.bytecode.consts.objects.ConstInteger;
import org.coolpot.bytecode.consts.objects.ConstDouble;
import org.coolpot.bytecode.consts.objects.ConstObject;
import org.coolpot.bytecode.consts.objects.ConstString;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ConstTable {

    ByteCodeFile file;
    Map<Integer, ConstObject<?>> const_objects;
    int index;

    public ConstTable(ByteCodeFile file){
        this.file = file;
        this.const_objects = new HashMap<>();
    }

    public void putObject(ConstObject<?> object){
        const_objects.put(index,object);
        index++;
    }

    public void dump(DataOutputStream out) throws IOException {
        out.writeInt(const_objects.size());
        for(Integer index : const_objects.keySet()){
            out.writeInt(index);
            ConstObject<?> consts = const_objects.get(index);
            out.writeByte(consts.getType());
            if(consts instanceof ConstInteger)
                out.writeInt(((ConstInteger) consts).getData());
            else if(consts instanceof ConstDouble)
                out.writeDouble(((ConstDouble) consts).getData());
            else if(consts instanceof ConstString) {
                out.writeInt(((String) consts.getData()).length());
                out.write(((String) consts.getData()).getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public static final byte INT = 0x01, DOUBLE = 0x02, STRING = 0x03;
}