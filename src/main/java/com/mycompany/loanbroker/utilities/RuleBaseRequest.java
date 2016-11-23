package com.mycompany.loanbroker.utilities;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

@XmlRootElement( name = "RuleBaseRequest" )
@XmlAccessorType( XmlAccessType.FIELD )
public class RuleBaseRequest {

    @XmlElement( name = "relevantBanks" )
    private ArrayList<Bank> relevantBanks;

    public RuleBaseRequest() {
        relevantBanks = new ArrayList<>();
    }

    public ArrayList<Bank> getRelevantBanks() {
        return relevantBanks;
    }
}
