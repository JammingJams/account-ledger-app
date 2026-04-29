package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DepositsPaymentsWriter {
    public static void writePayment(Scanner sc) {
        boolean userInLoop = true;
        String userSelection = "";
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));
            double tempPrice;
            double notParsedPrice;

            System.out.print("What type of payment are you making?: ");
            String transactionDescription = sc.nextLine().trim();
            System.out.print("What vendor is receiving the payment?: ");
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

            String dateAndTime = "";

            dateAndTime = dateTimePref(sc, userInLoop, userSelection, userCurrentDate, userCurrentTime, dateAndTime);

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

    public static void writeDeposit(Scanner sc) {
        boolean userInLoop = true;
        String userSelection = "";
        try {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));
            double tempPrice;
            double notParsedPrice;

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

            String dateAndTime = "";

            dateAndTime = dateTimePref(sc, userInLoop, userSelection, userCurrentDate, userCurrentTime, dateAndTime);

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


    public static String dateTimePref(Scanner sc, boolean userInLoop, String userSelection, LocalDate userCurrentDate, LocalTime userCurrentTime, String dateAndTime) {
        System.out.print("Type (A) to automatically assign date and time to current date\nType (M) for manually assigning date and time\nType here: ");
        char ch = ' ';
        while (true) {
            userSelection = sc.nextLine().trim().toLowerCase();
            switch (userSelection) {
                case ("m") -> {
                    System.out.print("Type in the date  with this (yyyy-MM-dd) you want to assign: ");
                    while (true) {
                        userSelection = sc.nextLine().trim();
                        try {
                            if(userSelection.length() == 8) {
                                ch = '-';
                                StringBuilder sb = new StringBuilder(userSelection);
                                sb.insert(4, ch);
                                sb.insert(7,ch);
                                userCurrentDate = LocalDate.parse(sb.toString());
                                break;

                            }
                            else if (userSelection.contains("-")) {
                                userCurrentDate = LocalDate.parse(userSelection);
                                break;
                            }
                            else {
                                System.out.println("Invalid length your date need to contain 8 exactly numbers: ");
                            }
                        }
                        catch (DateTimeParseException e) {
                            //e.printStackTrace();
                            System.out.println("Incorrect Format, your format must be (yyyy-MM-dd)");
                            System.out.print("Type in the date with this (yyyy-MM-dd) correct format: ");
                        }
                    }
                    System.out.print("Type in the time with this (HH:mm:ss) you want to assign: ");
                    while (true) {
                        userSelection = sc.nextLine().trim();
                        try {
                            if(userSelection.length() == 6) {
                                ch = ':';
                                StringBuilder sb = new StringBuilder(userSelection);
                                sb.insert(2, ch);
                                System.out.println(sb.toString());
                                sb.insert(5,ch);
                                System.out.println(sb.toString());
                                userCurrentTime = LocalTime.parse(sb.toString());
                                break;

                            }
                            else if (userSelection.contains(":")) {
                                userCurrentTime = LocalTime.parse(userSelection);
                                break;
                            }
                            else {
                                System.out.println("Invalid length your date need to contain 6 exactly numbers: ");
                            }
                        }
                        catch (DateTimeParseException e) {
                            //e.printStackTrace();
                            System.out.println("Incorrect Format, your format must be (HH:mm:ss)");
                            System.out.print("Type in the time with this (HH:mm:ss) correct format: ");
                        }
                    }
                    return userCurrentDate.toString() + "|" + userCurrentTime.toString();
                }
                case ("a") -> {
                    userCurrentTime = LocalTime.now().withNano(0);
                    userCurrentDate = LocalDate.now();
                    userInLoop = false;
                    return userCurrentDate.toString() + "|" + userCurrentTime.toString();
                }
                default -> System.out.print("Invalid user input,\nType (A) automatically assigns date and time to current date\nType (M) for manually assigning date and time\nType here: ");
            }

        }

    }
}
