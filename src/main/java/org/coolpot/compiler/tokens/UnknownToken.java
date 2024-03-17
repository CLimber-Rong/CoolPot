package org.coolpot.compiler.tokens;

public class UnknownToken extends Token{
    Character character;
    String filename;
    int line;

    public UnknownToken(Character character,String filename,int line){
        this.character = character;
        this.line = line;
        this.filename = filename;
    }

    public Character getCharacter() {
        return character;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public Type getType() {
        return Type.UKN;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Unknown { '"+character+"' ("+filename+") lines:"+line+" } 0x"+Integer.toHexString(hashCode());
    }
}
