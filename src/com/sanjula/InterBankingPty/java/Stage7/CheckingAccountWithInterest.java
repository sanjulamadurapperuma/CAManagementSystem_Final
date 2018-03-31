package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class CheckingAccountWithInterest extends CheckingAccount {
    protected SavingsAccount savingsAccount;

    public CheckingAccountWithInterest(int accountNumber,
                                       double accountBalance, BankBranch homeBranch,
                                       double monthlyFee,
                                       int noOfChecksAllowed,
                                       SavingsAccount savingsAccount) {
        super(accountNumber, accountBalance, homeBranch, monthlyFee, noOfChecksAllowed);
        this.savingsAccount = savingsAccount;
        savingsAccount.setInterestRate(0.0002);
    }

    public CheckingAccountWithInterest(){
        accountBalance = 0.00;
        savingsAccount.setInterestRate(0.00);
    }

    @Override
    protected CheckingAccountWithInterest enterAccountData(Customer customer){
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

        CheckingAccountWithInterest account = new CheckingAccountWithInterest(accountNumber, accBalance,
                homeBranch, monthlyFee, noOfChecksAllowed, savingsAccount);
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

}
