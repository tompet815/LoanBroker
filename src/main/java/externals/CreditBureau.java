package externals;

import com.mycompany.loanbroker.reciplist.Data;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreditBureau {

    private static final String EXCHANGE_NAME = "customer_direct_exchange";
    private static MessageUtility messageUtility = new MessageUtility();

    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        getCreditInfo();
    }

    public static void getCreditInfo() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
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
            byte[] message = delivery.getBody();

            System.out.println( " [x] Received '" + messageUtility.deSerializeBody( message ).toString() + "'" );
            returnCreditScore( message );

        }
    }

    public static void returnCreditScore( byte[] clientInput ) throws IOException, TimeoutException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Data message = ( Data ) messageUtility.deSerializeBody( clientInput );
        message.setCreditScore( calculateCreditScore() );

        channel.basicPublish( EXCHANGE_NAME, "credit_score", null, messageUtility.serializeBody( message ) );
        System.out.println( " [x] Sent '" + message.toString() + "'" );

        channel.close();
        connection.close();
    }

    public static int calculateCreditScore() {
        int response = 800;
        return response;
    }
}
