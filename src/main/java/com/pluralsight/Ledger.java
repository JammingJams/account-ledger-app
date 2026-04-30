package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {

public static void  openLedger(boolean userInLedger, boolean initialLedger, Scanner sc, String userSelection, ArrayList<Transaction> depositTransactionList, ArrayList<Transaction> paymentTransactionList, String depositType, String paymentType) {
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
    DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
    boolean reportLoop;
        while (userInLedger) {
            reportLoop = true;
            initialLedger = initializer(initialLedger, depositTransactionList, paymentTransactionList);

            System.out.println(Decor.space);
            Decor.ledgerDisplay();
            System.out.println("|(A) ->  Display all entries                    |\n|(D) ->  Display all entries that are deposits  |\n|(P) ->  Display payment entries                |\n|(R) ->  Run pre-defined reports                |\n|(H) ->  Go back to homepage                    |");
            Decor.bar(); System.out.print("Enter character here: ");
            userSelection = sc.nextLine().trim().toLowerCase();

            switch (userSelection) {
                case ("a") -> {
                    Decor.bar(); System.out.println("Deposits!"); Decor.bar();
                    displayDepositsPayments(depositTransactionList);
                    Decor.bar(); System.out.println("Payments!"); Decor.bar();
                    displayDepositsPayments(paymentTransactionList); Decor.bar();
                    Decor.waitAndContinue();
                }
                case ("d") -> {Decor.bar(); displayDepositsPayments(depositTransactionList); Decor.bar(); Decor.waitAndContinue();}
                case ("p") -> {Decor.bar(); displayDepositsPayments(paymentTransactionList); Decor.bar(); Decor.waitAndContinue();}
                case ("r") -> {
                    while (reportLoop) {
                        System.out.println(Decor.space);
                        Decor.reportsDisplay();
                        System.out.println("|(1) ->  Month to Date        |\n|(2) ->  Previous Month       |\n|(3) ->  Year to Date         |\n|(4) ->  Previous Year        |\n|(5) ->  Search by Vendor     |\n|(6) ->  Custom Search        |\n|(0) ->  Ledger Home Page     |");
                        Decor.bar(); System.out.print("Enter character here: ");
                        userSelection = sc.nextLine();

                        switch (userSelection) {
                            case ("1") -> {
                                LocalDate dateNow = LocalDate.now();
                                String parsedDateNow = dateNow.format(monthFormat);
                                Decor.bar(); System.out.println("Deposits!"); monthComparison(depositTransactionList, monthFormat, dateFormat, timeFormat, parsedDateNow, depositType); Decor.bar();
                                Decor.bar(); System.out.println("Payments!"); monthComparison(paymentTransactionList, monthFormat, dateFormat, timeFormat, parsedDateNow, paymentType); Decor.bar();
                                Decor.waitAndContinue();
                            }
                            case ("2") -> {
                                LocalDate dateNow = LocalDate.now().minusMonths(1);
                                String parsedDateNow = dateNow.format(monthFormat);
                                Decor.bar(); System.out.println("Deposits!"); monthComparison(depositTransactionList, monthFormat, dateFormat, timeFormat, parsedDateNow, depositType); Decor.bar();
                                Decor.bar(); System.out.println("Payments!"); monthComparison(paymentTransactionList, monthFormat, dateFormat, timeFormat, parsedDateNow, paymentType); Decor.bar();
                                Decor.waitAndContinue();
                            }
                            case ("3") -> {
                                LocalDate dateNow = LocalDate.now();
                                String parsedDateNow = dateNow.format(yearFormat);
                                Decor.bar(); System.out.println("Deposits!"); yearComparison(depositTransactionList, yearFormat, dateFormat, timeFormat, parsedDateNow, depositType); Decor.bar();
                                Decor.bar(); System.out.println("Payments!"); yearComparison(paymentTransactionList, yearFormat, dateFormat, timeFormat, parsedDateNow, paymentType); Decor.bar();
                                Decor.waitAndContinue();
                            }
                            case ("4") -> {
                                LocalDate dateNow = LocalDate.now().minusYears(1);
                                String parsedDateNow = dateNow.format(yearFormat);
                                Decor.bar(); System.out.println("Deposits!"); yearComparison(depositTransactionList, yearFormat, dateFormat, timeFormat, parsedDateNow, depositType); Decor.bar();
                                Decor.bar(); System.out.println("Payments!"); yearComparison(paymentTransactionList, yearFormat, dateFormat, timeFormat, parsedDateNow, paymentType); Decor.bar();
                                Decor.waitAndContinue();
                            }
                            case ("5") -> {
                                System.out.print("Select the vendor you want to display: ");
                                userSelection = sc.nextLine().trim().toLowerCase().replaceAll("\\s+","");
                                Decor.bar(); System.out.println("Deposits!"); displayVendor(depositTransactionList, userSelection, dateFormat, timeFormat, depositType); Decor.bar();
                                Decor.bar(); System.out.println("Payments!"); displayVendor(paymentTransactionList, userSelection, dateFormat, timeFormat, paymentType); Decor.bar();
                                Decor.waitAndContinue();
                            }
                            case ("6") -> CustomSearch.search(depositTransactionList, paymentTransactionList, depositType, paymentType);
                            case ("0") -> reportLoop = false;
                            default -> {
                                System.out.println(Decor.red +"Invalid user input!" + Decor.reset);
                                Decor.waitAndContinue();
                            }

                        }

                    }
                }
                case ("h") -> userInLedger = false;
                default -> {
                    System.out.println(Decor.red +"Invalid user input!" + Decor.reset);
                    Decor.waitAndContinue();
                }
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

                        if (amount > 0) {
                            depositTransactionList.add(transaction);
                        } else {
                            paymentTransactionList.add(transaction);
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                }

            } catch (IOException | DateTimeParseException e) {
                e.printStackTrace();
            }
            //We would compare dates and allow user to input dates as well
            depositTransactionList.sort(Comparator.comparing(Transaction::getTransactionDate).thenComparing(Transaction::getTransactionTime));
            paymentTransactionList.sort(Comparator.comparing(Transaction::getTransactionDate).thenComparing(Transaction::getTransactionTime));


            initialLedger = false;
        }
        return initialLedger;
    }

    public static void  displayDepositsPayments(ArrayList<Transaction> depositTransactionList) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Collections.reverse(depositTransactionList);
        for(Transaction i : depositTransactionList) {
            System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice());
        }
    }
    public static void monthComparison(ArrayList<Transaction> depositTransactionList, DateTimeFormatter monthFormat, DateTimeFormatter dateFormat, DateTimeFormatter timeFormat, String parsedDateNow, String paymentType) {
        Collections.reverse(depositTransactionList);
        boolean matchFound = false;
        for(Transaction i : depositTransactionList) {
            String parsedDepositDate = i.getTransactionDate().format(monthFormat);

            if (parsedDateNow.equals(parsedDepositDate)) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice()); matchFound = true;
            }
        }
        Decor.checkMatch(matchFound, paymentType);
    }
    public static void yearComparison(ArrayList<Transaction> depositTransactionList, DateTimeFormatter yearFormat, DateTimeFormatter dateFormat, DateTimeFormatter timeFormat, String parsedDateNow, String paymentType) {
        Collections.reverse(depositTransactionList);
        boolean matchFound = false;
        for(Transaction i : depositTransactionList) {
            String parsedDepositDate = i.getTransactionDate().format(yearFormat);

            if (parsedDateNow.equals(parsedDepositDate)) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice()); matchFound = true;
            }
        }
        Decor.checkMatch(matchFound, paymentType);
    }
    public static void displayVendor(ArrayList<Transaction> depositTransactionList, String userSelection, DateTimeFormatter dateFormat, DateTimeFormatter timeFormat, String paymentType) {
    boolean matchFound = false;
        for(Transaction i : depositTransactionList) {

            if (userSelection.equals(i.getVendor().trim().toLowerCase().replaceAll("\\s+",""))) {
                System.out.printf("%s|%s|%s|%s|$%.2f\n",i.getTransactionDate().format(dateFormat),i.getTransactionTime().format(timeFormat),i.getTransactionDescription(),i.getVendor(),i.getPrice()); matchFound = true;
            }
        }
        Decor.checkMatch(matchFound, paymentType);
    }

}
