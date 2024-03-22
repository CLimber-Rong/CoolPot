package org.coolpot.bytecode;

import org.coolpot.bytecode.consts.ConstTable;
import org.coolpot.compiler.SourceFile;
import org.coolpot.util.MetaConfig;
import org.coolpot.util.error.CompilerException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteCodeFile {
    SourceFile file;
    ConstTable table;
    File output;

    public ByteCodeFile(SourceFile file,File output){
        this.file = file;
        this.table = new ConstTable(this);
        this.output = output;
    }

    public void dump(){
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(output));
            out.writeShort(0XADBD);
            out.writeByte(MetaConfig.bc_version[0]);
            out.writeByte(MetaConfig.bc_version[1]<<4 + MetaConfig.bc_version[2]&0x0f);
            table.dump(out);
        }catch (IOException io){
            throw new CompilerException(io,"Cannot write bytecode file ["+output.getName()+"]");
        }
    }
}