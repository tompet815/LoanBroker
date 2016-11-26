package models;

import models.Bank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class Data implements Serializable {

    private String ssn;
    private int creditScore;
    private double loanAmount;
    private int loanDuration;

    @XmlElementWrapper(name = "bankExchangeNames")
    @XmlElement(name = "bankExchangeName")
    private ArrayList<Bank> banks;

    public Data() {
    }

    public Data(String ssn, int creditScore, double loanAmount, int loanDuration, ArrayList<Bank> banks) {
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
        this.banks = banks;
    }

    public Data(String ssn, int creditScore, double loanAmount, int loanDuration) {
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
        this.banks = null;
    }

    public String getSsn() {
        return ssn;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    public void setBanks(ArrayList<Bank> banks) {
        this.banks = banks;
    }

    @Override
    public String toString() {
        return "Data{" + "ssn=" + ssn + ", creditScore=" + creditScore + ", loanAmount="
                + loanAmount + ", loanDuration=" + loanDuration + ", banks " + banks + '}';
    }

}
