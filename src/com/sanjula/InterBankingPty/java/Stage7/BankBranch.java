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

    public int getBsbNumber() {
        return bsbNumber;
    }

    public void setBsbNumber(int bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void getBranchDetails(){

    }
}
