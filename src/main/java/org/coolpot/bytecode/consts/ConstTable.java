package org.coolpot.bytecode.consts;

import org.coolpot.bytecode.ByteCodeFile;
import org.coolpot.bytecode.consts.objects.ConstInteger;
import org.coolpot.bytecode.consts.objects.ConstDouble;
import org.coolpot.bytecode.consts.objects.ConstObject;
import org.coolpot.bytecode.consts.objects.ConstString;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConstTable {

    ByteCodeFile file;
    List<ConstObject<?>> const_objects;

    public ConstTable(ByteCodeFile file){
        this.file = file;
        this.const_objects = new ArrayList<>();
    }

    public void putObject(ConstObject<?> object){
        const_objects.add(object);
    }

    public void dump(DataOutputStream out) throws IOException {
        out.writeInt(const_objects.size());
        for(ConstObject<?> consts : const_objects){
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
