package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class CheckingAccountWithInterest extends CheckingAccount {
    protected SavingsAccount savingsAccount;

    public CheckingAccountWithInterest(double accountBalance, BankBranch homeBranch,
                                       SavingsAccount savingsAccount) {
        super(accountBalance, homeBranch);
        this.savingsAccount = savingsAccount;
        savingsAccount.setInterestRate(0.0002);
    }

    public CheckingAccountWithInterest(){
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    @Override
    protected CheckingAccountWithInterest enterAccountData(Customer customer){
        Scanner sc = new Scanner(System.in);

        //BankBranch object
        homeBranch = getHomeBranchDetails();


        double accBalance = Main.getAccountBalance(sc, "Enter the opening account balance ($): ");
        while(accBalance < 0 || accBalance > 100000){
            accBalance = Main.getAccountBalance(sc, "Enter valid opening account balance ($) : ");
        }

        CheckingAccountWithInterest account = new CheckingAccountWithInterest();
        account.setAccountNumber(accountNumber);
        account.setMonthlyFee(monthlyFee);
        account.setNoOfChecksAllowed(noOfChecksAllowed);
        account.setAccountBalance(accBalance);
        account.setHomeBranch(homeBranch);
        account.setSavingsAccount(savingsAccount);

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
        System.out.println("Checking Account Interest Rate : " + (savingsAccount.getInterestRate() * 100) + "%");
        System.out.println("Checking Account monthly fee : $" + monthlyFee);
        System.out.println("Number of checks allowed per month : " + noOfChecksAllowed);
        System.out.println();
        System.out.println("Bank Branch BSB Number : " + getHomeBranch().getBsbNumber());
        System.out.println("Bank Branch Address : " + getHomeBranch().getAddress());
        System.out.println("Bank Branch Postcode : " + getHomeBranch().getPostcode());
        System.out.println(Main.SEPARATOR_STRING);
    }

}
