package com.mycompany.loanbroker.utilities;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

@XmlRootElement( name = "RuleBaseRequest" )
@XmlAccessorType( XmlAccessType.FIELD )
public class RuleBaseRequest {

    @XmlElement( name = "relevantBanks" )
    private ArrayList<String> relevantBanks = new ArrayList<>();

    public ArrayList<String> getRelevantBanks() {
        return relevantBanks;
    }

    /* to uncomment after push the RuleBaseWS to GitHub
     @XmlElement(name="relevantBanks")
     private ArrayList<Bank> relevantBanks= new ArrayList<>();

     public ArrayList<Bank> getRelevantBanks() {
     return relevantBanks;
     } */
}
