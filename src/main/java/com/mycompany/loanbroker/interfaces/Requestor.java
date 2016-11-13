
package com.mycompany.loanbroker.interfaces;

public interface Requestor {
      void request(String ssn, double loanAmount, int loanDuration);//async
      LoanOffer receive();
}
