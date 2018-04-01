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
        while(!sc.hasNextDouble()){
            System.out.println("Please enter a valid monthly fee");
            sc.next();
        }
        this.monthlyFee = sc.nextDouble();
        System.out.println();
        System.out.print("Please enter the number of checks allowed per month : ");
        this.noOfChecksAllowed = Main.getSelectedOption();
    }

    public CheckingAccount(){

    }

    protected double getMonthlyFee() {
        return monthlyFee;
    }

    protected void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    protected int getNoOfChecksAllowed() {
        return noOfChecksAllowed;
    }

    protected void setNoOfChecksAllowed(int noOfChecksAllowed) {
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
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();

        return account;
    }

}
