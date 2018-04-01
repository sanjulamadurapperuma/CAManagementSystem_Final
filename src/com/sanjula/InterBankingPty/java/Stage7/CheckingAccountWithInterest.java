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

    protected SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    protected void setSavingsAccount(SavingsAccount savingsAccount) {
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
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();

        return account;
    }

}
