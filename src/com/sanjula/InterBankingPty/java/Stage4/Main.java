package com.sanjula.InterBankingPty.java.Stage4;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SEPARATOR_STRING = "***********************" +
            "***********************************************";
    private static File customerDetails = new File("CustomerDetails.txt");
    private static List<Customer> customerList = new ArrayList<>();
    private static List<BankAccount> bankAccountList = new ArrayList<>();


    public static void main(String[] args){
        customerList = dataLoad();
        int selectedOption;

        Customer customer = login(customerList);

        do{

            selectedOption = displayMenu();

            if(selectedOption == 1){

                int accNumber;
                do{
                    System.out.println("Enter 0 for the account number to stop creating a " +
                            "new bank account");
                    System.out.println();
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter the account number : ");
                    while (!sc.hasNextInt()){
                        System.out.println("Please enter a number between 1000 and 9999.");
                        sc.next();
                    }
                    accNumber = sc.nextInt();

                    if (accNumber == 0){
                        break;
                    }
                    System.out.println();
                    int accBalance = getAccountBalance(sc, "Enter the opening account balance ($): ");
                    while(accBalance < 0 || accBalance > 100000){
                        accBalance = getAccountBalance(sc, "Enter valid opening account balance ($) : ");
                    }
                    System.out.println();
                    double intRate = getInterestRate(sc, "Enter the interest rate : ");
                    while(intRate < 0.01 || intRate > 15.0){
                        intRate = getInterestRate(sc, "Enter a valid interest rate between 0.01% and 15.0% :");
                    }
                    System.out.println();
                    double deposit = validateAutomaticDeposit(sc, "Please enter the amount of " +
                            "dollars and cents you want to deposit automatically every month : $");
                    while(deposit < 0){
                        deposit = validateAutomaticDeposit(sc, "Enter valid deposit amount : $");
                    }
                    System.out.println();
                    double withdrawal = validateAutomaticWithdrawal(sc, "Please enter the amount of " +
                            "dollars and cents you want to withdraw automatically every month : $");
                    while(withdrawal < 0){
                        withdrawal = validateAutomaticWithdrawal(sc, "Enter valid withdrawal amount : $");
                    }
                    System.out.println();
                    BankAccount accountData = new BankAccount();
                    accountData.setAccountNumber(accNumber);
                    accountData.setAccountBalance(accBalance);
                    accountData.setInterestRate(intRate);
                    accountData.setAutomaticDepositAmount(deposit);
                    accountData.setAutomaticWithdrawalAmount(withdrawal);
                    bankAccountList.add(accountData);
                    if (customer != null) {
                        customer.setBankAccountsList(bankAccountList);
                    }
                    customerList.add(customer);
                    System.out.println("Bank Account created successfully.");
                    System.out.println();
                }while(accNumber != 0);

                if (customer != null){
                    for (BankAccount account : customer.getBankAccountsList()){
                        displayAccount(account);
                        computeInterest(account);
                    }
                }

            } else if(selectedOption == 2){

                transfer(customer);

            } else if(selectedOption == 3){
                generateForecastTable(customer);

            } else if (selectedOption == 4){
                Scanner sc = new Scanner(System.in);
                int accNumber = getAccountNumber(sc,
                        "Please enter the account number : ");
                while (accNumber < 1000 || accNumber> 9999){
                    accNumber = getAccountNumber(sc, "Enter valid bank account number : ");
                }

                if (customer != null){
                    for (BankAccount bankAccount : customer.getBankAccountsList()){
                        if (bankAccount.getAccountNumber() == accNumber){
                            computeInterest(bankAccount);
                            break;
                        }
                    }
                }

            }else if (selectedOption == 0){
                dataPersistency(customerList);
                System.exit(0);
            }
        }while (selectedOption !=0);
    }

    private static Customer login(List<Customer> customerList){
        String username;
        char[] password;

        Scanner sc = new Scanner(System.in);
        int selectedOption = displayLoginMenu();
        if(selectedOption == 100){
            String loginName;
            char[] loginPassword;
            boolean isValidCustomer;

            do {
                System.out.println();
                System.out.println("Please sign in to continue.");

                System.out.print("Enter your name : ");
                loginName = sc.nextLine();

                System.out.print("Enter the password : ");
                loginPassword = sc.nextLine().toCharArray();

                for (Customer loginCustomer : customerList) {
                    if (loginCustomer.getName().equals(loginName) &&
                            Arrays.equals(loginCustomer.getLoginPassword(), loginPassword)) {
                        System.out.println(SEPARATOR_STRING);
                        System.out.println("Welcome, " + loginName);
                        System.out.println();
                        isValidCustomer = true;
                        return loginCustomer;
                    }
                }
                isValidCustomer = false;
            } while (!isValidCustomer);
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
            Customer customer = new Customer();

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
        return null;
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
        System.out.println("============ This is the InterBanking " +
                "Pty Customer and Account Management System ============");
        System.out.println("What do you want to do?");
        System.out.println("Enter '1' to create a new Bank Account.");
        System.out.println("Enter '2' to transfer money between bank accounts");
        System.out.println("Enter '3' to generate a forecast for your bank account balance.");
        System.out.println("Enter '4' to forecast the yearly balance of account based on " +
                "the interest rate");
        System.out.println("Enter '0' to exit the program.");
        int selectedOption = getSelectedOption();

        while (selectedOption < 0 || selectedOption > 4){
            System.out.println("Please try again. Enter a " +
                    "number which represents the option you want to select.");
            selectedOption = getSelectedOption();
        }

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
                numberTransferredFrom = transferFrom.getAccountNumber();
                balanceTransferredFrom = transferFrom.getAccountBalance();
            }
            if (bankAccount.getAccountNumber() == toAccountNumber){
                transferTo = bankAccount;
                numberTransferredTo = transferTo.getAccountNumber();
                balanceTransferredTo = transferTo.getAccountBalance();
            }
        }
            if(!(balanceTransferredFrom - amountToTransfer < 0)){
                if (balanceTransferredTo + amountToTransfer > 100000){
                    System.out.println("WARNING : The balance in account " + numberTransferredTo
                            + " increases above $100,000, which is the highest amount " +
                            "that is federally insured.");
                    System.out.println();
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
                    if (transferFrom != null && transferTo != null){
                        BankAccount.transferMoney(transferFrom, transferTo, amountToTransfer);
                        System.out.println("Account " + numberTransferredFrom + " after withdrawal : $"
                                + transferFrom.getAccountBalance());
                        System.out.println("Account " + numberTransferredTo + " after deposit : $"
                                + transferTo.getAccountBalance());
                        System.out.println(SEPARATOR_STRING);
                    }
                }
            } else{
                System.out.println("This transaction is not allowed because the account from " +
                        "which you want to withdraw money has inadequate funds.");
                System.out.println();
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
                interestPerMonth = ((bankAccount.getInterestRate() / 100) / 12);
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

    private static int getNoOfYears(Scanner scanner, String message){
        int noOfYears;
        do{
            System.out.println(message);
            while(!scanner.hasNextInt()){
                System.out.println("Inavlid Input. Please enter a positive number");
                scanner.next();
            }

            noOfYears = scanner.nextInt();
            if (!(noOfYears >= 1)){
                System.out.println("Invalid Number of Years. Please try again");
            }
        }while(noOfYears < 1);
        return noOfYears;
    }

    private static void dataPersistency(List<Customer> customerList){

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(customerDetails);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);


            for (Customer customer : customerList) {
                objectOutputStream.writeObject(customer);
            }
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //READ FROM A FILE
    private static List<Customer> dataLoad(){

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        List<Customer> customerList = new ArrayList<>();
        try{
            fileInputStream = new FileInputStream(customerDetails);
            objectInputStream = new ObjectInputStream(fileInputStream);

            while(fileInputStream.available() > 0){
                customerList.add((Customer) objectInputStream.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error occured");
        } catch (ClassNotFoundException e) {
            System.out.println("Fatal Internal Error");
        }
        return customerList;
    }

    public static BankAccount enterAccountData(Customer customer){
        Scanner sc = new Scanner(System.in);
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

        BankAccount accountData = new BankAccount();
        accountData.setAccountNumber(accNumber);
        accountData.setAccountBalance(accBalance);
        accountData.setInterestRate(intRate);
        accountData.setAutomaticDepositAmount(deposit);
        accountData.setAutomaticWithdrawalAmount(withdrawal);
        bankAccountList.add(accountData);
        customer.setBankAccountsList(bankAccountList);
        customerList.add(customer);
        System.out.println("=====Bank Account created successfully.=====");
        System.out.println();
        return accountData;
    }

    private static void displayAccount(BankAccount account){
        System.out.println("=====The details of your new bank account are : =====");
        System.out.println();
        System.out.println("Bank Account Number : " + account.getAccountNumber());
        System.out.println("Bank Account Balance : $" + account.getAccountBalance());
        System.out.println("Annual Interest Rate (%) : " + account.getInterestRate());
        System.out.println("Automatic Deposit Amount : $" + account.getAutomaticDepositAmount());
        System.out.println("Automatic Withdrawal Amount : $" + account.getAutomaticWithdrawalAmount());
        System.out.println(SEPARATOR_STRING);
    }

    private static void computeInterest(BankAccount account){
        Scanner sc = new Scanner(System.in);

        System.out.println();

        int noOfYears = getNoOfYears(sc,
                "Enter the number of years the account will earn interest : ");
        while(noOfYears < 1){
            noOfYears = getNoOfYears(sc, "Enter valid number of years");
        }

        System.out.printf("%25s", "Account Balance Forecast");
        System.out.println();
        System.out.println("------------------------------");
        System.out.printf("%2s %18s", "| YEAR |", "| ENDING BALANCE |");
        System.out.println();
        System.out.println("------------------------------");

        DecimalFormat decimal = new DecimalFormat("##.##");
        double interestPerYear = (account.getInterestRate() / 100);
        double forecastedBalance = account.getAccountBalance();
        for(int j = 1; j <= noOfYears; j++){
            forecastedBalance += (forecastedBalance * interestPerYear);
            System.out.printf("%2s %18s", j, decimal.format(forecastedBalance));
            System.out.println();
        }
        System.out.println();
    }
}
