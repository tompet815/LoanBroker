package externals;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreditBureau {

    private static final String EXCHANGE_NAME = "customer_exchange";

    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException {
        getCreditInfo();
        returnCreditScore();
    }

    public static void getCreditInfo() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME, "" );
        System.out.println( "Queue name: " + queueName );
        System.out.println( " [*] Waiting for messages. To exit press CTRL+C" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String( delivery.getBody() );

            System.out.println( " [x] Received '" + message + "'" );

        }
    }

    public static void returnCreditScore() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );

        String message = "" + calculateCreditScore();

        channel.basicPublish( EXCHANGE_NAME, "", null, message.getBytes() ); //the message should be a command
        System.out.println( " [x] Sent '" + message + "'" );

        channel.close();
        connection.close();
    }

    public static int calculateCreditScore() {
        int response = 800;
        return response;
    }
}
