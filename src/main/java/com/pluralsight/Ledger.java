package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {

public static void  openLedger(boolean userInLedger, boolean initialLedger, Scanner sc, String userSelection, ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList) {
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
    DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
    boolean reportLoop;
        while (userInLedger) {
            reportLoop = true;
            initialLedger = initializer(initialLedger, depositTransactionList, paymentTransactionList);

            System.out.println("(A) Display all entries\n(D) Display all entries that are deposits\n(P) Display payment entries\n(R) Run pre-defined reports\n(H) Go back to homepage");
            System.out.print("Type your selection here: ");
            userSelection = sc.nextLine().trim().toLowerCase();

            switch (userSelection) {
                case ("a") -> {
                    System.out.println("Deposits!");
                    depositReader(depositTransactionList);
                    System.out.println("Payments!");
                    paymentReader(paymentTransactionList);
                    System.out.println("\n\n\n");
                }
                case ("d") -> depositReader(depositTransactionList);
                case ("p") -> paymentReader(paymentTransactionList);
                case ("r") -> {
                    while (reportLoop) {
                        System.out.println("(1) Month to Date\n(2) Previous Month\n(3) Year to Date\n(4) Previous Year\n(5) Search by Vendor\n(0) Ledger Home Page");
                        System.out.print("Please type in the number for the task: ");
                        userSelection = sc.nextLine();

                        switch (userSelection) {
                            case ("1") -> {
                                LocalDate dateNow = LocalDate.now();
                                String parsedDateNow = dateNow.format(monthFormat);
                                monthComparison(depositTransactionList, paymentTransactionList, monthFormat, dateFormat, timeFormat, parsedDateNow);
                            }
                            case ("2") -> {
                                LocalDate dateNow = LocalDate.now().minusMonths(1);
                                String parsedDateNow = dateNow.format(monthFormat);
                                monthComparison(depositTransactionList, paymentTransactionList, monthFormat, dateFormat, timeFormat, parsedDateNow);
                            }
                            case ("3") -> {
                                LocalDate dateNow = LocalDate.now();
                                String parsedDateNow = dateNow.format(yearFormat);
                                yearComparison(depositTransactionList, paymentTransactionList, yearFormat, dateFormat, timeFormat, parsedDateNow);
                            }
                            case ("4") -> {
                                LocalDate dateNow = LocalDate.now().minusYears(1);
                                String parsedDateNow = dateNow.format(yearFormat);
                                yearComparison(depositTransactionList, paymentTransactionList, yearFormat, dateFormat, timeFormat, parsedDateNow);
                            }
                            case ("5") -> {
                                System.out.println("Select the vendor you want to display: ");
                                userSelection = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
                                for(Transaction i : depositTransactionList) {

                                    if (userSelection.equals(i.getVendor().trim().toLowerCase().replaceAll("\\s+",""))) {
                                        System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
                                    }
                                }
                                for(Transaction i : paymentTransactionList) {
                                    if (userSelection.equals(i.getVendor().trim().toLowerCase().replaceAll("\\s+",""))) {
                                        System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
                                    }
                                }
                            }
                            case ("6") -> {
                                System.out.println("Search Date/End Date/Description/Vendor/Amount");
                                System.out.println("Search for your payment/deposit history with these parameters!: ");
                                userSelection = sc.nextLine().trim().toLowerCase();

                            }
                            case ("0") -> reportLoop = false;
                            default -> System.out.println("Invalid user input");

                        }

                    }
                }
                case ("h") -> userInLedger = false;
                default -> System.out.println("Invalid user input");
            }
        }

    }
    public static boolean initializer(boolean initialLedger, ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList ) {
        while (initialLedger) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
                String line;

                bufferedReader.readLine();

                while ((line = bufferedReader.readLine()) != null) {

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
                        } else {
                            paymentTransactionList.add(transaction);
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            //We would compare dates and allow user to input dates as well
            depositTransactionList.sort(Comparator.comparing(Transaction::getTransactionDate).thenComparing(Transaction::getTransactionTime));
            paymentTransactionList.sort(Comparator.comparing(Transaction::getTransactionDate).thenComparing(Transaction::getTransactionTime));


            initialLedger = false;
        }
        return initialLedger;
    }

    public static void  paymentReader(ArrayList<Transaction> paymentTransactionList) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i = paymentTransactionList.size() - 1; i > -1; i--) {
            System.out.printf("%s|%s|%s|%s|$%.2f\n",paymentTransactionList.get(i).getTransactionDate().format(dateFormat),paymentTransactionList.get(i).getTransactionTime().format(timeFormat),paymentTransactionList.get(i).getTransactionDescription(),paymentTransactionList.get(i).getVendor(),paymentTransactionList.get(i).getPrice());
        }
    }
    public static void depositReader(ArrayList<Transaction> depositTransactionList) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i = depositTransactionList.size() - 1; i > -1; i--) {
            System.out.printf("%s|%s|%s|%s|$%.2f\n",depositTransactionList.get(i).getTransactionDate().format(dateFormat),depositTransactionList.get(i).getTransactionTime().format(timeFormat),depositTransactionList.get(i).getTransactionDescription(),depositTransactionList.get(i).getVendor(),depositTransactionList.get(i).getPrice());
        }
    }
    public static void monthComparison(ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList, DateTimeFormatter monthFormat, DateTimeFormatter dateFormat, DateTimeFormatter timeFormat, String parsedDateNow) {
        for(Transaction i : depositTransactionList) {
            String parsedDepositDate = i.getTransactionDate().format(monthFormat);

            if (parsedDateNow.equals(parsedDepositDate)) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
            }
        }
        for(Transaction i : paymentTransactionList) {
            String parsedPaymentDate = i.getTransactionDate().format(monthFormat);
            if (parsedDateNow.equals(parsedPaymentDate)) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
            }
        }
    }
    public static void yearComparison(ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList, DateTimeFormatter yearFormat, DateTimeFormatter dateFormat, DateTimeFormatter timeFormat, String parsedDateNow) {
        for(Transaction i : depositTransactionList) {
            String parsedDepositDate = i.getTransactionDate().format(yearFormat);

            if (parsedDateNow.equals(parsedDepositDate)) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
            }
        }
        for(Transaction i : paymentTransactionList) {
            String parsedPaymentDate = i.getTransactionDate().format(yearFormat);
            if (parsedDateNow.equals(parsedPaymentDate)) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
            }
        }
    }


}
