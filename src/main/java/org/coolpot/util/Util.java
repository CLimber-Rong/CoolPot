package org.coolpot.util;

import org.coolpot.util.error.CompilerException;

import java.io.*;

public class Util {

    public static int dynamic(int value) {
        return value;
    }

    public static <T> T dynamic(T value) {
        return value;
    }

    public static InputStream getResource(String path) {
        String pathx;
        if (path.startsWith("/")) pathx = path;
        else pathx = "/" + path;
        return Util.class.getResourceAsStream(pathx);
    }

    public static char[] readFile(File file){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            StringBuilder sb = new StringBuilder();
            String buffer;
            while ((buffer = reader.readLine()) != null)
                sb.append(buffer).append('\n');
            return sb.toString().toCharArray();
        }catch (IOException io){
            throw new CompilerException(io,"Error: Cannot read source file!");
        }
    }
}
