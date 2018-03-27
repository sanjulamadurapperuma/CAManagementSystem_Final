package com.sanjula.InterBankingPty.java.Stage2;

public class BankAccount {
    public int accountNumber;
    public double accountBalance;


    public BankAccount(int accountNumber, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public BankAccount(){

    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void deposit(double depositAmount){
        accountBalance += depositAmount;
    }

    public void withdraw(double withdrawAmount){
        accountBalance -= withdrawAmount;
    }

    public static void transferMoney(BankAccount transferFrom, BankAccount transferTo, double byAmount){
        transferFrom.withdraw(byAmount);
        transferTo.deposit(byAmount);
    }
}
