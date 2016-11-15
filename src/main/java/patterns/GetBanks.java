package patterns;

import com.mycompany.loanbroker.reciplist.Data;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.mycompany.loanbroker.utilities.RuleBaseRequest;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceRef;
import rulebasews.IOException_Exception;
import rulebasews.JAXBException_Exception;
import rulebasews.RuleBase;
import rulebasews.RuleBaseWS;
//import static patterns.GetCreditScore.getCreditScore;

public class GetBanks {

    private static final String EXCHANGE_NAME_CUSTOMER = "customer_direct_exchange";
    private static final String EXCHANGENAME = "whatRecipientList"; //exchange used for the recipList
    private static MessageUtility messageUtility = new MessageUtility();
    @WebServiceRef( wsdlLocation
            = "http://localhost:8080/RuleeBasedWS/RuleBase?WSDL" )
    private static RuleBase service = new RuleBase();

    public static void main( String[] argv ) throws Exception {
        getCreditScore();

    }

    public static void getCreditScore() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME_CUSTOMER, "banks" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] message = delivery.getBody();
            Data objectMessage = ( Data ) messageUtility.deSerializeBody( message );
            int creditScore = objectMessage.getCreditScore();

            System.out.println( " [x] Received from the credit score '" + objectMessage.toString() + "'" );
            objectMessage.setBanks( getRelevantBanks( creditScore ) );
            sendRequestRecipList( objectMessage);
        }
    }

    public static ArrayList<String> getRelevantBanks( int creditScore ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, IOException_Exception, JAXBException_Exception, JAXBException {
        RuleBaseWS rbws = service.getRuleBaseWSPort();
        String xmlString = rbws.getRelevantBanks( creditScore );
        System.out.println( "This is the xml string: " + xmlString );

        java.io.FileWriter fw = new java.io.FileWriter( "relevantBanks.xml" );
        fw.write( xmlString );
        fw.close();

        JAXBContext jc = JAXBContext.newInstance( RuleBaseRequest.class );
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File( "relevantBanks.xml" );
        RuleBaseRequest root = ( RuleBaseRequest ) unmarshaller.unmarshal( xml );

        System.out.println( "The first bank is " + root.getRelevantBanks().get( 0 ) );
        System.out.println( "The second bank is " + root.getRelevantBanks().get( 1 ) );
        return root.getRelevantBanks();
    }

    public static void sendRequestRecipList( Data objectToSend ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        byte[] dataToSend= messageUtility.serializeBody( objectToSend);
        channel.basicPublish( EXCHANGENAME, "", null, dataToSend );
        System.out.println( " [x] Sent request to recipient list: " + objectToSend + "'" );

        channel.close();
        connection.close();
    }

}
