package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class SavingsAccount extends BankAccount {
    protected double interestRate;

    public SavingsAccount(int accountNumber, double accountBalance,
                          double interestRate, BankBranch homeBranch) {
        super(accountNumber, accountBalance, homeBranch);
        this.interestRate = 0.03;
        this.interestRate = interestRate;
    }

    public SavingsAccount(){
        accountBalance = 0.00;
        interestRate = 0.00;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    protected SavingsAccount enterAccountData(Customer customer){
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

        SavingsAccount account = new SavingsAccount(accountNumber, accBalance,
                interestRate, homeBranch);
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

    protected void displayAccount(SavingsAccount account){
        System.out.println("=====The details of your new savings account are : =====");
        System.out.println();
        System.out.println("Savings Account Number : " + account.getAccountNumber());
        System.out.println("Savings Account Balance : $" + account.getAccountBalance());
        System.out.println("Savings Account Interest : " + (interestRate * 100) + "%");
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + getHomeBranch().getBsbNumber());
        System.out.println("Bank Branch Address : " + getHomeBranch().getAddress());
        System.out.println("Bank Branch Postcode : " + getHomeBranch().getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }

}
