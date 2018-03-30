package com.sanjula.InterBankingPty.java.Stage7;

public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(int accountNumber, double accountBalance,
                          double interestRate, BankBranch homeBranch) {
        super(accountNumber, accountBalance, interestRate, homeBranch);
        interestRate = 0.003;
        this.interestRate = interestRate;
    }

    protected void displayAccount(BankAccount account){
        System.out.println("=====The details of your new savings account are : =====");
        System.out.println();
        System.out.println("Savings Account Number : " + account.getAccountNumber());
        System.out.println("Savings Account Balance : $" + account.getAccountBalance());
        System.out.println("Savings Account Interest : ");
        System.out.println(Main.SEPARATOR_STRING);
    }

}
