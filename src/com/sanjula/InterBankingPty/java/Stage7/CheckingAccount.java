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

    @Override
    protected CheckingAccount enterAccountData(Customer customer){
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

        CheckingAccount account = new CheckingAccount(accountNumber, accBalance,
                homeBranch, monthlyFee, noOfChecksAllowed);
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

    @Override
    public void displayAccount(BankAccount account){
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
