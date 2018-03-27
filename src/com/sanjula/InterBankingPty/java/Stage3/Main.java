package com.sanjula.InterBankingPty.java.Stage3;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SEPARATOR_STRING = "***********************" +
            "***********************************************";
    public static void main(String[] args){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        int selectedOption;

        login(customerList);

        do{
            Scanner sc = new Scanner(System.in);

            selectedOption = displayMenu();

            if(selectedOption == 1){
                String continue2;

                do{
                    int accNumber = getAccountNumber(sc, "Enter the account number : ");
                    while(accNumber < 1000 || accNumber > 9999){
                        accNumber = getAccountNumber(sc, "Enter valid account number : ");
                    }

                    int accBalance = getAccountBalance(sc, "Enter the opening account balance ($): ");
                    while(accBalance < 0 || accBalance > 100000){
                        accBalance = getAccountBalance(sc, "Enter valid opening account balance ($) : ");
                    }

                    double intRate = getInterestRate(sc, "Enter the interest rate : ");
                    while(intRate < 0.01 || intRate > 15.0){
                        intRate = getInterestRate(sc, "Enter a valid interest rate between 0.01% and 15.0% :");
                    }

                    BankAccount account = new BankAccount();
                    account.setAccountNumber(accNumber);
                    account.setAccountBalance(accBalance);
                    account.setInterestRate(intRate);
                    customer.addBankAccount(account);
                    System.out.println("Bank Account created successfully.");

                    System.out.println("Do you want to add another bank account? If yes enter 'Yes', else 'No'");
                    sc.nextLine();
                    continue2 = sc.nextLine();

                }while(!continue2.equalsIgnoreCase("No"));

                System.out.println("The details of your bank accounts are : ");
                List<BankAccount> list = customer.getBankAccountsList();

                for (BankAccount bankAccount : list) {

                    System.out.println("Bank Account Number : " + bankAccount.getAccountNumber());
                    System.out.println("Bank Account Balance " + bankAccount.getAccountBalance());
                    System.out.println("Annual Interest Rate : " + bankAccount.getInterestRate());
                    System.out.println(SEPARATOR_STRING);
                }
            } else if(selectedOption == 2){

                transfer(customer);

            } else if(selectedOption == 3){

                automaticTransaction(customer);

            } else if (selectedOption == 4){
                generateForecastTable(customer);
            }
        }while (selectedOption !=0);
    }

    private static void login(List<Customer> customerList){
        String username;
        char[] password;

        Scanner sc = new Scanner(System.in);
        int selectedOption = displayLoginMenu();
        Customer customer = new Customer();

        if(selectedOption == 100){
            String loginName;
            char[] loginPassword;
            for (Customer loginCustomer : customerList) {
                do {
                    System.out.println();
                    System.out.println("Please sign in to continue.");

                    System.out.print("Enter your name : ");
                     loginName = sc.nextLine();

                    System.out.print("Enter the password : ");
                    loginPassword = sc.nextLine().toCharArray();
                }while(!(loginCustomer.getName().equals(loginName) && Arrays.equals(loginCustomer.getLoginPassword(), loginPassword)));

                if (loginCustomer.getName().equals(loginName) &&
                        Arrays.equals(loginCustomer.getLoginPassword(), loginPassword)){
                    System.out.println(SEPARATOR_STRING);
                    System.out.println("Welcome, " + loginName);
                    System.out.println();
                }
            }
        } else if (selectedOption == 101){

            do{
                System.out.println();
                System.out.println("Please note that only authorised users " +
                        "can create a new customer account.");

                System.out.println("Enter credentials with administrator privileges to continue.");

                System.out.print("Enter the username : ");
                username = sc.nextLine();

                System.out.print("Enter the password : ");
                password = sc.nextLine().toCharArray();

            }while(!username.equals("admin") || !String.valueOf(password).equals("admin123"));
            System.out.println();
            System.out.println("Authentication Successful.");

            System.out.println("Create a new customer account. ");
            System.out.print("Enter the customer name : ");
            customer.setName(sc.nextLine());

            System.out.print("Enter the password : ");
            customer.setLoginPassword(sc.nextLine().toCharArray());

            System.out.print("Enter phone number : ");
            customer.setPhoneNumber(sc.next());

            System.out.print("Enter email address : ");
            customer.setEmailAddress(sc.next());

            customerList.add(customer);
            System.out.println();
            System.out.println("Customer Account created successfully.");
            System.out.println(SEPARATOR_STRING);
            System.out.println("Now sign in with the new customer account : ");
            login(customerList);
        } else{
            System.out.println("You don't have permission to create a new customer account");
        }
    }


    private static int displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the InterBanking " +
                "Pty Customer and Account Management System");
        int selectedOption = getSelectedOption(scanner, "Enter '100' to sign in \n" +
                "Enter '101' to register a new account");

        return selectedOption;
    }

    private static int displayMenu(){
        System.out.println("This is the InterBanking " +
                "Pty Customer and Account Management System");
        System.out.println("What do you want to do?");
        System.out.println("Enter '1' to create a new Bank Account.");
        System.out.println("Enter '2' to transfer money between bank accounts");
        System.out.println("Enter '3' to set an automatic deposit and withdrawal for a bank account");
        System.out.println("Enter '4' to generate a forecast for your bank account balance.");
        System.out.println("Enter '0' to exit the program.");
        int selectedOption = getSelectedOption();

        return selectedOption;
    }

    private static int getSelectedOption(Scanner scanner, String message){
        int selectedOption;

        do{
            System.out.println(message);
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input, please enter one of the numbers specified : 100 or 101");
                scanner.next();
            }

            selectedOption = scanner.nextInt();
            if(selectedOption != 100 && selectedOption != 101){
                System.out.println("Invalid option number!!! It has to be either 100 or 101.");
            }

        }while(selectedOption != 100 && selectedOption != 101);

        return selectedOption;
    }

    private static int getSelectedOption(){
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()){
            System.out.println("Please enter a valid number.");
            sc.next();
        }
        return sc.nextInt();
    }

    private static int getAccountNumber(Scanner scanner, String message){
        int accountNumber;
        do{
            System.out.print(message);
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input, please enter a number within the range of 1000 to 9999");
                scanner.next();
            }
            accountNumber = scanner.nextInt();
            if(!(accountNumber >= 1000 && accountNumber <= 9999)){
                System.out.println("Invalid Account Number!!! It has to be within the range of 1000 to 9999");
            }
        }while(accountNumber < 1000 || accountNumber > 9999);
        return accountNumber;
    }

    private static int getAccountBalance(Scanner scanner, String message){
        int accountBalance;
        do{
            System.out.print(message);
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input, please enter a positive number.");
                scanner.next();
            }
            accountBalance = scanner.nextInt();

            if(!(accountBalance >= 0 || accountBalance <= 100000)){
                System.out.println("Invalid account balance!!! It has to be a positive " +
                        "number and be within the range of $0 to $100000");
            }

        }while(accountBalance < 0 || accountBalance > 100000);

        return accountBalance;
    }

    private static double getInterestRate(Scanner scanner, String message){
        double interestRate;
        do {
            System.out.print(message);
            while(!scanner.hasNextDouble()){
                System.out.println("Invalid input, please enter a number.");
                scanner.next();
            }
            interestRate = scanner.nextDouble();
            if(!(interestRate >= 0.01 || interestRate <= 15.0)){
                System.out.println("Invalid interest rate!!! It has to be a positive number.");
            }
        }while (interestRate < 0.01  || interestRate > 15.0);
        return interestRate;
    }

    private static void transfer(Customer customer){
        Scanner scanner = new Scanner(System.in);
        BankAccount transferFrom = null;
        BankAccount transferTo = null;
        int numberTransferredFrom = 0;
        int numberTransferredTo = 0;
        double balanceTransferredFrom = 0;
        double balanceTransferredTo = 0;
        System.out.print("Enter the account number from which you want to transfer money from :");
        int fromAccountNumber = scanner.nextInt();
        System.out.print("Enter the account number you want to transfer money to : ");
        int toAccountNumber = scanner.nextInt();
        System.out.print("Enter the amount you wish to transfer : $");
        double amountToTransfer = scanner.nextDouble();

        for (BankAccount bankAccount : customer.getBankAccountsList()) {
            if(bankAccount.getAccountNumber() == fromAccountNumber){
                transferFrom = bankAccount;
                numberTransferredFrom = bankAccount.getAccountNumber();
                balanceTransferredFrom = bankAccount.getAccountBalance();
            }
            if (bankAccount.getAccountNumber() == toAccountNumber){
                transferTo = bankAccount;
                numberTransferredTo = bankAccount.getAccountNumber();
                balanceTransferredTo = bankAccount.getAccountBalance();
            }
        }

        if(!(balanceTransferredFrom - amountToTransfer < 0)){
            if (balanceTransferredTo + amountToTransfer > 100000){
                System.out.println("WARNING : The balance in account " + numberTransferredTo
                        + " increases above $100,000, which is the highest amount " +
                        "that is federally insured.");
            }
            if (balanceTransferredFrom - amountToTransfer < 10){
                System.out.println("WARNING : The balance in account " + numberTransferredFrom +
                        " falls below $10.00 as a result of this transaction.");
                System.out.println();
            }
            if(numberTransferredFrom == numberTransferredTo){
                System.out.println("You are not allowed to transfer money between the same account");
            } else{
                System.out.println();
                BankAccount.transferMoney(transferFrom, transferTo, amountToTransfer);
                System.out.println("Account " + numberTransferredFrom + " after withdrawal : "
                        + transferFrom.getAccountBalance());
                System.out.println("Account " + numberTransferredTo + " after deposit : "
                        + transferTo.getAccountBalance());
                System.out.println(SEPARATOR_STRING);
            }
        } else{
            System.out.println("This transaction is not allowed because the account from " +
                    "which you want to withdraw money has inadequate funds.");
        }
    }

    private static void automaticTransaction(Customer customer){
        Scanner sc = new Scanner(System.in);
        int accNumber = getAccountNumber(sc,
                "Please enter the account number you want " +
                        "to deposit to automatically, every month : ");
        while(accNumber < 1000 || accNumber > 9999){
            accNumber = getAccountNumber(sc, "Enter valid bank account number : ");
        }
        double deposit = validateAutomaticDeposit(sc, "Please enter the amount of " +
                "dollars and cents you want to deposit automatically every month : $");
        while(deposit < 0){
            deposit = validateAutomaticDeposit(sc, "Enter valid deposit amount : $");
        }

        double withdrawal = validateAutomaticWithdrawal(sc, "Please enter the amount of " +
                "dollars and cents you want to withdraw automatically every month : $");
        while(withdrawal < 0){
            withdrawal = validateAutomaticWithdrawal(sc, "Enter valid withdrawal amount : $");
        }
        for (BankAccount bankAccount : customer.getBankAccountsList()) {
            if (bankAccount.getAccountNumber() == accNumber){
                bankAccount.setAutomaticDepositAmount(deposit);
                bankAccount.setAutomaticWithdrawalAmount(withdrawal);
            }
        }
    }

    private static double validateAutomaticDeposit(Scanner scanner, String message){
        double depositAmount;

        do {
            System.out.print(message);
            while(!scanner.hasNextDouble()){
                System.out.println("Invalid deposit amount. Please enter a number.");
                scanner.next();
            }
            depositAmount = scanner.nextDouble();
            if (!(depositAmount >= 0)){
                System.out.println("Invalid deposit amount. Please enter a positive deposit amount.");
            }
        }while(depositAmount < 0);
        return depositAmount;
    }

    private static double validateAutomaticWithdrawal(Scanner scanner, String message){
        double withdrawalAmount;
        do{
            System.out.print(message);
            while(!scanner.hasNextDouble()){
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            withdrawalAmount = scanner.nextDouble();
            if(!(withdrawalAmount >= 0)){
                System.out.println("Invalid deposit amount. Please enter a positive deposit amount.");
            }
        }while (withdrawalAmount < 0);

        return withdrawalAmount;
    }

    private static void generateForecastTable(Customer customer){
        Scanner scanner = new Scanner(System.in);
        int accNumber = getAccountNumber(scanner,
                "Please enter the account number : ");
        while (accNumber < 1000 || accNumber> 9999){
            accNumber = getAccountNumber(scanner, "Enter valid bank account number : ");
        }
        int noOfMonths = getNoOfMonths(scanner, "Enter the number of " +
                "months you want the forecast to be calculated for : ");
        while(noOfMonths < 1){
            noOfMonths = getNoOfMonths(scanner, "Enter valid number of months : ");
        }

        System.out.printf("%40s", "Account Balance Forecast");
        System.out.println();
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%5s %10s %20s %20s", "YEAR", "MONTH", "STARTING BALANCE", "ENDING BALANCE");
        System.out.println();
        System.out.println("-----------------------------------------------------------");


        DecimalFormat decimal = new DecimalFormat("##.##");
        double interestPerMonth;
        for (BankAccount bankAccount : customer.getBankAccountsList()) {
            if (bankAccount.getAccountNumber() == accNumber){
                interestPerMonth = (bankAccount.getInterestRate() / 12);
                double forecastedBalance = bankAccount.getAccountBalance();
                for (int j = 1; j <= noOfMonths; j++){
                    double startingBalance = forecastedBalance;
                    forecastedBalance += (forecastedBalance * interestPerMonth) +
                            bankAccount.getAutomaticDepositAmount() -
                            bankAccount.getAutomaticWithdrawalAmount();

                    int yearNo = j/12;
                    System.out.printf("%5s %10s %20s %20s", yearNo, j, decimal.format(startingBalance),
                            decimal.format(forecastedBalance));
                    System.out.println();

                }
            }

        }
        System.out.println();
    }

    private static int getNoOfMonths(Scanner scanner, String message){
        int noOfMonths;
        do{
            System.out.print(message);
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input. Please enter a number from 1 - 12.");
                scanner.next();
            }
            noOfMonths = scanner.nextInt();
            if (!(noOfMonths >= 1)){
                System.out.println("Invalid month number. Please try again.");
            }
        }while(noOfMonths < 1);
        return noOfMonths;
    }

    private static void fileHandler(Customer customer){
        /*Please note : Method invocation and the separation of the
        * reading and writing tasks will be performed during later stages of this coursework.
        * This is given the assumption that the requirement is to only write a simple Java method to
        * read and write from a text file.*/

        File bankAccountDetails = new File("BankAccountDetailsOf" + customer.getName() + ".txt");

        //write to a file
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(bankAccountDetails);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (BankAccount bankAccount : customer.getBankAccountsList()){
                objectOutputStream.writeObject(bankAccount);
            }
        } catch (FileNotFoundException e) {
            System.out.println("A file was not found");
        } catch (IOException e) {
            System.out.println("There was an error initialising the stream");
        }
        finally {
            try{
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //read from a file
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            fileInputStream = new FileInputStream(bankAccountDetails);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while(true){
                BankAccount bankAccount = (BankAccount)objectInputStream.readObject();
                System.out.println(bankAccount);
            }
        } catch (FileNotFoundException e) {
            System.out.println("A file was not found");
        } catch (IOException e) {
            System.out.println("There was an error initialising the stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}