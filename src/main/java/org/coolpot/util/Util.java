package org.coolpot.util;

import org.coolpot.util.error.CompilerException;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collector;

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

    public static String readFile(File file){
        try(BufferedReader reader = Files.newBufferedReader(file.toPath())){
            StringBuilder sb = new StringBuilder();
            String buf;
            while ((buf = reader.readLine()) != null)
                sb.append(buf).append('\n');
            return sb.toString();
        }catch (IOException io){
            throw new CompilerException(io,"Error: Cannot read source file!");
        }
    }
}
