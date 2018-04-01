package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class CheckingAccount extends BankAccount{
    protected double monthlyFee;
    protected int noOfChecksAllowed;

    public CheckingAccount(double accountBalance,
                           BankBranch homeBranch) {
        super(accountBalance, homeBranch);

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the Monthly Fee for the Checking Account : ");
        this.monthlyFee = sc.nextDouble();
        System.out.println();
        System.out.print("Please enter the number of checks allowed per month : ");
        this.noOfChecksAllowed = sc.nextInt();
    }

    public CheckingAccount(){

    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public int getNoOfChecksAllowed() {
        return noOfChecksAllowed;
    }

    public void setNoOfChecksAllowed(int noOfChecksAllowed) {
        this.noOfChecksAllowed = noOfChecksAllowed;
    }

    @Override
    protected CheckingAccount enterAccountData(Customer customer){
        Scanner sc = new Scanner(System.in);

        //BankBranch object
        homeBranch = getHomeBranchDetails();

        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
        while(accBalance < 0 || accBalance > 100000){
            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
        }

        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(accountNumber);
        account.setAccountBalance(accBalance);
        account.setMonthlyFee(monthlyFee);
        account.setNoOfChecksAllowed(noOfChecksAllowed);
        account.setHomeBranch(homeBranch);

        Main.bankAccountList.add(account);
        if (customer != null){
            customer.setBankAccountsList(Main.bankAccountList);
        }
        Main.customerList.add(customer);
//        Main.dataPersistency(Main.customerList);
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();

        return account;
    }

    @Override
    public void displayAccount(BankAccount account){
        System.out.println("=====The details of your new Checking account are : =====");
        System.out.println();
        System.out.println("Checking Account Number : " + account.getAccountNumber());
        System.out.println("Checking Account Balance : $" + account.getAccountBalance());
        System.out.println("Checking Account monthly fee : $" + monthlyFee);
        System.out.println("Number of checks allowed per month : " + noOfChecksAllowed);
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + getHomeBranch().getBsbNumber());
        System.out.println("Bank Branch Address : " + getHomeBranch().getAddress());
        System.out.println("Bank Branch Postcode : " + getHomeBranch().getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }
}
