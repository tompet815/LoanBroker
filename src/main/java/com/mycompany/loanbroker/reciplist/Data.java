package com.mycompany.loanbroker.reciplist;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable {

    private String ssn;
    private int creditScore;
    private double loanAmoount;
    private Date loanDuration;

    public Data(String ssn, int creditScore, double loanAmoount, Date loanDuration) {
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

    public Date getLoanDuration() {
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

    public void setLoanDuration(Date loanDuration) {
        this.loanDuration = loanDuration;
    }

    @Override
    public String toString() {
        return "Data{" + "ssn=" + ssn + ", creditScore=" + creditScore + ", loanAmoount=" + loanAmoount + ", loanDuration=" + loanDuration + '}';
    }

}
