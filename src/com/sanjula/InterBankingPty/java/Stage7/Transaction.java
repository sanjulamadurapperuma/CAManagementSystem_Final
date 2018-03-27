package com.sanjula.InterBankingPty.java.Stage7;

public class Transaction {
    private static int numberTransferredFrom = 0;
    private static int numberTransferredTo = 0;
    private static double balanceTransferredFrom = 0;
    private static double balanceTransferredTo = 0;


    protected void transfer(Customer customer, int fromAccountNumber,
                            int toAccountNumber, double amountToTransfer){

        BankAccount transferFrom = null;
        BankAccount transferTo = null;

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
                System.err.println("You are not allowed to transfer money between the same account");
                System.out.println();
            } else{
                System.out.println();
                if (transferFrom != null && transferTo != null){
                    BankAccount.transferMoney(transferFrom, transferTo, amountToTransfer);
                    System.out.println("Account " + numberTransferredFrom + " after withdrawal : $"
                            + transferFrom.getAccountBalance());
                    System.out.println("Account " + numberTransferredTo + " after deposit : $"
                            + transferTo.getAccountBalance());
                    System.out.println();
                    System.out.println(Main.SEPARATOR_STRING);
                    System.out.println();

                    //Ask if user wants to roll back the transaction:
                    int rollBackOption;
                    do{
                        System.out.println("Do you want to roll back the transaction you " +
                                "just made? Enter '1' for yes and '2' for no.");
                        rollBackOption = Main.getSelectedOption();
                    }while(!(rollBackOption == 1 || rollBackOption == 2));

                    if (rollBackOption == 1){
                        rollBack(transferFrom,
                                transferTo, amountToTransfer);//Call the rollBack method only
                        // if user says yes
                    } else {
                        System.out.println("The rollback of the transaction was cancelled.");
                        System.out.println();
                    }
                } else{
                    System.err.println("Please try again. One or both of " +
                            "the account numbers you entered are not valid.");
                    System.out.println();
                }
            }
        } else{
            System.out.println("This transaction is not allowed because the account from " +
                    "which you want to withdraw money has inadequate funds.");
            System.out.println();
        }
    }

    private void rollBack(BankAccount transferFrom, BankAccount transferTo, double amountToTransfer){
        BankAccount.transferMoney(transferTo, transferFrom, amountToTransfer);
        System.out.println("=====Rollback successful=====");
        System.out.println("Updated details below : ");
        System.out.println();
        System.out.println("Account " + transferTo.getAccountNumber() + " after withdrawal : $"
                + transferTo.getAccountBalance());
        System.out.println("Account " + transferFrom.getAccountNumber() + " after deposit : $"
                + transferFrom.getAccountBalance());
        System.out.println();
    }
}
