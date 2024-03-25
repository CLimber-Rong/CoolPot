package org.coolpot.test;

import org.coolpot.Main;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        Main.main(new String[]{"-d","--nostd","script.st"});
    }
}
