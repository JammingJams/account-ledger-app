package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class LedgerAccountApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean userInHomescreen = true;
        boolean userInLedger;
        String homeScreenOption;
        String userSelection;
        double priceSelection = 0;
        boolean initialLedger;
        boolean reportLoop;
        ArrayList<Transaction> depositTransactionList = new ArrayList<>();
        ArrayList<Transaction> paymentTransactionList = new ArrayList<>();

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(("HH:mm:ss"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");



        while (userInHomescreen) {
            System.out.println("Welcome to the account ledger");
            System.out.println("(D) Add deposit\n(P) Make Payment (Debit)\n(L) Ledger\n(X) exit");
            initialLedger = true;
            userInLedger = true;
            userSelection = "";
            depositTransactionList.clear();
            paymentTransactionList.clear();
            homeScreenOption = sc.nextLine().trim().toLowerCase();

            switch (homeScreenOption) {

                case ("d") -> DepositsPaymentsWriter.writeDeposit();
                case ("p") -> DepositsPaymentsWriter.writePayment();
                case ("l") -> {
                    while (userInLedger) {
                        reportLoop = true;

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
                            initialLedger = false;
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
                            case ("r") -> {
                                while (reportLoop) {
                                    System.out.println("(1) Month to Date\n(2) Previous Month\n(3) Year to Date\n(4) Previous Year\n(5) Search by Vendor\n(0) Ledger Home Page");
                                    System.out.print("Please type in the number for the task: ");
                                    userSelection = sc.nextLine();

                                    switch (userSelection) {
                                        case ("1") -> {
                                            LocalDate dateNow = LocalDate.now();
                                            String parsedDateNow = dateNow.format(monthFormat);
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
                                        case ("2") -> {
                                            LocalDate dateNow = LocalDate.now().minusMonths(1);
                                            String parsedDateNow = dateNow.format(monthFormat);
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
                                        case ("3") -> {
                                            LocalDate dateNow = LocalDate.now();
                                            String parsedDateNow = dateNow.format(yearFormat);
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
                                        case ("4") -> {
                                            LocalDate dateNow = LocalDate.now().minusYears(1);
                                            String parsedDateNow = dateNow.format(yearFormat);
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
                case ("x") -> userInHomescreen = false;
                default -> System.out.println("Incorrect user input");

            }



        }


    }
}
