package com.sanjula.InterBankingPty.java.Stage1;

import java.util.Scanner;

public class BankAccount_1 {
    public static final String SEPARATOR_STRING = "***********************" +
            "***********************************************";
    public int accountNumber;
    public double accountBalance;
    public String customerName;
    public char[] accountPassword;

    public BankAccount_1(int accountNumber, double accountBalance,
                       String customerName, char[] accountPassword) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.customerName = customerName;
        this.accountPassword = accountPassword;
    }

    public static void main(String[] args){
        login();
        int selectedOption;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to InterBanking Pty " +
                "Customer and Account Management System");

        selectedOption = displayMenu();

        if(selectedOption == 1){
            String continue2;
            do{
                System.out.print("Enter the account number : ");
                int accNumber = integerValidation();
                System.out.print("Enter the opening " +
                        "account balance : ");
                double accBalance = doubleValidation();
                System.out.print("Enter your name : ");
                String name = scanner.nextLine();
                System.out.print("Enter your password : ");
                char[] password = scanner.next().toCharArray();
                System.out.println(SEPARATOR_STRING);
                BankAccount_1 newAccount = new BankAccount_1(accNumber,
                        accBalance, name, password);

                System.out.println("You entered the following details. ");
                System.out.println("Account Number : " + accNumber);
                System.out.println("Account Balance : " + accBalance);
                System.out.println("Customer Name : " + name);
                System.out.println("Password : " + new String(password));
                System.out.println(SEPARATOR_STRING);
                System.out.print("Do you want to continue creating a new " +
                        "Bank Account? If yes, enter 'Yes', else 'No' : ");
                scanner.nextLine();
                continue2 = scanner.nextLine();
            }while(!continue2.equalsIgnoreCase("No"));
        }
    }

    public static void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the InterBanking " +
                "Pty Customer and Account Management System");
        System.out.println("Please sign in to continue.");
        System.out.print("Account Number : ");
        int loginNumber = integerValidation();
        System.out.print("Name : ");
        String loginName = scanner.nextLine();
        System.out.println("Signed in successfully.");
        System.out.println(SEPARATOR_STRING);
    }

    public static int displayMenu(){
        System.out.print("Enter '1' to create " +
                "a new Bank Account : " );
        int selectedOption = getSelectedOption();
        while (selectedOption < 1 || selectedOption > 1){
            System.out.println("Please try again. Enter a " +
                    "number which represents the option you want to select.");
            selectedOption = getSelectedOption();
        }
        return selectedOption;
    }

    public static int getSelectedOption() {
        Scanner sc = new Scanner(System.in);
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid number.");
            sc.next();
        }
        return sc.nextInt();
    }

    public static double doubleValidation(){
        Scanner sc =  new Scanner(System.in);
        while(!sc.hasNextDouble()){
            System.out.println("Please enter a valid amount.");
            sc.next();
        }
        return sc.nextDouble();
    }
    public static int integerValidation(){
        Scanner sc = new Scanner(System.in);
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid number.");
            sc.next();
        }
        return sc.nextInt();
    }
}
