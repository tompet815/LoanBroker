package externals;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreditBureau {

    private static final String EXCHANGE_NAME = "customer_direct_exchange";

    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException {
        getCreditInfo();
    }

    public static void getCreditInfo() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME, "credit_bureau" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String( delivery.getBody() );

            System.out.println( " [x] Received '" + message + "'" );
            returnCreditScore();

        }
    }

    public static void returnCreditScore() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = "" + calculateCreditScore();

        channel.basicPublish( EXCHANGE_NAME, "credit_score", null, message.getBytes() );
        System.out.println( " [x] Sent '" + message + "'" );

        channel.close();
        connection.close();
    }

    public static int calculateCreditScore() {
        int response = 800;
        return response;
    }
}
