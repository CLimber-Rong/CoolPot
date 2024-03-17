package org.coolpot.util;

import java.util.HashSet;
import java.util.Set;

public final class MetaConfig {
    private MetaConfig(){}

    public static final String name = Util.dynamic("CoolPot");
    public static final String version = Util.dynamic("v0.0.1");

    public static final String version_info = Util.dynamic(name+" (OpenJDK, Build by plants-os) "+version
    +".\nCopyright 2024 by XIAOYI12, CLimber-Rong.");

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
        }
    };

    public static boolean isKey(String k) {
        return keys.contains(k);
    }
}
