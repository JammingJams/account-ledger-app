package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomSearch {
    public static void search(ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList) {
        Scanner sc = new Scanner(System.in);
        double amount;
        String startDateT = "";
        String endDateT = "";
        LocalDate startDate = null;
        LocalDate endDate = null;
        System.out.println("Search Date/End Date/Description/Vendor/Amount");
        System.out.println("Search for your payment/deposit history with these parameters!: ");

        System.out.print("Please enter Start Date: ");
        startDate = askForDate(startDateT, startDate);

        System.out.print("Please enter End Date: ");
        endDate = askForDate(endDateT, endDate);

        System.out.print("Please enter Item Description: ");
        String description = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");

        System.out.print("Please enter Item Vendor: ");
        String vendor = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");

        System.out.print("Please enter Item Amount: ");
        while (true) {
            try {
                double tempPrice;
                tempPrice = sc.nextDouble();
                amount = Math.round(tempPrice * 100.0) / 100.0;
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                //e.printStackTrace();
                System.out.print("Hey please input a valid number not a string!: ");
                sc.nextLine();
            }
        }
        System.out.println("Deposits");
        checkInventory(depositTransactionList, startDate, endDate, description, vendor, amount);
        System.out.println("Payments");
        checkInventory(paymentTransactionList, startDate, endDate, description, vendor, amount);
    }
    public static void checkInventory(ArrayList<Transaction> depositTransactionList,
                                 LocalDate startDate, LocalDate endDate, String description, String vendor, double amount) {
        for (Transaction i : depositTransactionList) {
            boolean matchesDateRange = (startDate == null || i.getTransactionDate().isAfter(startDate.minusDays(1))) && (endDate == null || i.getTransactionDate().isBefore(endDate.plusDays(1)));
            boolean matchesDescription = description.isEmpty() || i.getTransactionDescription().toLowerCase().trim().replaceAll("\\s+","").contains(description);
            boolean matchesVendor = vendor.isEmpty() || i.getVendor().toLowerCase().trim().replaceAll("\\s+","").contains(vendor);
            boolean matchesAmount = amount == 0 || i.getPrice() == amount;
            if(matchesDateRange && matchesDescription && matchesVendor && matchesAmount) {
                System.out.printf("%s|%s|%s|%s|%.2f\n", i.getTransactionDate().toString(), i.getTransactionTime().toString(), i.getTransactionDescription(), i.getVendor(), i.getPrice());
            }
        }
    }
    public static LocalDate askForDate(String startDateT, LocalDate startDate) {
        Scanner sc  = new Scanner(System.in);
        while (true) {
            char ch;
            startDateT = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
            try {
                if (startDateT.isEmpty()) {
                    break;
                }
                else if(startDateT.length() == 8) {
                    ch = '-';
                    StringBuilder sb = new StringBuilder(startDateT);
                    sb.insert(4, ch);
                    sb.insert(7,ch);
                    startDate = LocalDate.parse(sb.toString());
                    break;
                }
                else if (startDateT.contains("-")) {
                    startDate = LocalDate.parse(startDateT);
                    break;
                }
                else {
                    System.out.print("Invalid length your date need to contain 8 exactly numbers: ");
                }
            }
            catch (DateTimeParseException e) {
                //e.printStackTrace();
                System.out.println("Incorrect Format, your format must be (yyyy-MM-dd)");
                System.out.print("Type in the date with this (yyyy-MM-dd) correct format: ");
            }
        }
        return startDate;
    }

}
