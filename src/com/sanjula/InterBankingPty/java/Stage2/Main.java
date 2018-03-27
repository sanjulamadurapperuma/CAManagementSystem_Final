package com.sanjula.InterBankingPty.java.Stage2;

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
                    while(accBalance < 0){
                        accBalance = getAccountBalance(sc, "Enter valid opening account balance ($) : ");
                    }
                    BankAccount account = new BankAccount();
                    account.setAccountNumber(accNumber);
                    account.setAccountBalance(accBalance);
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
                    System.out.println(SEPARATOR_STRING);
                }
            } else if(selectedOption == 2){
                transfer(customer);
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

            System.out.println("Please sign in to continue.");
            System.out.println("Enter your name : ");
            String loginName = sc.nextLine();
            System.out.println("Enter the password : ");
            char[] loginPassword = sc.nextLine().toCharArray();
            for (Customer customer1 : customerList) {
                if (customer1.getName().equals(loginName) ||
                        Arrays.equals(customer1.getLoginPassword(), loginPassword)){
                    System.out.println("Welcome, " + loginName);
                    System.out.println("");
                }
            }


        } else if (selectedOption == 101){

            do{
                System.out.println("Please note that only authorised users " +
                        "can create a new customer account.");
                System.out.println("Enter credentials with administrator privileges to continue.");
                System.out.println("Enter the username : ");
                username = sc.nextLine();
                System.out.println("Enter the password : ");
                password = sc.nextLine().toCharArray();
            }while(!username.equals("admin") || !String.valueOf(password).equals("admin123"));

            System.out.println("Authentication Successful.");

            System.out.println("Create a new customer account. ");
            System.out.println("Enter the customer name : ");
            customer.setName(sc.nextLine());
            System.out.println("Enter the password : ");
            customer.setLoginPassword(sc.nextLine().toCharArray());
            System.out.println("Enter phone number : ");
            customer.setPhoneNumber(sc.next());
            System.out.println("Enter email address : ");
            customer.setEmailAddress(sc.next());

            customerList.add(customer);
            System.out.println("Customer Account created successfully.");
            System.out.println("Now sign in with the new customer account : ");
            login(customerList);//check this.
        } else{
            System.out.println("You don't have permission to create a new customer account");
        }
    }


    private static int displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the InterBanking " +
                "Pty Customer and Account Managemenr System");
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
            System.out.println(message);
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
            System.out.println(message);
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input, please enter a positive number.");
                scanner.next();
            }
            accountBalance = scanner.nextInt();
            if(!(accountBalance >= 0)){
                System.out.println("Invalid account balance!!! It has to be a positive number.");
            }
        }while(accountBalance < 0);
        return accountBalance;
    }

    public static int integerValidation(){
        Scanner sc = new Scanner(System.in);
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid number.");
            sc.next();
        }
        return sc.nextInt();
    }

    private static void transfer(Customer customer){
        Scanner scanner = new Scanner(System.in);
        BankAccount transferFrom = null;
        BankAccount transferTo = null;
        int numberTransferredFrom = 0;
        int numberTransferredTo = 0;
        double balanceTransferredFrom = 0;
        double balanceTransferredTo = 0;
        System.out.println("Enter the account number from which you want to transfer money from :");
        int fromAccountNumber = scanner.nextInt();
        System.out.println("Enter the account number you want to transfer money to : ");
        int toAccountNumber = scanner.nextInt();
        System.out.println("Enter the amount you wish to transfer : $");
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
//                System.out.println("Account " + numberTransferredFrom + " before withdrawal : "
//                        + transferFrom.getAccountBalance());
//                System.out.println("Account " + numberTransferredTo + " before deposit : "
//                        + transferTo.getAccountBalance());
//                System.out.println(SEPARATOR_STRING);
                BankAccount.transferMoney(transferFrom, transferTo, amountToTransfer);
                System.out.println("Account " + numberTransferredFrom + " after withdrawal : "
                        + transferFrom.getAccountBalance());
                System.out.println("Account " + numberTransferredTo + " after deposit : "
                        + transferTo.getAccountBalance());
            }
        } else{
            System.out.println("This transaction is not allowed because the account from " +
                    "which you want to withdraw money has inadequate funds.");
        }


    }
}
