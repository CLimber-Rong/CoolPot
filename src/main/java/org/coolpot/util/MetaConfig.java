package org.coolpot.util;

import java.util.HashSet;
import java.util.Set;

public final class MetaConfig {
    private MetaConfig(){}

    public static final String name = Util.dynamic("CoolPot");
    public static final String version = Util.dynamic("v0.0.1");

    public static final String version_info = Util.dynamic(name+" (OpenJDK, Build by plants-os) "+version
    +".\nCopyright 2024 by XIAOYI12, CLimber-Rong.");

    public static boolean disableSFN = false;
    public static boolean disableSTD = false;
    public static boolean enableRuntime = false;
    public static boolean isDebug = false;
    public static boolean isO2 = false;
    public static byte[] bc_version = {0,0,1};

    public static final Set<String> OP_DATA = Set.of("+", "-", "*", "/", ">=", "<=", "==", "!", "&",
            "|", "=", ">", "<", ",", "+=", "-=", "*=", "/=", "%", "%=",
            "++", "--", "&&", "||", "^", ".");

    private static final Set<String> keys = new HashSet<>(){
        {
            add("import");
            add("def");
            add("class");
            add("func");
            add("if");
            add("while");
            add("else");
            add("sfn");
            add("break");
            add("continue");
            add("for");
            add("new");
            add("true");
            add("false");
            add("null");
            add("extends");
            add("in");
        }
    };

    /*
    * [Error info]
    * Type is already defined. - 类型已定义
    * Unable to resolve symbols. - 无法解析符号
    * Type name is not valid. - 无效的类型名称
    * <identifier> expected. - 需要: <标识符>
    * ',' expected. - 需要 ','
    * '=' expected. - 需要 '='
    * '"' expected. - 需要 '"'
    * '(' expected. - 需要 '('
    * Cannot found import library. - 找不到指定库
    * Illegal escape character in string literal. - 字符串中存在非法转义字符
    * Illegal symbol in function define. - 函数定义中存在非法字符
    * Not a statement. - 不是语句
    * Missing function body. - 缺少函数/方法体
    * Missing class body. - 缺少类体
    * Illegal statement. - 非法语句
    * back outside loop. - 退出语句位于循环体外部
    * return outside function. - 返回语句位于方法体外部
     */

    public static boolean isKey(String k) {
        return keys.contains(k);
    }
}
