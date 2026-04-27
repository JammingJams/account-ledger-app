package com.pluralsight;

import javax.swing.tree.TreeCellRenderer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LedgerAccountApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger = true;
        String homeScreenOption = "";
        String userSelection = "";
        double priceSelection = 0;
        HashMap<String, Transaction> depositTransactionHashMap = new HashMap<>();
        HashMap<String, Transaction> paymentTransactionHashMap = new HashMap<>();

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("hh:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        /*Transaction obj = new Transaction();
        obj.setPrice(90.00);
        transactionHashMap.put("Hello world", obj);*/



        System.out.println("Welcome to the account ledger");
        System.out.println("(D) Add deposit\n(P) Make Payment (Debit)\n(L) Ledger\n(X) exit");



        while (userInHomescreen) {
            userInLedger = true;
            userSelection = "";
            homeScreenOption = sc.nextLine().trim().toLowerCase();

            switch (homeScreenOption) {

                case ("d") -> {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));
                        Transaction depositTransaction = new Transaction();
                        LocalTime userCurrentTime = LocalTime.now().withSecond(2).withNano(0);
                        LocalDate userCurrentDate = LocalDate.now();

                        String parsedTime = userCurrentTime.format(timeFormat);
                        String parsedDate = userCurrentDate.format(dateFormat);
                        String dateAndTime = parsedDate + "|" + parsedTime;


                        //This area sets the new objects datatypes
                        depositTransaction.setTransactionDate(userCurrentDate);
                        depositTransaction.setTransactionTime(userCurrentTime);
                        System.out.print("Put in what you are paying for: ");
                        depositTransaction.setTransactionDescription(userSelection = sc.nextLine().trim());
                        System.out.print("Put in what who you are buying from: ");
                        depositTransaction.setVendor(userSelection = sc.nextLine().trim());
                        System.out.print("How much does this product cost?: ");

                        //this catches any user misinputs
                        while (true) {
                            try {
                                priceSelection = sc.nextDouble();
                                depositTransaction.setPrice(priceSelection = Math.round(priceSelection * 100.0) / 100.0);
                                sc.nextLine();
                                break;
                            } catch (InputMismatchException e) {
                                //e.printStackTrace();
                                System.out.print("Hey please input a valid number not a string!: ");
                                sc.nextLine();
                            }
                        }
                        //This hashmap is no longer needed
                        depositTransactionHashMap.put(dateAndTime, depositTransaction);

                        String forLog = String.valueOf(depositTransaction.getPrice());
                        String depositLog = dateAndTime + "|" + depositTransaction.getTransactionDescription() + "|" + depositTransaction.getVendor() + "|" + forLog;
                        bufferedWriter.write(depositLog);
                        bufferedWriter.newLine();
                        bufferedWriter.close();

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                case ("p") -> {}
                case ("l") -> {
                    while (userInLedger) {
                        System.out.println("(A) Display all entries\n(D) Display all entries that are deposits\n(P) Display negative entries\n(R) Run pre-defined reports\n(H) Go back to homepage");
                        System.out.print("Type your selection here: ");
                        userSelection = sc.nextLine().trim().toLowerCase();

                        switch (userSelection) {
                            case ("a") -> {}
                            case ("d") -> {}
                            case ("p") -> {}
                            case ("r") -> {}
                            case ("h") -> {userInLedger = false;}
                            default -> {System.out.println("Invalid user input");}
                        }
                    }
                }
                case ("x") -> {userInHomescreen = false;}
                default -> {System.out.println("Incorrect user input");}

            }



        }


    }
}
