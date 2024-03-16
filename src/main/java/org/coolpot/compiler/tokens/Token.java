package org.coolpot.compiler.tokens;

import org.coolpot.compiler.SourceFile;

public class Token {
    String data;
    SourceFile file;
    Type type;
    int line;

    public Token(){
    }

    public Token(Type type,String data,int line,SourceFile file){
        this.type = type;
        this.data = data;
        this.line = line;
        this.file = file;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public Type getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public SourceFile getSourceFile() {
        return file;
    }

    @Override
    public String toString() {
        return "{"+type.name()+":"+data+"}";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Token){
            return ((Token) obj).data.equals(this.data) && ((Token) obj).file.equals(this.file) && ((Token) obj).type.equals(this.type);
        }
        return false;
    }

    public enum Type{
        KEY, // 关键字
        SEM, // 符号
        NAM, // 标识符
        NUM, // 标准整形
        DBL, // 浮点型
        B16, // 16进制
        BIN, // 2进制
        LP,  // 左括号
        LR,  // 右括号
        STR, // 字符串型
        TXT, // 注释(忽略)
        LINE,// 换行符
        END, // 终止符
        LITX,// 行注释
    }

}
