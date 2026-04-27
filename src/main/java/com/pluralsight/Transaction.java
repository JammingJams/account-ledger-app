package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private String transactionDescription;
    private String vendor;
    private Double price;

    public Transaction() {
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.transactionDescription = transactionDescription;
        this.vendor = vendor;
        this.price = price;
    }

    //These are the getters
    public LocalDate getTransactionDate() {return transactionDate;}
    public LocalTime getTransactionTime() {return transactionTime;}
    public String getTransactionDescription() {return transactionDescription;}
    public String getVendor() {return vendor;}
    public Double getPrice() {return price;}

    //These are the setters
    public void setTransactionDate(LocalDate transactionDate) {this.transactionDate = transactionDate;}
    public void setTransactionTime(LocalTime transactionTime) {this.transactionTime = transactionTime;}
    public void setTransactionDescription(String transactionDescription) {this.transactionDescription = transactionDescription;}
    public void setVendor(String vendor) {this.vendor = vendor;}
    public void setPrice(Double price) {this.price = price;}


}
