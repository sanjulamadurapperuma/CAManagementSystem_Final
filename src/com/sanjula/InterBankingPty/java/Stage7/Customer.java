package com.sanjula.InterBankingPty.java.Stage7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable{//Start of Customer class
    private String name;
    private char[] loginPassword;
    private String phoneNumber;
    private String emailAddress;
    private List<BankAccount> bankAccountsList = new ArrayList<>();


    public Customer(String name, char[] loginPassword, String phoneNumber, String emailAddress, List<BankAccount> bankAccountsList) {
        this.name = name;
        this.loginPassword = loginPassword;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bankAccountsList = bankAccountsList;
    }

    public Customer(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(char[] loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<BankAccount> getBankAccountsList() {
        return bankAccountsList;
    }

    public void setBankAccountsList(List<BankAccount> bankAccountsList) {
        this.bankAccountsList = bankAccountsList;
    }

    @Override
    public String toString() {
        return "[Customer Name : " + name +
                ", Phone Number : " + phoneNumber
                + ", Email Address : " + emailAddress +
                ", Bank Accounts : " +
                getBankAccountsList() + "]\n\n";
    }
}//End of Customer class
