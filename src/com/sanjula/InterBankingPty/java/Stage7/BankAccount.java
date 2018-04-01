package com.sanjula.InterBankingPty.java.Stage7;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class BankAccount implements Serializable {
    protected int accountNumber;
    protected double accountBalance;
    protected static boolean depositPending = true;
    protected static boolean withdrawPending = true;
    protected BankBranch homeBranch;

    public BankAccount(){
    }

    //Overloaded constructor
    public BankAccount(double accountBalance,
                       BankBranch homeBranch){
        //Do while accountNumber is in the bankAccountsList
        //Generate random number while > 1000 and  < 9999
        //For each account in the bankAccountsList check whether random number equals any bank account's number
        //If not assign random number to current account number
        boolean isAccountNumberExists = false;
        int randomAccNum;
        do {
            Random accNumber = new Random();
            randomAccNum = accNumber.nextInt((9999 - 1000) + 1) + 1000;
            for (BankAccount bankAccount : Main.bankAccountList) {
                if((randomAccNum == bankAccount.getAccountNumber())){
                    isAccountNumberExists = true;
                }
            }
        }while (isAccountNumberExists);
        this.accountNumber = randomAccNum;
        this.accountBalance = accountBalance;
        this.homeBranch = homeBranch;
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

    protected BankBranch getHomeBranch() {
        return homeBranch;
    }

    protected void setHomeBranch(BankBranch homeBranch) {
        this.homeBranch = homeBranch;
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
                + accountBalance;
    }

    protected BankAccount enterAccountData(Customer customer){
        //BankBranch object
        homeBranch = getHomeBranchDetails();

        Scanner sc = new Scanner(System.in);
        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
        System.out.println("accBalance " + accBalance);
        while(accBalance < 0 || accBalance > 100000){
            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
        }

        BankAccount account = new BankAccount(accBalance, homeBranch);
        account.setAccountNumber(accountNumber);
        account.setAccountBalance(accBalance);
        account.setHomeBranch(homeBranch);
        Main.bankAccountList.add(account);
        if (customer != null){
            customer.setBankAccountsList(Main.bankAccountList);
        }
        Main.customerList.add(customer);
//        Main.dataPersistency(Main.customerList);
        System.out.println();
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();

        return account;
    }

    protected BankBranch getHomeBranchDetails(){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("=================================");
        System.out.println("Please enter details of the Bank Branch");
        System.out.println();
        System.out.print("Enter BSB Number : ");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a 6-digit number");
            sc.next();
        }
        int bsbNumber = sc.nextInt();
        System.out.print("Enter the address : ");
        sc.nextLine();
        String address = sc.nextLine();
        System.out.print("Enter the postcode : ");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid postcode number");
            sc.next();
        }
        int postcode = sc.nextInt();

        BankBranch branch = new BankBranch(bsbNumber, address, postcode);
        setHomeBranch(branch);
        return homeBranch;
    }


    protected void displayAccount(int accountNumber, double accountBalance){
        System.out.println("=====The details of your new bank account are : =====");
        System.out.println();
        System.out.println("Bank Account Number : " + accountNumber);
        System.out.println("Bank Account Balance : $" + accountBalance);
        System.out.println(Main.SEPARATOR_STRING);
    }

    //Overloaded method for Savings Accounts
    protected void displayAccount(int accountNumber, double accountBalance, double interestRate, BankBranch homeBranch) {
        System.out.println("=====The details of your new savings account are : =====");
        System.out.println();
        System.out.println("Savings Account Number : " + accountNumber);
        System.out.println("Savings Account Balance : $" + accountBalance);
        System.out.println("Savings Account Interest : " + (interestRate * 100) + "%");
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + homeBranch.getBsbNumber());
        System.out.println("Bank Branch Address : " + homeBranch.getAddress());
        System.out.println("Bank Branch Postcode : " + homeBranch.getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }

    //Overloaded method for Checking Accounts
    protected void displayAccount(int accountNumber, double accountBalance, double monthlyFee,
                                  double noOfChecksAllowed, BankBranch homeBranch){
        System.out.println("=====The details of your new Checking account are : =====");
        System.out.println();
        System.out.println("Checking Account Number : " + accountNumber);
        System.out.println("Checking Account Balance : $" + accountBalance);
        System.out.println("Checking Account monthly fee : $" + monthlyFee);
        System.out.println("Number of checks allowed per month : " + noOfChecksAllowed);
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + homeBranch.getBsbNumber());
        System.out.println("Bank Branch Address : " + homeBranch.getAddress());
        System.out.println("Bank Branch Postcode : " + homeBranch.getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }

    //Overloaded method for Checking Accounts with Interest
    protected void displayAccount(int accountNumber, double accountBalance, double interestRate,
                                  double monthlyFee, int noOfChecksAllowed, BankBranch homeBranch){
        System.out.println("=====The details of your new Checking account with interest are : =====");
        System.out.println();
        System.out.println("Checking Account Number : " + accountNumber);
        System.out.println("Checking Account Balance : $" + accountBalance);
        System.out.println("Checking Account Interest Rate : " + (interestRate * 100) + "%");
        System.out.println("Checking Account monthly fee : $" + monthlyFee);
        System.out.println("Number of checks allowed per month : " + noOfChecksAllowed);
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + homeBranch.getBsbNumber());
        System.out.println("Bank Branch Address : " + homeBranch.getAddress());
        System.out.println("Bank Branch Postcode : " + homeBranch.getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }
}
