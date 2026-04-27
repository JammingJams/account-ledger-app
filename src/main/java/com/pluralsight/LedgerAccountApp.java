package com.pluralsight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LedgerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger = true;
        String homeScreenOption = "";

        System.out.println("Welcome to the account ledger");
        System.out.println("(D) Add deposit\n(P) Make Payment (Debit)\n(L) Ledger\n(X) exit");



        while (userInHomescreen) {
            homeScreenOption = sc.nextLine().trim().toLowerCase();

            switch (homeScreenOption) {

                case ("d") -> {}
                case ("p") -> {}
                case ("l") -> {
                    while (userInLedger) {
                        System.out.println("(A)");

                    }
                }
                case ("x") -> {userInHomescreen = false;}
                default -> {System.out.println("Incorrect user input");}

            }






        }


    }
}
