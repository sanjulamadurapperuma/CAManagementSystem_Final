package com.sanjula.InterBankingPty.java.Stage7;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    protected static final String SEPARATOR_STRING = "***********************" +
            "***********************************************";
    private static File customerDetails = new File("CustomerDetails_InterBankingPty.txt");//Customer objects are stored here
    protected static List<Customer> customerList = new ArrayList<>();//Stores all customer objects
    protected static List<BankAccount> bankAccountList = new ArrayList<>();//Stores all the bank accounts of a customer

    public static void main(String[] args){
        customerList = dataLoad();//Loads all the customer objects to the customerList array list
        int selectedOption;//Instance variable used to store the selected option from the menu

        Customer customer = login(customerList);//Customer is shown the login menu
        if (customer != null) {
            bankAccountList = customer.getBankAccountsList();
        }

        do{//If selected option = 0, exit the program

            selectedOption = displayMenu();//Displays the customer's main menu

            if(selectedOption == 1){//Creates a new bank account
                int selectedOptionAccount = displayAccountCreation();
                Scanner sc = new Scanner(System.in);//Scanner declared

                BankBranch branch = new BankBranch(0,"",0);
                if(selectedOptionAccount == 1){
                    String continue2;//String to check whether the user wants to continue creating new bank accounts

                    do{
                        SavingsAccount savingsAccount = new SavingsAccount(0,branch);
                        savingsAccount.enterAccountData(customer);

                        System.out.println();
                        System.out.print("Do you want to continue creating a new " +
                                "Bank Account? If yes, enter 'Yes', else 'No' : ");
                        continue2 = sc.nextLine();
                    } while(!continue2.equalsIgnoreCase("No"));

                    //Try-Catch block to check whether customer is null
                    try{
                        if (customer != null){
                            for (BankAccount account : bankAccountList){
                                if (account != null){
                                    if(account instanceof SavingsAccount){
                                        account.displayAccount(account.getAccountNumber(),
                                                account.getAccountBalance(),
                                                ((SavingsAccount) account).getInterestRate()
                                                , account.homeBranch);
                                    }//end of inner if
                                }//end of outer if
                            }//end of for loop
                        }//end of main if
                    } catch (NullPointerException e){
                        System.out.println("Internal Error occured : Your account was not associated with the" +
                                "savings account you just created");
                    }


                } else if(selectedOptionAccount == 2){
                    String continue2;//String to check whether the user wants to continue creating new bank accounts

                    do{
                        CheckingAccount checkingAccount = new CheckingAccount(0, branch);
                        checkingAccount.enterAccountData(customer);

                        System.out.println();
                        System.out.print("Do you want to continue creating a new " +
                                "Bank Account? If yes, enter 'Yes', else 'No' : ");
                        continue2 = sc.nextLine();
                    } while(!continue2.equalsIgnoreCase("No"));

                    //Try-Catch block to check whether customer is null
                    try{
                        if (customer != null){
                            for (BankAccount account : bankAccountList){
                                if (account != null){
                                    if(account instanceof CheckingAccount){
                                        account.displayAccount(account.getAccountNumber(), account.getAccountBalance()
                                                , ((CheckingAccount) account).monthlyFee,
                                                ((CheckingAccount) account).noOfChecksAllowed, account.homeBranch);
                                    }//end of inner if
                                }//end of outer if
                            }//end of for loop
                        }//end of main if
                    } catch (NullPointerException e){
                        System.out.println("Internal Error occured : Your account was not associated with the" +
                                "checking account you just created");
                    }


                } else if(selectedOptionAccount == 3){
                    CheckingAccountWithInterest[] accountWithInterests = new CheckingAccountWithInterest[5];
                    SavingsAccount savingsAccount = new SavingsAccount(0, branch);
                    String continue2;//String to check whether the user wants to continue creating new bank accounts

                    for (int i = 0; i < accountWithInterests.length; i++){
                        CheckingAccountWithInterest checkingAccount =
                                new CheckingAccountWithInterest(0, branch, savingsAccount);
                        accountWithInterests[i] = checkingAccount.enterAccountData(customer);

                        System.out.println();
                        System.out.print("Do you want to continue creating a new " +
                                "Bank Account? If yes, enter 'Yes', else 'No' : ");
                        continue2 = sc.nextLine();
                        if (continue2.equalsIgnoreCase("No")){
                            break;
                        }
                    }

                    //Try-Catch block to check whether customer is null
                    try{
                        if (customer != null){
                            for (BankAccount account : bankAccountList){
                                if (account != null){
                                    if(account instanceof CheckingAccountWithInterest){
                                        account.displayAccount(account.getAccountNumber(),
                                                account.getAccountBalance(),
                                                ((CheckingAccountWithInterest) account).getSavingsAccount().getInterestRate(),
                                                ((CheckingAccountWithInterest) account).monthlyFee,
                                                ((CheckingAccountWithInterest) account).noOfChecksAllowed,
                                                account.homeBranch);
                                    }//end of inner if
                                }//end of outer if
                            }//end of for loop
                        }//end of main if

                    } catch (NullPointerException e){
                        System.out.println("Internal Error occured : Your account was not associated with the" +
                                "checking account you just created");
                    }

                }//end of else if statement

            } else if(selectedOption == 2){//Transfer money between accounts

                if (customer != null) {
                    Scanner scanner = new Scanner(System.in);
                    int fromAccountNumber = getAccountNumber(scanner, "Enter the " +
                            "account number from which you want to transfer money from :");
                    System.out.print("");
                    int toAccountNumber = getAccountNumber(scanner, "Enter the " +
                            "account number you want to transfer money to : ");
                    System.out.print("");
                    double amountToTransfer = getAccountBalance(scanner,
                            "Enter the amount you wish to transfer : $");
                    Transaction transaction = new Transaction();//Create new Transaction object
                    transaction.transfer(customer, fromAccountNumber,
                            toAccountNumber, amountToTransfer);//Calling the transfer method passing in the
                    // customer object

                } else{
                    System.err.println("Error checking for customer details.");
                }
            } else if(selectedOption == 3){//Generate Bank Account Report
                produceReport("Bank Account Report ", bankAccountList);

            } else if (selectedOption == 0){//User enters 0 to exit the program
                //The customer objects are saved into the text file specified by calling the dataPersistency method
                dataPersistency(customerList);
                System.exit(0);//Exit the program
            }
        }while (selectedOption !=0);
    }

    private static Customer login(List<Customer> customerList){
        //The username and password variables hold the administrator's credentials
        String username;
        char[] password;

        Scanner sc = new Scanner(System.in);
        //Call the menu asking whether the user wants to sign in or admin wants to register an account
        int selectedOption = displayLoginMenu();
        if(selectedOption == 100){//User wants to sign in to the system
            String loginName;
            char[] loginPassword;
            boolean isValidCustomer;//Helps when checking whether the customer exists in the customerList

            do {
                System.out.println();
                System.out.println("Please sign in to continue.");

                System.out.print("Enter your name : ");
                loginName = sc.nextLine();

                System.out.print("Enter the password : ");
                loginPassword = sc.nextLine().toCharArray();

                //For each customer object in the customerList, checks whether
                // the username and password entered by the user matches.
                for (Customer loginCustomer : customerList) {
                    if (loginCustomer != null){
                        if (loginCustomer.getName().equals(loginName) &&
                                Arrays.equals(loginCustomer.getLoginPassword(), loginPassword)) {
                            System.out.println(SEPARATOR_STRING);
                            System.out.println("Welcome, " + loginName);
                            System.out.println();
                            isValidCustomer = true;
                            return loginCustomer;//returns the specific customer object
                        } else{
                            System.err.println("Invalid Username or password. Please try again");
                            isValidCustomer = false;
                        }
                    }
                }
                isValidCustomer = false;
            } while (!isValidCustomer);
        } else if (selectedOption == 101){

            do{
                //administrator should sign in first and then create the customer account
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
            //New Customer object declared to hold the new customer account
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
            System.err.println("You don't have permission to create a new customer account");
        }
        return null;
    }

    private static int displayAccountCreation(){
        System.out.println("What type of bank account do you want to create?");
        System.out.println("Enter '1' to create a savings account");
        System.out.println("Enter '2' to create a checking account");
        System.out.println("Enter '3' to create checking accounts with interest");
        int selectedOption = getSelectedOption();

        while(selectedOption < 1 || selectedOption > 3){
            System.err.println("Please try again. Enter a" +
                    "number which represents the option you want to select");
            selectedOption = getSelectedOption();
        }
        return selectedOption;
    }


    private static int displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the InterBanking " +
                "Pty Customer and Account Management System");
        int selectedOption = getSelectedOption(scanner, "Enter '100' to sign in \n" +
                "Enter '101' to register a new account");

        return selectedOption;
    }

    private static int displayMenu(){//Customer's main menu
        System.out.println("============ This is the InterBanking " +
                "Pty Customer and Account Management System ============");
        System.out.println("What do you want to do?");
        System.out.println("Enter '1' to create a new Bank Account.");
        System.out.println("Enter '2' to transfer money between bank accounts");
        System.out.println("Enter '3' to produce a report for all of your accounts");
        System.out.println("Enter '0' to exit the program.");
        int selectedOption = getSelectedOption();

        while (selectedOption < 0 || selectedOption > 4){
            System.err.println("Please try again. Enter a " +
                    "number which represents the option you want to select.");
            selectedOption = getSelectedOption();
        }

        return selectedOption;
    }

    private static int getSelectedOption(Scanner scanner, String message){//Validates login menu options
        int selectedOption;

        do{
            System.out.println(message);
            while(!scanner.hasNextInt()){
                System.err.println("Invalid input, please enter one of the numbers specified : 100 or 101");
                scanner.next();
            }

            selectedOption = scanner.nextInt();
            if(selectedOption != 100 && selectedOption != 101){
                System.err.println("Invalid option number!!! It has to be either 100 or 101.");
            }

        }while(selectedOption != 100 && selectedOption != 101);

        return selectedOption;
    }

    protected static int getSelectedOption(){//validates customer's menu and
        // other integer values
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()){
            System.out.println("Please enter a valid number.");
            sc.next();
        }
        return sc.nextInt();
    }

    public static int getAccountNumber(Scanner scanner, String message){
        int accountNumber;
        do{
            System.out.print(message);
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input, please enter a number within the range of 1000 to 9999");
                System.out.println();
                scanner.next();
            }
            accountNumber = scanner.nextInt();
            if(!(accountNumber >= 1000 && accountNumber <= 9999)){
                System.out.println("Invalid Account Number!!! It has to be within the range of 1000 to 9999");
            }
        }while(accountNumber < 1000 || accountNumber > 9999);
        return accountNumber;
    }

    public static double getAccountBalance(Scanner scanner, String message){
        double accountBalance;
        do{
            System.out.print(message);
            while(!scanner.hasNextDouble()){
                System.err.println("Invalid input, please enter a positive number.");
                scanner.next();
            }
            accountBalance = scanner.nextDouble();
            if(!(accountBalance >= 0 || accountBalance <= 100000)){
                System.err.println("Invalid account balance!!! It has to be a positive " +
                        "number and be within the range of $0 to $100000");
            }

        }while(accountBalance < 0 || accountBalance > 100000);
        return accountBalance;
    }

    protected static void dataPersistency(List<Customer> customerList){

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

    private static void produceReport(String reportTitle, List<BankAccount> bankAccountList){
        if(bankAccountList != null){
            System.out.printf("%75s", reportTitle);
            System.out.println();
            System.out.println();
            System.out.println("SAVINGS ACCOUNTS");
            System.out.println("-----------------------------------------------------------" +
                    "-------------------");
            System.out.printf("%5s %13s %12s %10s %12s %15s", "ACCOUNT NO", "BALANCE($)", "INTEREST(%)", "BSB NO",
                    "ADDRESS", "POSTCODE");
            System.out.println();
            System.out.println("-----------------------------------------------------------" +
                    "-------------------");


            for (BankAccount account : bankAccountList) {
                if(account instanceof SavingsAccount){
                    System.out.printf("%5s %14s %11s %15s %10s %12s", account.getAccountNumber(),
                            account.getAccountBalance(), (((SavingsAccount) account).getInterestRate() * 100),
                            account.getHomeBranch().getBsbNumber(), account.getHomeBranch().getAddress(),
                            account.getHomeBranch().getPostcode());
                    System.out.println();
                    System.out.println("-----------------------------------------------------------" +
                            "-------------------");
                }
            }

            System.out.println();
            System.out.println();

            System.out.println("CHECKING ACCOUNTS");
            System.out.println("-----------------------------------------------------------" +
                    "-------------------------------------------");
            System.out.printf("%5s %12s %15s %22s %10s %13s %13s", "ACCOUNT NO", "BALANCE($)", "MONTHLY FEE($)",
                    "NO OF CHECKS ALLOWED", "BSB NO", "ADDRESS", "POSTCODE");
            System.out.println();
            System.out.println("-----------------------------------------------------------" +
                    "-------------------------------------------");

            for (BankAccount bankAccount: bankAccountList) {
                if(bankAccount instanceof CheckingAccount){
                    System.out.printf("%5s %16s %14s %20s %14s %13s %13s", bankAccount.getAccountNumber(),
                            bankAccount.getAccountBalance(), ((CheckingAccount) bankAccount).getMonthlyFee(),
                            ((CheckingAccount) bankAccount).getNoOfChecksAllowed(),
                            bankAccount.getHomeBranch().getBsbNumber(), bankAccount.getHomeBranch().getAddress(),
                            bankAccount.getHomeBranch().getPostcode());
                    System.out.println();
                    System.out.println("-----------------------------------------------------------" +
                            "-------------------------------------------");
                }
            }

            System.out.println();
            System.out.println();

            System.out.println("CHECKING ACCOUNTS WITH INTEREST");
            System.out.println("-----------------------------------------------------------" +
                    "--------------------------------------------------------------------");
            System.out.printf("%5s %15s %14s %15s %25s %10s %15s %15s", "ACCOUNT NO", "BALANCE($)", "INTEREST(%)", "MONTHLY FEE",
                    "NO OF CHECKS ALLOWED", "BSB NO", "ADDRESS", "POSTCODE");
            System.out.println();
            System.out.println("-----------------------------------------------------------" +
                    "--------------------------------------------------------------------");

            for (BankAccount bankAccount: bankAccountList) {
                if(bankAccount instanceof CheckingAccountWithInterest){
                    System.out.printf("%5s %17s %14s %15s %25s %13s %13s %15s", bankAccount.getAccountNumber(),
                            bankAccount.getAccountBalance(),
                            (((CheckingAccountWithInterest) bankAccount).getSavingsAccount().getInterestRate() * 100),
                            ((CheckingAccountWithInterest) bankAccount).getMonthlyFee(),
                            ((CheckingAccountWithInterest) bankAccount).getNoOfChecksAllowed(),
                            bankAccount.getHomeBranch().getBsbNumber(), bankAccount.getHomeBranch().getAddress(),
                            bankAccount.getHomeBranch().getPostcode());
                    System.out.println();
                    System.out.println("-----------------------------------------------------------" +
                            "--------------------------------------------------------------------");
                }
            }
            System.out.println();
            System.out.println("==================================================================" +
                    "End of Report==================================================================");
            System.out.println();
        }

    }

}
