package com.pluralsight;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomSearch {
    public static void search(ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList, String depositType, String paymentType) {
        Scanner sc = new Scanner(System.in);
        double amount;
        String startDateT = "start";
        String endDateT = "end";
        LocalDate startDate = null;
        LocalDate endDate = null;
        System.out.println("Search Date/End Date/Description/Vendor/Amount");
        System.out.println("Search for your payment/deposit history with these parameters!: ");

        startDate = askForDate(startDateT, startDate);

        endDate = askForDate(endDateT, endDate);

        System.out.print("Please enter Item Description: ");
        String description = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");

        System.out.print("Please enter Item Vendor: ");
        String vendor = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");

        while (true) {
            System.out.print("Please enter Item Amount: ");
            try {
                String tempPrice;
                tempPrice = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
                if (tempPrice.isEmpty()) {
                    amount = 0;
                    break;
                }
                else {
                    double parsedPrice = Double.parseDouble(tempPrice);
                    amount = Math.round(parsedPrice * 100.0) / 100.0;
                    break;
                }
            } catch (InputMismatchException|NumberFormatException e) {
                //e.printStackTrace();
                System.out.println(Decor.red + "Invalid input: make sure your only inputting numbers" + Decor.reset);
                Decor.waitAndContinue();
            }
        }
        Decor.bar(); System.out.println("Deposits"); Decor.bar();
        checkInventory(depositTransactionList, startDate, endDate, description, vendor, amount, depositType);
        Decor.bar(); System.out.println("Payments"); Decor.bar();
        checkInventory(paymentTransactionList, startDate, endDate, description, vendor, amount, paymentType);
        Decor.bar(); Decor.waitAndContinue();
    }
    public static void checkInventory(ArrayList<Transaction> depositTransactionList,
                                 LocalDate startDate, LocalDate endDate, String description, String vendor, double amount, String paymentType) {
        boolean matchFound = false;
        for (Transaction i : depositTransactionList) {
            boolean matchesDateRange = (startDate == null || i.getTransactionDate().isAfter(startDate.minusDays(1))) && (endDate == null || i.getTransactionDate().isBefore(endDate.plusDays(1)));
            boolean matchesDescription = description.isEmpty() || i.getTransactionDescription().toLowerCase().trim().replaceAll("\\s+","").contains(description);
            boolean matchesVendor = vendor.isEmpty() || i.getVendor().toLowerCase().trim().replaceAll("\\s+","").contains(vendor);
            boolean matchesAmount = amount == 0 || i.getPrice() == amount;
            if(matchesDateRange && matchesDescription && matchesVendor && matchesAmount) {
                System.out.printf("%s|%s|%s|%s|%.2f\n", i.getTransactionDate().toString(), i.getTransactionTime().toString(), i.getTransactionDescription(), i.getVendor(), i.getPrice()); matchFound = true;
            }
        }
        Decor.checkMatch(matchFound, paymentType);
    }
    public static LocalDate askForDate(String dateCondition, LocalDate startDate) {
        Scanner sc  = new Scanner(System.in);
        while (true) {
            char ch;
            System.out.printf("Please enter %s Date (yyyy-MM-dd): ", dateCondition);
            String startDateT = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
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
                    System.out.println(Decor.red + "Invalid length your date need to contain 8 exactly numbers" + Decor.reset);
                    Decor.waitAndContinue();
                }
            }
            catch (DateTimeParseException e) {
                //e.printStackTrace();
                System.out.println(Decor.red +"Incorrect Format, your format must be (yyyy-MM-dd)" + Decor.reset);
                Decor.waitAndContinue();
            }
        }
        return startDate;
    }

}
