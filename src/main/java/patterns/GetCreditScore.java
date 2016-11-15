package patterns;

import com.mycompany.loanbroker.reciplist.Data;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceRef;
import org.bank.credit.web.service.CreditScoreService;

public class GetCreditScore {

    private static final String EXCHANGE_NAME_CUSTOMER = "customer_direct_exchange";
    private static MessageUtility messageUtility = new MessageUtility();
//    private static final String EXCHANGE_NAME_CREDIT_BUREAU = "customer_exchange";
  
    @WebServiceRef( wsdlLocation
            = "http://139.59.154.97:8080/CreditScoreService/CreditScoreService?wsdl" )
    private static CreditScoreService service;

    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, Exception {
        getCustomerRequest();
//        System.out.println( getCreditScoreWS( "280938-3429" ) );
    }

    //message transmition from the customer to Get Credit Score
    public static void getCustomerRequest() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME_CUSTOMER, "customer" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] message = delivery.getBody();
            Data inputMessage = ( Data ) messageUtility.deSerializeBody( message );

            System.out.println( " [x] Received from the customer '" + inputMessage.toString() + "'" );

            //sendRequestCreditBureau( message );
            getCreditScoreWS( inputMessage.getSsn(), inputMessage );
        }
    }

    private static void getCreditScoreWS( String ssn, Data message ) {
        int creditScore=service.creditScore(ssn);
        
        message.setCreditScore( creditScore );
        try {
            sendScoreToGetBanks( messageUtility.serializeBody(message) );
        } catch ( Exception ex ) {
            System.out.println( ex );
        }
    }

    //message transmition from Get Credit Score to Credit Bureau
//    public static void sendRequestCreditBureau( byte[] clientInput ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost( "datdb.cphbusiness.dk" );
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        channel.basicPublish( EXCHANGE_NAME_CUSTOMER, "credit_bureau", null, clientInput );
//        System.out.println( " [x] Sent request for credit score '" + messageUtility.deSerializeBody( clientInput ).toString() + "'" );
//
//        getCreditScore();
//
//        channel.close();
//        connection.close();
//    }
//
//    //message transmition from Credit Bureau to Get Credit Score
//    public static void getCreditScore() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost( "datdb.cphbusiness.dk" );
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind( queueName, EXCHANGE_NAME_CUSTOMER, "credit_score" );
//
//        QueueingConsumer consumer = new QueueingConsumer( channel );
//        channel.basicConsume( queueName, true, consumer );
//        while ( true ) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            byte[] message = delivery.getBody();
//
//            System.out.println( " [x] Received credit score = '" + messageUtility.deSerializeBody( message ).toString() + "'" );
//
//            sendScoreToGetBanks( message );
//        }
//    }
    //message transmition from Get Credit Score to Get Banks
    public static void sendScoreToGetBanks( byte[] message ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.basicPublish( EXCHANGE_NAME_CUSTOMER, "banks", null, message );
        System.out.println( " [x] Sent request to get banks '" + messageUtility.deSerializeBody( message ) + "'" );

        channel.close();
        connection.close();
    }
}
