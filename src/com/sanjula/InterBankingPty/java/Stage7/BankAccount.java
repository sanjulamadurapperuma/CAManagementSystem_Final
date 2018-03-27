package com.sanjula.InterBankingPty.java.Stage7;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Scanner;

public class BankAccount implements Serializable {
    private int accountNumber;
    private double accountBalance;
    private double automaticDepositAmount;
    private double automaticWithdrawalAmount;
    protected static final double INTEREST_RATE = 0.03;
    private static boolean depositPending = true;
    private static boolean withdrawPending = true;

    public BankAccount(int accountNumber, double accountBalance,
                       double automaticDepositAmount,
                       double automaticWithdrawalAmount) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.automaticDepositAmount = automaticDepositAmount;
        this.automaticWithdrawalAmount = automaticWithdrawalAmount;
    }

    public BankAccount(){

    }

    protected int getAccountNumber() {
        return accountNumber;
    }

    protected void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    protected double getAccountBalance() {
        return accountBalance;
    }

    protected void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    protected double getAutomaticDepositAmount() {
        return automaticDepositAmount;
    }

    private void setAutomaticDepositAmount(double automaticDepositAmount) {
        this.automaticDepositAmount = automaticDepositAmount;
    }

    protected double getAutomaticWithdrawalAmount() {
        return automaticWithdrawalAmount;
    }

    private void setAutomaticWithdrawalAmount(double automaticWithdrawalAmount) {
        this.automaticWithdrawalAmount = automaticWithdrawalAmount;
    }

    private void deposit(double depositAmount){
        accountBalance += depositAmount;
        depositPending = false;
    }

    private void withdraw(double withdrawAmount){
        accountBalance -= withdrawAmount;
        withdrawPending = false;
    }

    public static void transferMoney(BankAccount transferFrom, BankAccount transferTo, double byAmount){
        depositPending = true;
        withdrawPending = true;
        transferFrom.withdraw(byAmount);
        if (!withdrawPending) {
            transferTo.deposit(byAmount);
            if (!depositPending) {
                System.out.println("Transfer successful");
            } else {
                transferFrom.deposit(byAmount);
                System.out.println("Transfer unsuccessful. Transaction rollbacked.");
            }
        }
    }

    @Override
    public String toString() {
        return "Details of Bank Account - "
                + accountNumber + "\nAccount Balance : "
                + accountBalance + "\nAutomatic Deposit Amount : "
                + automaticDepositAmount +
                "\nAutomatic Withdrawal Amount : "
                + automaticWithdrawalAmount;
    }

    protected static BankAccount enterAccountData(Customer customer){
        Scanner sc = new Scanner(System.in);
        int accNumber = Main.getAccountNumber(sc, "Enter the account number : ");
        while(accNumber < 1000 || accNumber > 9999){
            accNumber = Main.getAccountNumber(sc, "Enter valid account number : ");
        }

        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
        while(accBalance < 0 || accBalance > 100000){
            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
        }

        double deposit = Main.validateAutomaticDeposit(sc, "Please enter the amount of " +
                "dollars and cents you want to deposit automatically every month : $");
        while(deposit < 0){
            deposit = Main.validateAutomaticDeposit(sc, "Enter valid deposit amount : $");
        }

        double withdrawal = Main.validateAutomaticWithdrawal(sc, "Please enter the amount of " +
                "dollars and cents you want to withdraw automatically every month : $");
        while(withdrawal < 0){
            withdrawal = Main.validateAutomaticWithdrawal(sc, "Enter valid withdrawal amount : $");
        }

        //Setting the collected details to the variables of the objects
        //and finally adding the bank account to the array list
        BankAccount accountData = new BankAccount();//This holds the new bank account
        accountData.setAccountNumber(accNumber);
        accountData.setAccountBalance(accBalance);
        accountData.setAutomaticDepositAmount(deposit);
        accountData.setAutomaticWithdrawalAmount(withdrawal);
        Main.bankAccountList.add(accountData);
        if (customer != null) {
            customer.setBankAccountsList(Main.bankAccountList);
        }
//        customer.setBankAccountsList(Main.bankAccountList);
        Main.customerList.add(customer);
        Main.dataPersistency(Main.customerList);
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();
        return accountData;
    }

    protected void computeInterest(BankAccount account, int noOfYears){
        System.out.println();

        System.out.printf("%25s", "Account Balance Forecast");
        System.out.println();
        System.out.println("------------------------------");
        System.out.printf("%2s %18s", "| YEAR |", "| ENDING BALANCE |");
        System.out.println();
        System.out.println("------------------------------");

        DecimalFormat decimal = new DecimalFormat("##.##");
        double forecastedBalance = account.getAccountBalance();
        for(int j = 1; j <= noOfYears; j++){
            forecastedBalance += (forecastedBalance * INTEREST_RATE);
            System.out.printf("%2s %18s", j, decimal.format(forecastedBalance));
            System.out.println();
        }
        System.out.println();
    }

    /*Overloaded method for generating yearly forecast*/
    protected static void computeInterest(BankAccount[] accounts, int count, int noOfYears){
        System.out.println();

        for (int i = 0; i < count; i++) {
            System.out.printf("%25s", "Account Balance Forecast for account : "
                    + accounts[i].getAccountNumber());
            System.out.println();
            System.out.println("------------------------------");
            System.out.printf("%2s %18s", "| YEAR |", "| ENDING BALANCE($) |");
            System.out.println();
            System.out.println("------------------------------");

            DecimalFormat decimal = new DecimalFormat("##.##");
            double forecastedBalance = accounts[i].getAccountBalance();
            for(int j = 1; j <= noOfYears; j++){
                forecastedBalance += (forecastedBalance * INTEREST_RATE);
                System.out.printf("%2s %18s", j, decimal.format(forecastedBalance));
                System.out.println();
            }
            System.out.println();
        }

    }

    protected void displayAccount(BankAccount account){
        System.out.println("=====The details of your new bank account are : =====");
        System.out.println();
        System.out.println("Bank Account Number : " + account.getAccountNumber());
        System.out.println("Bank Account Balance : $" + account.getAccountBalance());
        System.out.println("Annual Interest Rate (%) : " + (INTEREST_RATE * 100));
        System.out.println("Automatic Deposit Amount : $" + account.getAutomaticDepositAmount());
        System.out.println("Automatic Withdrawal Amount : $" + account.getAutomaticWithdrawalAmount());
        System.out.println(Main.SEPARATOR_STRING);
    }
}
