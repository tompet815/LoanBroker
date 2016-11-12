package ruleBaseWS;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class RuleBaseResponse {
    private String relevantBanks;

    public RuleBaseResponse( String relevantBanks ) {
        this.relevantBanks = relevantBanks;
    }

    public String getRelevantBanks() {
        return relevantBanks;
    }

    public void setRelevantBanks( String relevantBanks ) {
        this.relevantBanks = relevantBanks;
    }
    
}
