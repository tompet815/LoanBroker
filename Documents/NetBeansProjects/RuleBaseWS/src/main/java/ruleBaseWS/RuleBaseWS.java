package ruleBaseWS;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

@WebService( serviceName = "RuleBase" )
public class RuleBaseWS {

    private static ArrayList<String> allBanks;
    private static ArrayList<String> relevantBanks;

    @WebMethod( operationName = "getRelevantBanks" )
    public String getInterestRate( @WebParam( name = "creditScore" ) int creditScore ) throws JAXBException, IOException {

        allBanks = new ArrayList<>();
        allBanks.add( "Danske Bank" );
        allBanks.add( "Nordea" );
        allBanks.add( "Jyske Bank" );
        relevantBanks = new ArrayList<String>();
        if ( creditScore >= 500 ) {
            relevantBanks.add( allBanks.get( 0 ) );
            relevantBanks.add( allBanks.get( 1 ) );
        } else if ( creditScore >= 300 ) {
            relevantBanks.add( allBanks.get( 1 ) );
            relevantBanks.add( allBanks.get( 2 ) );
        } else {
            relevantBanks.add( allBanks.get( 0 ) );
            relevantBanks.add( allBanks.get( 2 ) );
        }

        RuleBaseResponse response= new RuleBaseResponse(relevantBanks );
        JAXBContext jc = JAXBContext.newInstance( RuleBaseResponse.class );
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        JAXBElement<RuleBaseResponse> je2 = new JAXBElement( new QName( "RuleBaseRequest" ), RuleBaseResponse.class, response );
        StringWriter sw = new StringWriter();
        marshaller.marshal( je2, sw );
        String xmlString = sw.toString();

        System.out.println( "xml" + xmlString );
        return xmlString;
    }

}
