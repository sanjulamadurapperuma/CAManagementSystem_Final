package com.sanjula.InterBankingPty.java.Stage3;

public class BankAccount {
    public int accountNumber;
    public double accountBalance;
    public double interestRate;
    public double automaticDepositAmount;
    public double automaticWithdrawalAmount;



    public BankAccount(int accountNumber, double accountBalance,
                       double interestRate, double automaticDepositAmount,
                       double automaticWithdrawalAmount) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.interestRate = interestRate;
        this.automaticDepositAmount = automaticDepositAmount;
        this.automaticWithdrawalAmount = automaticWithdrawalAmount;
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

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getAutomaticDepositAmount() {
        return automaticDepositAmount;
    }

    public void setAutomaticDepositAmount(double automaticDepositAmount) {
        this.automaticDepositAmount = automaticDepositAmount;
    }

    public double getAutomaticWithdrawalAmount() {
        return automaticWithdrawalAmount;
    }

    public void setAutomaticWithdrawalAmount(double automaticWithdrawalAmount) {
        this.automaticWithdrawalAmount = automaticWithdrawalAmount;
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
