package org.coolpot.compiler;

import org.coolpot.compiler.tokens.Token;
import org.coolpot.compiler.tokens.UnknownToken;
import org.coolpot.util.MetaConfig;
import org.coolpot.util.Util;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.Collection;

public final class LexicalAnalysis {
    SourceFile file;
    char[] datas;
    int index;
    int line;
    Character buffer;

    public LexicalAnalysis(SourceFile file){
        this.file = file;
        this.buffer = null;
        this.index = 0;
        this.line = 1;
        this.datas = Util.readFile(this.file.file);
    }

    private Character getChar(){
        char c;
        if(buffer!=null){
            c = buffer;
            buffer = null;
        }else {
            if(index >= datas.length) return null;
            c = datas[index];
            index++;
        }
        return c;
    }

    private Token getToken(){
        Character c;
        StringBuilder sb = new StringBuilder();
        do{
            c = getChar();
            if(c == null) return null;
        }while (c == ' ' || c == '\t');

        if(isSEM(c)){
            sb.append(c);
            return new Token(Token.Type.SEM, sb.toString(),line,file);
        }else if(isNum(c)){
            boolean isdouble = false;
            if(c == '0'){
                sb.append((char) c);
                c = getChar();
                if(c == 'x'){
                    return null;
                }else {
                    do {
                        sb.append((char) c);
                        c = getChar();
                        if (c == '.') isdouble = true;
                    } while (isNum(c) || c == '.'); //小数
                    if (isNam(c)) {
                        do {
                            sb.append((char) c);
                            c = getChar();
                        } while (isNam(c) || isNum(c));
                        buffer = (char) c;
                        return new Token(Token.Type.NAM, sb.toString(), line,file);
                    }
                    buffer = c;
                    if (isdouble) return new Token(Token.Type.DBL, sb.toString(), line,file);
                    else return new Token(Token.Type.NUM, sb.toString(), line,file);
                }
            }else {
                do {
                    sb.append((char) c);
                    c = getChar();
                    if (c == '.') isdouble = true;
                } while (isNum(c) || c == '.'); //小数
                if (isNam(c)) {
                    do {
                        sb.append((char) c);
                        c = getChar();
                    } while (isNam(c) || isNum(c));
                    buffer = c;
                    return new Token(Token.Type.NAM, sb.toString(), line,file);
                }
                buffer = c;
                if (isdouble) return new Token(Token.Type.DBL, sb.toString(), line,file);
                else return new Token(Token.Type.NUM, sb.toString(), line,file);
            }
        }else if(isNam(c)){
            do {
                sb.append((char) c);
                c = getChar();
                if (isSEM(c)) break;
            } while (isNam(c) || isNum(c));
            buffer = c;
            if (MetaConfig.isKey(sb.toString())) return new Token(Token.Type.KEY, sb.toString(), line,file);
            return new Token(Token.Type.NAM, sb.toString(), line,file);
        }else if (c == '"') {
            do {
                c = getChar();
                if (c == '\n')
                    throw new SyntaxException(new Token(Token.Type.STR, sb.toString(), line,file),"'\"' expected.");
                if (c == '\\') {
                    c = getChar();
                    if (c == 'n') {
                        sb.append("\n");
                    } else if (c == 't') {
                        sb.append("\t");
                    } else if (c == '\\') {
                        sb.append("\\");
                    } else if (c == '"') {
                        sb.append("\"");
                    } else
                        throw new SyntaxException(new Token(Token.Type.STR, sb.toString(), line,file),"Illegal escape character in string literal.");
                    continue;
                }
                sb.append((char) c);
            } while (c != '"');
            sb.deleteCharAt(sb.indexOf("\""));
            return new Token(Token.Type.STR, sb.toString(), line,file);
        }else if (isLP(c)) {
            sb.append((char) c);
            return new Token(Token.Type.LP, sb.toString(), line,file);
        } else if (isLR(c)) {
            sb.append((char) c);
            return new Token(Token.Type.LR, sb.toString(), line,file);
        }else if (c == ';') return new Token(Token.Type.END, ";", line,file);
        else if (c == '\n') {
            line += 1;
            return new Token(Token.Type.LINE, "", line,file);
        }else {
            throw new SyntaxException(new UnknownToken(c,file.file.getName(),line),"Unknown character in source file.");
        }
    }

    public Collection<Token> getTokens(){
        Collection<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = getToken()) != null)
            if(!token.getType().equals(Token.Type.TXT)){
                tokens.add(token);
            }
        return tokens;
    }

    private boolean isSEM(int c) {
        return (c == ':') || (c == '!') || (c == '.') || (c == ',') || (c == '%') || (c == '$');
    }

    private boolean isNam(int c) {
        char a = (char) c;
        return Character.isLetter((char) c) || a == '_';
    }

    private boolean isNum(int c) {
        return Character.isDigit((char) c);
    }

    private boolean isLP(int c) {
        return (c == '(' || c == '[' || c == '{');
    }

    private boolean isLR(int c) {
        return (c == ')' || c == ']' || c == '}');
    }
}
