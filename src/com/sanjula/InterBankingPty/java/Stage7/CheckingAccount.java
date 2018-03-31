package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class CheckingAccount extends BankAccount{
    protected double monthlyFee;
    protected int noOfChecksAllowed;

    public CheckingAccount(int accountNumber, double accountBalance,
                           BankBranch homeBranch, double monthlyFee,
                           int noOfChecksAllowed) {
        super(accountNumber, accountBalance, homeBranch);

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the Monthly Fee for the Checking Account : ");
        this.monthlyFee = sc.nextDouble();
        System.out.println();
        System.out.print("Please enter the number of checks allowed per month : ");
        this.noOfChecksAllowed = sc.nextInt();

        this.monthlyFee = monthlyFee;
        this.noOfChecksAllowed = noOfChecksAllowed;
    }

    public CheckingAccount(){

    }

    public void display(CheckingAccount account){
        System.out.println("=====The details of your new Checking account are : =====");
        System.out.println();
        System.out.println("Checking Account Number : " + account.getAccountNumber());
        System.out.println("Checking Account Balance : $" + account.getAccountBalance());
        System.out.println("Checking Account monthly fee : $" + monthlyFee);
        System.out.println("Number of checks allowed per month : " + noOfChecksAllowed);
//        System.out.println("Savings Account Interest : " + (interestRate * 100) + "%");
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + getHomeBranch().getBsbNumber());
        System.out.println("Bank Branch Address : " + getHomeBranch().getAddress());
        System.out.println("Bank Branch Postcode : " + getHomeBranch().getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }
}
