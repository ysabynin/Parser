package com.ysabynin;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        Parser yyparser;
        if (args.length > 0) {
            yyparser = new Parser(new FileReader(args[0]));
            yyparser.yyparse();
        } else {
            System.out.println("Some of the arguments are missed");
        }
    }
}
