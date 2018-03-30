package com.sanjula.InterBankingPty.java.Stage7;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class BankAccount implements Serializable {
    private int accountNumber;
    private double accountBalance;
    private double automaticDepositAmount;
    private double automaticWithdrawalAmount;
    protected double interestRate;
    private static boolean depositPending = true;
    private static boolean withdrawPending = true;
    private BankBranch homeBranch;

    public BankAccount(int accountNumber, double accountBalance,
                       double automaticDepositAmount,
                       double automaticWithdrawalAmount,
                       BankBranch homeBranch) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.automaticDepositAmount = automaticDepositAmount;
        this.automaticWithdrawalAmount = automaticWithdrawalAmount;
        this.homeBranch = homeBranch;
    }

    //Overloaded constructor
    public BankAccount(int accountNumber, double accountBalance,
                       double interestRate, BankBranch homeBranch){
        //Do while accountNumber is in the bankAccountsList
        //Generate random number while > 1000 and  < 9999
        //For each account in the bankAccountsList check whether random number equals any bank account's number
        //If not assign random number to current account number
        boolean isTrue = false;
        do {
            Random accNumber = new Random();
            int randomAccNum = accNumber.nextInt((9999 - 1000) + 1) + 1000;
            for (BankAccount bankAccount : Main.bankAccountList) {
                if((randomAccNum != bankAccount.getAccountNumber())){
                    randomAccNum = accountNumber;

                } else{
//                    accountNumber = 0;
                    isTrue = true;
                }
            }
        }while (!isTrue);
        this.accountNumber = accountNumber;
        System.out.println("Your account number is : " + accountNumber);
        this.accountBalance = accountBalance;
        this.interestRate = interestRate;
        this.homeBranch = homeBranch;
    }

    public BankAccount(){
        accountNumber = 0;
        interestRate = 0;
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

    public BankBranch getHomeBranch() {
        return homeBranch;
    }

    public void setHomeBranch(BankBranch homeBranch) {
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
                + accountBalance + "\nAutomatic Deposit Amount : "
                + automaticDepositAmount +
                "\nAutomatic Withdrawal Amount : "
                + automaticWithdrawalAmount;
    }

//    protected BankAccount enterAccountData(Customer customer){
//        Scanner sc = new Scanner(System.in);
//
////        int accNumber = Main.getAccountNumber(sc, "Enter the account number : ");
////        while(accNumber < 1000 || accNumber > 9999){
////            accNumber = Main.getAccountNumber(sc, "Enter valid account number : ");
////        }
//
//        //Auto-generating account number between 1000 and 9999
//
////        if(Main.bankAccountList != null){
////            BankAccount bankAccount = Main.bankAccountList.get(Main.bankAccountList.size() - 1);
////            int previousNumber = bankAccount.getAccountNumber();
////            if (previousNumber < 9999){
////                accountNumber = ++previousNumber;
////            }
////        } else{
////            System.out.println("No bank account has been created");
////        }
//
//
//        System.out.println("Your account number is : " + accountNumber);
//
//        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
//        while(accBalance < 0 || accBalance > 100000){
//            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
//        }
//
//        double deposit = Main.validateAutomaticDeposit(sc, "Please enter the amount of " +
//                "dollars and cents you want to deposit automatically every month : $");
//        while(deposit < 0){
//            deposit = Main.validateAutomaticDeposit(sc, "Enter valid deposit amount : $");
//        }
//
//        double withdrawal = Main.validateAutomaticWithdrawal(sc, "Please enter the amount of " +
//                "dollars and cents you want to withdraw automatically every month : $");
//        while(withdrawal < 0){
//            withdrawal = Main.validateAutomaticWithdrawal(sc, "Enter valid withdrawal amount : $");
//        }
//
//        //Setting the collected details to the variables of the objects
//        //and finally adding the bank account to the array list
//        BankAccount accountData = new BankAccount();//This holds the new bank account
//        accountData.setAccountNumber(accountNumber);
//        accountData.setAccountBalance(accBalance);
//        accountData.setAutomaticDepositAmount(deposit);
//        accountData.setAutomaticWithdrawalAmount(withdrawal);
//        Main.bankAccountList.add(accountData);
//        if (customer != null) {
//            customer.setBankAccountsList(Main.bankAccountList);
//        }
////        customer.setBankAccountsList(Main.bankAccountList);
//        Main.customerList.add(customer);
//        Main.dataPersistency(Main.customerList);
//        System.out.println("=====Bank Account created successfully.=====");
//        System.out.println();
//        return accountData;
//    }

    protected BankAccount enterAccountData(Customer customer){
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
        System.out.println("Enter the address : ");
        String address = sc.nextLine();
        System.out.println("Enter the postcode : ");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid postcode number");
            sc.next();
        }
        int postcode = sc.nextInt();

        //Filling the homeBranch object
        homeBranch.setBsbNumber(bsbNumber);
        homeBranch.setAddress(address);
        homeBranch.setPostcode(postcode);


        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
        while(accBalance < 0 || accBalance > 100000){
            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
        }

        BankAccount account = new BankAccount(accountNumber, accBalance, interestRate, homeBranch);
        Main.bankAccountList.add(account);
        if (customer != null){
            customer.setBankAccountsList(Main.bankAccountList);
        }
        Main.customerList.add(customer);
        Main.dataPersistency(Main.customerList);
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();

        return account;
    }


    protected void displayAccount(BankAccount account){
        System.out.println("=====The details of your new bank account are : =====");
        System.out.println();
        System.out.println("Bank Account Number : " + account.getAccountNumber());
        System.out.println("Bank Account Balance : $" + account.getAccountBalance());
        System.out.println(Main.SEPARATOR_STRING);
    }
}
