package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;
//


public class LedgerAccountApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger;
        String homeScreenOption;
        String userSelection;
        boolean initialLedger;
        ArrayList<Transaction> depositTransactionList = new ArrayList<>();
        ArrayList<Transaction> paymentTransactionList = new ArrayList<>();



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
