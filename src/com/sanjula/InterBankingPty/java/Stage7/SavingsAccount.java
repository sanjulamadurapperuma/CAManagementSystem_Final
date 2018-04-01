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

    protected double getInterestRate() {
        return interestRate;
    }

    protected void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    protected SavingsAccount enterAccountData(Customer customer){
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
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();

        return account;
    }

}
