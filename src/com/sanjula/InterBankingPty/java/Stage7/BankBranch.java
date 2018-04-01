package com.sanjula.InterBankingPty.java.Stage7;

import java.io.Serializable;

public class BankBranch implements Serializable {
    private int bsbNumber;
    private String address;
    private int postcode;

    public BankBranch(){
    }

    public BankBranch(int bsbNumber, String address, int postcode) {
        this.bsbNumber = bsbNumber;
        this.address = address;
        this.postcode = postcode;
    }

    protected int getBsbNumber() {
        return bsbNumber;
    }

    protected void setBsbNumber(int bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected int getPostcode() {
        return postcode;
    }

    protected void setPostcode(int postcode) {
        this.postcode = postcode;
    }

}
