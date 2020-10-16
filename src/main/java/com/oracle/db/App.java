package com.oracle.db;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Companies companies = new Companies();
        if (args.length == 2) {
            companies.getScript(args[0], args[1]);
        } else if (args.length == 1) {
            companies.getScript(args[0], "output.txt");
        } else {
            companies.getScript("partner.xml", "output.txt");
        }

    }
}
