package com.mycompany.loanbroker.reciplist;

import java.io.Serializable;

public class Data implements Serializable {

    private String ssn;
    private int creditScore;
    private double loanAmoount;
    private int loanDuration;

    public Data(String ssn, int creditScore, double loanAmoount, int loanDuration) {
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmoount = loanAmoount;
        this.loanDuration = loanDuration;
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

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public void setLoanAmoount(double loanAmoount) {
        this.loanAmoount = loanAmoount;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    @Override
    public String toString() {
        return "Data{" + "ssn=" + ssn + ", creditScore=" + creditScore + ", loanAmoount=" + loanAmoount + ", loanDuration=" + loanDuration + '}';
    }

}
