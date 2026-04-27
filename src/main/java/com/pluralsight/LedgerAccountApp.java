package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LedgerAccountApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger = true;
        String homeScreenOption = "";
        String userSelection = "";
        double priceSelection = 0;
        ArrayList<Transaction> depositTransactionList = new ArrayList<>();
        ArrayList<Transaction> paymentTransactionList = new ArrayList<>();

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
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

                        LocalTime userCurrentTime = LocalTime.now().withNano(0);
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
                case ("l") -> {
                    while (userInLedger) {

                        depositTransactionList.clear();
                        paymentTransactionList.clear();

                        try {
                            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
                            String line;

                            bufferedReader.readLine();

                            while((line = bufferedReader.readLine()) != null) {

                                    String[] tempArray = line.split("\\|");

                                    Transaction transaction = new Transaction();

                                    try {
                                        LocalDate transactionDate = LocalDate.parse(tempArray[0]);
                                        LocalTime transactionTime = (LocalTime.parse(tempArray[1]));
                                        String transactionDescription = tempArray[2];
                                        String vendor = tempArray[3];
                                        double amount = Double.valueOf(tempArray[4]);

                                        transaction.setTransactionDate(transactionDate);
                                        transaction.setTransactionTime(transactionTime);
                                        transaction.setTransactionDescription(transactionDescription);
                                        transaction.setVendor(vendor);
                                        transaction.setPrice(amount);

                                        if (amount < 0) {
                                            depositTransactionList.add(transaction);
                                        }
                                        else {
                                            paymentTransactionList.add(transaction);
                                        }

                                    }
                                    catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }


                            }

                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("(A) Display all entries\n(D) Display all entries that are deposits\n(P) Display payment entries\n(R) Run pre-defined reports\n(H) Go back to homepage");
                        System.out.print("Type your selection here: ");
                        userSelection = sc.nextLine().trim().toLowerCase();

                        switch (userSelection) {
                            case ("a") -> {
                                System.out.println("Deposits!");
                                for(Transaction i : depositTransactionList) {
                                    System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
                                }
                                System.out.println("Payments!");
                                for(Transaction i : paymentTransactionList) {
                                    System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
                                }
                                System.out.println("\n\n\n");
                            }
                            case ("d") -> {
                                for(Transaction i : depositTransactionList) {
                                    System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
                                }
                            }
                            case ("p") -> {
                                for(Transaction i : paymentTransactionList) {
                                    System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
                                }
                            }
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
