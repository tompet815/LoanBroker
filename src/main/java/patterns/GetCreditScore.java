package patterns;

import models.Data;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceRef;
import org.bank.credit.web.service.CreditScoreService;
import org.bank.credit.web.service.CreditScoreService_Service;

public class GetCreditScore {

    private static final String EXCHANGE_NAME_CUSTOMER = "customer_direct_exchange";
    private static MessageUtility messageUtility = new MessageUtility();
//    private static final String EXCHANGE_NAME_CREDIT_BUREAU = "customer_exchange";
    @WebServiceRef(wsdlLocation
            = "http://139.59.154.97:8080/CreditScoreService/CreditScoreService?wsdl")
    private static CreditScoreService_Service service = new CreditScoreService_Service();

    public static void main(String[] argv) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, Exception {
        getCustomerRequest();
//        System.out.println( getCreditScoreWS( "280938-3429" ) );
    }

    //message transmition from the customer to Get Credit Score
    public static void getCustomerRequest() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME_CUSTOMER, "customer");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                byte[] message = delivery.getBody();
                String stringMessage=removeBom(new String(message));
                Data inputMessage= unmarchal(stringMessage);
                //Data inputMessage = (Data) messageUtility.deSerializeBody(message);
                String corrId = delivery.getProperties().getCorrelationId();
                System.out.println(" [x] Received from the customer '" + inputMessage.toString() + "'");
                
                //sendRequestCreditBureau( message );
                getCreditScoreWS(inputMessage.getSsn(), inputMessage, corrId);
            }
            catch (JAXBException ex) {
                Logger.getLogger(GetCreditScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void getCreditScoreWS(String ssn, Data message, String corrId) {
        CreditScoreService port = service.getCreditScoreServicePort();
        int creditScore = port.creditScore(ssn);
        message.setCreditScore(creditScore);
        try {
            sendScoreToGetBanks(messageUtility.serializeBody(message), corrId);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
     private static Data unmarchal(String bodyString) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Data.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringReader reader = new StringReader(bodyString);
        return (Data) unmarshaller.unmarshal(reader);
    }
//remove unnecessary charactors before xml declaration 
    private static String removeBom(String bodyString) {
        String res = bodyString.trim();
        int substringIndex = res.indexOf("<?xml");
        if (substringIndex < 0) {
            return res;
        }
        return res.substring(res.indexOf("<?xml"));
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
    public static void sendScoreToGetBanks(byte[] message, String corrId) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.correlationId(corrId);
        AMQP.BasicProperties prop = builder.build();
        channel.basicPublish(EXCHANGE_NAME_CUSTOMER, "banks", prop, message);
        System.out.println(" [x] Sent request to get banks '" + messageUtility.deSerializeBody(message) + "'");

        channel.close();
        connection.close();
    }
}
