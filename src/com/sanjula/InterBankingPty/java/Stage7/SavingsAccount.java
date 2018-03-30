package com.sanjula.InterBankingPty.java.Stage7;

public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(int accountNumber, double accountBalance,
                          double interestRate, BankBranch homeBranch) {
        super(accountNumber, accountBalance, homeBranch);
        this.interestRate = 0.03;
        this.interestRate = interestRate;
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
