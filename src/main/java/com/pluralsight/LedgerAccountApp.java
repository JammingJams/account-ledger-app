package com.pluralsight;


import java.util.ArrayList;
import java.util.Scanner;


public class LedgerAccountApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger;
        String homeScreenOption;
        String userSelection;
        boolean initialLedger;
        String deposit = "";
        String payment = "-";
        String depositType = "deposit";
        String paymentType = "payment";
        ArrayList<Transaction> depositTransactionList = new ArrayList<>();
        ArrayList<Transaction> paymentTransactionList = new ArrayList<>();


        while (userInHomescreen) {
            System.out.println("Welcome to the Gaming Emporium!");
            //Decor.pause();
            System.out.println("Welcome admin press the correspond character to access parts of this program!");
            System.out.println("|(D) ->      Add Deposit      |\n|(P) ->  Make Payment (Debit) |\n|(L) ->        Ledger         |\n|(X) ->         Exit          |");
            initialLedger = true;
            userInLedger = true;
            userSelection = "";
            depositTransactionList.clear();
            paymentTransactionList.clear();
            System.out.print("Enter character here: ");
            homeScreenOption = sc.nextLine().trim().toLowerCase();

            switch (homeScreenOption) {

                case ("d") -> DepositsPaymentsWriter.write(sc,deposit,depositType);
                case ("p") -> DepositsPaymentsWriter.write(sc,payment,paymentType);
                case ("l") -> Ledger.openLedger(userInLedger, initialLedger, sc, userSelection, depositTransactionList, paymentTransactionList, depositType, paymentType);
                case ("x") -> userInHomescreen = false;
                default -> System.out.println("Incorrect user input");
            }
        }
    }
}
