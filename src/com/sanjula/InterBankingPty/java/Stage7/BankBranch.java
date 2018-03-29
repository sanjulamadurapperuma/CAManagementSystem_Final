package com.sanjula.InterBankingPty.java.Stage7;

import java.util.Scanner;

public class BankBranch {
    private int bsbNumber;
    private String address;
    private int postcode;

    public BankBranch(int bsbNumber, String address, int postcode) {
        this.bsbNumber = bsbNumber;
        this.address = address;
        this.postcode = postcode;
    }

    public void getBranchDetails(){
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
        bsbNumber = sc.nextInt();
        System.out.println("Enter the address : ");
        address = sc.nextLine();
        System.out.println("Enter the postcode : ");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid postcode number");
            sc.next();
        }
        postcode = sc.nextInt();
    }
}
