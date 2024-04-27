package com.example.expense_manager.models;

public class account {
    private double accountAmount;
    private String accountName;

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public account() {
    }

    public account(double accountAmount, String accountName) {
        this.accountAmount = accountAmount;
        this.accountName = accountName;
    }
}
