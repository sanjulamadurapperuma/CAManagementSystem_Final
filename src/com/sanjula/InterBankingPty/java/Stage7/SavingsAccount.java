package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class SavingsAccount extends BankAccount {
    protected double interestRate;

    public SavingsAccount(double accountBalance,
                          BankBranch homeBranch) {
        super(accountBalance, homeBranch);
        this.interestRate = 0.03;
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
    protected BankAccount enterAccountData(Customer customer){
        //BankBranch object
        homeBranch = getHomeBranchDetails();

        Scanner sc = new Scanner(System.in);
        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
        while(accBalance < 0 || accBalance > 100000){
            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
        }
        SavingsAccount account = new SavingsAccount(accBalance, homeBranch);
        account.setAccountNumber(accountNumber);
        account.setAccountBalance(accBalance);
        account.setInterestRate(interestRate);
        account.setHomeBranch(homeBranch);

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
    protected void displayAccount(BankAccount account){
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
