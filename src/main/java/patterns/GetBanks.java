package patterns;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import externals.CreditBureau;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import static patterns.GetCreditScore.getCreditScore;

public class GetBanks {
    
    private static final String EXCHANGE_NAME_CUSTOMER = "customer_direct_exchange";
    
    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException {
        getRelevantBanks();
    }
    
    public static void getRelevantBanks() throws IOException, TimeoutException, InterruptedException {
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
            String message = new String( delivery.getBody() );

            System.out.println( " [x] Received from the credit score '" + message + "'" );

            sendRequestToRuleBase( message );
        }
    }
    
    public static void sendRequestToRuleBase(String creditScore) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = creditScore;

        channel.basicPublish( EXCHANGE_NAME_CUSTOMER, "rule_base", null, message.getBytes() );
        System.out.println( " [x] Sent request to rule base '" + message + "'" );

        getRelevantBanks();

        channel.close();
        connection.close();
    }
    
}
