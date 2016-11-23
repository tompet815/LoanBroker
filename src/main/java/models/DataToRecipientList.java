package models;

import java.util.List;

public class DataToRecipientList {

    private List<Bank> bankExchangeNames;
    private String ssn;
    private int creditScore;
    private double loanAmount;
    private int loanDuration;

    public DataToRecipientList() {
    }

    public DataToRecipientList( List<Bank> bankExchangeNames, String ssn, int creditScore, double loanAmount, int loanDuration ) {
        this.bankExchangeNames = bankExchangeNames;
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
    }

    public List<Bank> getBankExchangeNames() {
        return bankExchangeNames;
    }

    public void setBankExchangeNames( List<Bank> bankExchangeNames ) {
        this.bankExchangeNames = bankExchangeNames;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn( String ssn ) {
        this.ssn = ssn;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore( int creditScore ) {
        this.creditScore = creditScore;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount( double loanAmount ) {
        this.loanAmount = loanAmount;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration( int loanDuration ) {
        this.loanDuration = loanDuration;
    }

   
}
