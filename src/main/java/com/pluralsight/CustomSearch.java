package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomSearch {
    public static void search(ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList) {
        Scanner sc = new Scanner(System.in);
        double amount = 0;
        LocalDate startDate = null;
        LocalDate endDate = null;
        System.out.println("Search Date/End Date/Description/Vendor/Amount");
        System.out.println("Search for your payment/deposit history with these parameters!: ");
        System.out.print("Please enter Start Date: ");
        while (true) {
            String startDateT = sc.nextLine().trim().toLowerCase().replaceAll("\\s+", "");
            try {
                if (!startDateT.isEmpty()) {
                    startDate = LocalDate.parse(startDateT);
                }
                break;
            }
            catch (DateTimeParseException e) {
                System.out.print("Hey this is an invalid date put in a valid date: ");
            }
        }
        System.out.print("Please enter End Date: ");
        String endDateT = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
        while (true) {
            try {
                if (!endDateT.isEmpty()) {
                    endDate = LocalDate.parse(endDateT);
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.print("Hey this is an invalid date put in a valid date: ");
            }
        }
        System.out.print("Please enter Item Description: ");
        String description = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
        System.out.print("Please enter Item Vendor: ");
        String vendor = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
        System.out.print("Please enter Item Amount: ");
        while (true) {
            try {
                double tempPrice = 0;
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
        //Is LocalStartDate within or at LocalEndDate//
        checkDate(depositTransactionList, paymentTransactionList, startDate, endDate, description, vendor, amount);

    }
    public static void checkDate(ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList,
                                 LocalDate startDate, LocalDate endDate, String description, String vendor, double amount) {
        for (Transaction i : depositTransactionList) {
            boolean matchesDateRange = (startDate == null || i.getTransactionDate().isAfter(startDate.minusDays(1))) && (endDate == null || i.getTransactionDate().isBefore(endDate.plusDays(1)));
            boolean matchesDescription = description.isEmpty() || i.getTransactionDescription().toLowerCase().trim().replaceAll("\\s+","").equals(description);
            boolean matchesVendor = vendor.isEmpty() || i.getVendor().toLowerCase().trim().replaceAll("\\s+","").equals(vendor);
            boolean matchesAmount = amount == 0 || i.getPrice() == amount;
            if(matchesDateRange && matchesDescription && matchesVendor && matchesAmount) {
                System.out.printf("%s|%s|%s|%s|%.2f\n", i.getTransactionDate().toString(), i.getTransactionTime().toString(), i.getTransactionDescription(), i.getVendor(), i.getPrice());
            }

        }
        for (Transaction i : paymentTransactionList) {
            boolean matchesDateRange = (startDate == null || i.getTransactionDate().isAfter(startDate.minusDays(1))) && (endDate == null || i.getTransactionDate().isBefore(endDate.plusDays(1)));
            boolean matchesDescription = description.isEmpty() || i.getTransactionDescription().toLowerCase().trim().replaceAll("\\s+","").equals(description);
            boolean matchesVendor = vendor.isEmpty() || i.getVendor().toLowerCase().trim().replaceAll("\\s+","").equals(vendor);
            boolean matchesAmount = amount == 0 || i.getPrice() == amount;
            if(matchesDateRange && matchesDescription && matchesVendor && matchesAmount) {
                System.out.printf("%s|%s|%s|%s|%.2f\n", i.getTransactionDate().toString(), i.getTransactionTime().toString(), i.getTransactionDescription(), i.getVendor(), i.getPrice());
            }

        }
    }

}
