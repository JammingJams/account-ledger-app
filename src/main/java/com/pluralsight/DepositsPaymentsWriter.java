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
    public static void write(Scanner sc, String minus, String paymentType) {
        boolean userInLoop = true;
        String userSelection = "";
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));
            double tempPrice;
            double notParsedPrice;

            System.out.printf("What type of %s are you making?: ",paymentType);
            String transactionDescription = sc.nextLine().trim();
            System.out.printf("What vendor is receiving the %s?: ",paymentType);
            String vendor = sc.nextLine().trim();
            while (true) {
                System.out.print("What is the amount of money your receiving?: " );
                try {
                    tempPrice = sc.nextDouble();
                    notParsedPrice = Math.round(tempPrice * 100.0) / 100.0;
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    //e.printStackTrace();
                    System.out.println(Decor.red + "Invalid input: make sure your only inputting numbers" + Decor.reset);
                    Decor.waitAndContinue();
                    sc.nextLine();
                }
            }
            LocalTime userCurrentTime = LocalTime.now().withSecond(2).withNano(0);
            LocalDate userCurrentDate = LocalDate.now();

            String dateAndTime = "";

            dateAndTime = dateTimePref(sc, userInLoop, userSelection, userCurrentDate, userCurrentTime, dateAndTime);

            String price = String.valueOf(notParsedPrice);

            String depositTransaction = dateAndTime + "|" + transactionDescription + "|" + vendor + "|" + minus + price;
            bufferedWriter.write(depositTransaction);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.printf(Decor.green + "Successfully created a %s!\n" + Decor.reset, paymentType);
            Decor.waitAndContinue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String dateTimePref(Scanner sc, boolean userInLoop, String userSelection, LocalDate userCurrentDate, LocalTime userCurrentTime, String dateAndTime) {
        System.out.print("Type (A) to automatically assign date and time to current date\nType (M) for manually assigning date and time\nType here: ");
        userSelection = sc.nextLine().trim();
        char ch;
        while (true) {
            switch (userSelection) {
                case ("m") -> {
                    while (true) {
                        System.out.print("Type in the date  with this (yyyy-MM-dd) you want to assign: ");
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
                                System.out.println(Decor.red + "Invalid length your date need to contain 8 exactly numbers!" + Decor.yellow);
                                Decor.waitAndContinue();
                            }
                        }
                        catch (DateTimeParseException e) {
                            //e.printStackTrace();
                            System.out.println(Decor.red + "Incorrect Format, your format must be (yyyy-MM-dd)!"+ Decor.yellow);
                            Decor.waitAndContinue();
                        }
                    }
                    while (true) {
                        System.out.print("Type in the time with this (HH:mm:ss) you want to assign: ");
                        userSelection = sc.nextLine().trim();
                        try {
                            if(userSelection.length() == 6) {
                                ch = ':';
                                StringBuilder sb = new StringBuilder(userSelection);
                                sb.insert(2, ch);
                                sb.insert(5,ch);
                                userCurrentTime = LocalTime.parse(sb.toString());
                                break;
                            }
                            else if (userSelection.contains(":")) {
                                userCurrentTime = LocalTime.parse(userSelection);
                                break;
                            }
                            else {
                                System.out.println(Decor.red + "Invalid length your date need to contain 6 exactly numbers!" + Decor.reset);
                                Decor.waitAndContinue();
                            }
                        }
                        catch (DateTimeParseException e) {
                            //e.printStackTrace();
                            System.out.println(Decor.red + "Incorrect Format, your format must be (HH:mm:ss)!" + Decor.reset);
                            Decor.waitAndContinue();
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
                default -> {
                    System.out.println(Decor.red + "Invalid user input!" + Decor.reset);
                    Decor.waitAndContinue();
                    System.out.print("Type (A) to automatically assign date and time to current date\nType (M) for manually assigning date and time\nType here: ");
                    userSelection = sc.nextLine().trim();
                }

            }

        }

    }
}
