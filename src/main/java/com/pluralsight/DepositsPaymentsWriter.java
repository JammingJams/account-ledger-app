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

            dateTimePref(sc, userInLoop, userSelection, userCurrentDate, userCurrentTime);

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

            dateTimePref(sc, userInLoop, userSelection, userCurrentDate, userCurrentTime);

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


    public static void dateTimePref(Scanner sc, boolean userInLoop, String userSelection, LocalDate userCurrentDate, LocalTime userCurrentTime) {
        System.out.print("Type (A) to automatically assign date and time to current date\nType (M) for manually assigning date and time\nType here: ");
        while (userInLoop) {
            userSelection = sc.nextLine().trim().toLowerCase();
            switch (userSelection) {
                case ("m") -> {
                    System.out.print("Type in the date  with this (yyyy-MM-dd) you want to assign: ");
                    while (true) {
                        userSelection = sc.nextLine().trim();
                        try {
                            userCurrentDate = LocalDate.parse(userSelection);
                            break;
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
                            userCurrentTime = LocalTime.parse(userSelection);
                            userInLoop = false;
                            break;
                        }
                        catch (DateTimeParseException e) {
                            //e.printStackTrace();
                            System.out.println("Incorrect Format, your format must be (HH:mm:ss)");
                            System.out.print("Type in the time with this (HH:mm:ss) correct format: ");
                        }
                    }
                }
                case ("a") -> {
                    userCurrentTime = LocalTime.now().withNano(0);
                    userCurrentDate = LocalDate.now();
                    userInLoop = false;
                }
                default -> System.out.print("Invalid user input,\nType (A) automatically assigns date and time to current date\nType (M) for manually assigning date and time\nType here: ");
            }

        }
    }
}
