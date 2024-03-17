package org.coolpot.compiler.tokens;

import org.coolpot.compiler.SourceFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UTF8Token extends Token{

    public UTF8Token(String data, int line, SourceFile file){
        super(Type.UTF8,data,line,file);
    }

    public static boolean isUTF8(Character data){
        byte[] byts = String.valueOf(data).getBytes();
        if(byts.length < 3) return false;
        return byts[0] == (byte) 0xEF && byts[1] == (byte) 0xBB && byts[2] == (byte) 0xBF;
    }

    public static boolean isChinese(Character data){
        String str = String.valueOf(data);
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean checkChinesePunctuationByScript(String str) {
        if (str == null) {
            return false;
        }
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (isChineseByScript(aChar)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChineseByScript(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
            return true;
        }
        return false;
    }


    @Override
    public Type getType() {
        return Type.UTF8;
    }
}
