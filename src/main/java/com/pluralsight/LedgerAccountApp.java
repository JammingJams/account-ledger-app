package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class LedgerAccountApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger;
        String homeScreenOption;
        String userSelection;
        double priceSelection = 0;
        boolean initialLedger;
        boolean reportLoop;
        ArrayList<Transaction> depositTransactionList = new ArrayList<>();
        ArrayList<Transaction> paymentTransactionList = new ArrayList<>();

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");



        while (userInHomescreen) {
            System.out.println("Welcome to the account ledger");
            System.out.println("(D) Add deposit\n(P) Make Payment (Debit)\n(L) Ledger\n(X) exit");
            initialLedger = true;
            userInLedger = true;
            userSelection = "";
            depositTransactionList.clear();
            paymentTransactionList.clear();
            homeScreenOption = sc.nextLine().trim().toLowerCase();

            switch (homeScreenOption) {

                case ("d") -> DepositsPaymentsWriter.writeDeposit(sc);
                case ("p") -> DepositsPaymentsWriter.writePayment(sc);
                case ("l") -> Ledger.openLedger(userInLedger, initialLedger, sc, userSelection, depositTransactionList, paymentTransactionList);
                case ("x") -> userInHomescreen = false;
                default -> System.out.println("Incorrect user input");
            }
        }
    }
}
