package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DepositsPaymentsWriter {
    public static void writePayment() {
        Scanner sc = new Scanner(System.in);
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

    public static void writeDeposit() {
        try {
            Scanner sc = new Scanner(System.in);
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

}
