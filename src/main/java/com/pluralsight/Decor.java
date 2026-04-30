package com.pluralsight;

import java.util.Scanner;

public class Decor {
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String space = "\n\n\n\n\n\n\n";


    public static void waitAndContinue() {
        Scanner sc = new Scanner(System.in);
        try {
            Thread.sleep(2000);
            System.out.print(yellow + "Press [ENTER] to continue: " + reset);
            sc.nextLine();
            System.out.print(space);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static void pause() {
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
