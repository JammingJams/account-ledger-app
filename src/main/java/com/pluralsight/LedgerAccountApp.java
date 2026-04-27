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
        //Remove the hashmap from Deposit
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




        while (userInHomescreen) {
            System.out.println("Welcome to the account ledger");
            System.out.println("(D) Add deposit\n(P) Make Payment (Debit)\n(L) Ledger\n(X) exit");
            userInLedger = true;
            userSelection = "";
            homeScreenOption = sc.nextLine().trim().toLowerCase();

            switch (homeScreenOption) {

                case ("d") -> {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));
                        double tempPrice = 0;
                        double notParsedPrice = 0;

                        System.out.print("What product are you buying?: ");
                        String transactionDescription = sc.nextLine().trim();
                        System.out.print("What is the product's vendor?: ");
                        String vendor = sc.nextLine().trim();
                        System.out.print("What is the amount of money your paying?: " );
                        while (true) {
                            try {
                                tempPrice = sc.nextDouble();
                                notParsedPrice = Math.round(tempPrice * 100.0) / 100.0;
                                sc.nextLine();
                                break;
                            } catch (InputMismatchException e) {
                                //e.printStackTrace();
                                System.out.print("Hey please input a valid number not a string!: ");
                                sc.nextLine();
                            }
                        }

                        LocalTime userCurrentTime = LocalTime.now().withSecond(2).withNano(0);
                        LocalDate userCurrentDate = LocalDate.now();

                        String parsedTime = userCurrentTime.format(timeFormat);
                        String parsedDate = userCurrentDate.format(dateFormat);
                        String dateAndTime = parsedDate + "|" + parsedTime;

                        String price = String.valueOf(notParsedPrice);

                        String depositTransaction = dateAndTime + "|" + transactionDescription + "|" + vendor + "|-" + price;
                        bufferedWriter.write(depositTransaction);
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        System.out.println("\n\n\n");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                case ("p") -> {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));
                        double tempPrice = 0;
                        double notParsedPrice = 0;

                        System.out.print("Why are you receiving money?: ");
                        String transactionDescription = sc.nextLine().trim();
                        System.out.print("Who is giving you the money?: ");
                        String vendor = sc.nextLine().trim();
                        System.out.print("What is the amount of money your receiving?: " );
                        while (true) {
                            try {
                                tempPrice = sc.nextDouble();
                                notParsedPrice = Math.round(tempPrice * 100.0) / 100.0;
                                sc.nextLine();
                                break;
                            } catch (InputMismatchException e) {
                                //e.printStackTrace();
                                System.out.print("Hey please input a valid number not a string!: ");
                                sc.nextLine();
                            }
                        }

                        LocalTime userCurrentTime = LocalTime.now().withSecond(2).withNano(0);
                        LocalDate userCurrentDate = LocalDate.now();

                        String parsedTime = userCurrentTime.format(timeFormat);
                        String parsedDate = userCurrentDate.format(dateFormat);
                        String dateAndTime = parsedDate + "|" + parsedTime;

                        String price = String.valueOf(notParsedPrice);

                        String depositTransaction = dateAndTime + "|" + transactionDescription + "|" + vendor + "|" + price;
                        bufferedWriter.write(depositTransaction);
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        System.out.println("\n\n\n");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }
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
