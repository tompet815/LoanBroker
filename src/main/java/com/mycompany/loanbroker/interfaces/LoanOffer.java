
package com.mycompany.loanbroker.interfaces;


public class LoanOffer {
    
    private float interestRate;
    private String bankName;

    public LoanOffer(float interestRate, String bankName) {
        this.interestRate = interestRate;
        this.bankName = bankName;
    }

    public float getInterestRate() {
        return interestRate;
    }
   
    public String getBankName() {
        return bankName;
    }

      
}
