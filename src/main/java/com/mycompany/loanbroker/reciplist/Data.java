package com.mycompany.loanbroker.reciplist;

import models.Bank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Data implements Serializable {

    private String ssn;
    private int creditScore;
    private double loanAmoount;
    private int loanDuration;
    private ArrayList<Bank> banks;

    public Data( String ssn, int creditScore, double loanAmoount, int loanDuration ) {
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmoount = loanAmoount;
        this.loanDuration = loanDuration;
        this.banks = null;
    }

    public String getSsn() {
        return ssn;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public double getLoanAmoount() {
        return loanAmoount;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public void setSsn( String ssn ) {
        this.ssn = ssn;
    }

    public void setCreditScore( int creditScore ) {
        this.creditScore = creditScore;
    }

    public void setLoanAmoount( double loanAmoount ) {
        this.loanAmoount = loanAmoount;
    }

    public void setLoanDuration( int loanDuration ) {
        this.loanDuration = loanDuration;
    }

    public void setBanks( ArrayList<Bank> banks ) {
        this.banks = banks;
    }

    @Override
    public String toString() {
        return "Data{" + "ssn=" + ssn + ", creditScore=" + creditScore + ", loanAmoount=" 
                + loanAmoount + ", loanDuration=" + loanDuration + ", banks " + banks + '}';
    }

}
