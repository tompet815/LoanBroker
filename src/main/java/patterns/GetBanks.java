package patterns;

import com.mycompany.loanbroker.utilities.MessageUtility;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.xml.ws.WebServiceRef;
import rulebasews.IOException_Exception;
import rulebasews.JAXBException_Exception;
import rulebasews.RuleBase;
import rulebasews.RuleBaseWS;
//import static patterns.GetCreditScore.getCreditScore;

public class GetBanks {

    private static final String EXCHANGE_NAME_CUSTOMER = "customer_direct_exchange";
    private static MessageUtility messageUtility = new MessageUtility();
     @WebServiceRef(wsdlLocation = 
      "http://localhost:8080/RuleBaseWS/RuleBase?WSDL")
    private static RuleBase service;
    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        getCreditScore();
        
    }

    public static void getCreditScore() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
    
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

            System.out.println( " [x] Received from the credit score '" + messageUtility.deSerializeBody(message).toString() + "'" );
        //message from Tomoe. Please get the credit score from body.
        // int creditScore= ???
        //sendRequestToRuleBase( creditScore );
        }
    }

    public static void sendRequestToRuleBase( int creditScore ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, IOException_Exception, JAXBException_Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost( "datdb.cphbusiness.dk" );
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        channel.basicPublish( EXCHANGE_NAME_CUSTOMER, "rule_base", null, creditScore );
//        System.out.println( " [x] Sent request to rule base '" + messageUtility.deSerializeBody( creditScore ).toString() + "'" );

        getRelevantBanks(creditScore);
//
//        channel.close();
//        connection.close();
    }

    public static void getRelevantBanks(int creditScore) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, IOException_Exception, JAXBException_Exception {
          RuleBaseWS rbws =service.getRuleBaseWSPort();
         String xmlString= rbws.getRelevantBanks(creditScore);
         //Message from Tomoe
         //Translate from xml to Objects and put into the Arraylist
         
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost( "datdb.cphbusiness.dk" );
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind( queueName, EXCHANGE_NAME_CUSTOMER, "relevant_banks" );
//
//        QueueingConsumer consumer = new QueueingConsumer( channel );
//        channel.basicConsume( queueName, true, consumer );
//        while ( true ) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            byte[] message = delivery.getBody();
//
//            System.out.println( " [x] Received from the rule base'" + messageUtility.deSerializeBody( message ).toString() + "'" );
//        }
    }

}
