package com.sanjula.InterBankingPty.java.Stage7;

public class CheckingAccountWithInterest extends CheckingAccount {
    protected SavingsAccount savingsAccount;

    public CheckingAccountWithInterest(int accountNumber,
                                       double accountBalance, BankBranch homeBranch,
                                       double monthlyFee,
                                       int noOfChecksAllowed,
                                       SavingsAccount savingsAccount) {
        super(accountNumber, accountBalance, homeBranch, monthlyFee, noOfChecksAllowed);
        this.savingsAccount = savingsAccount;
    }



}
