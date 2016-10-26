package externals;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class RuleBase {

    private static ArrayList<String> banks;
    private static final String EXCHANGE_NAME = "customer_direct_exchange";

    public static void main( String[] args ) throws IOException, TimeoutException, InterruptedException {
        getRequestFromGetBanks();
    }

    public RuleBase() {

    }

    public static void getRequestFromGetBanks() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME, "rule_base" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String( delivery.getBody() );

            System.out.println( " [x] Received from the get banks '" + message + "'" );

            sendRelevantBanks( message );

        }
    }

    public static String calculateRelevantBanks( String message ) {
        banks = new ArrayList<>();
        banks.add( "Danske Bank" );
        banks.add( "Nordea" );
        banks.add( "Jyske Bank" );
        String[] msgElements = message.split( ": " );
        int length = msgElements.length;
        int credScore = Integer.parseInt( msgElements[ length - 1 ] );
        String result = "";
        if ( credScore >= 500 ) {
            result = banks.get( 0 ) + ", " + banks.get( 1 );
        } else if ( credScore >= 300 ) {
            result = banks.get( 1 ) + ", " + banks.get( 1 );
        } else {
            result = banks.get( 2 ) + ", " + banks.get( 0 );
        }
        return result;
    }

    public static void sendRelevantBanks( String inputMessage ) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = inputMessage;
        String relevant_banks = calculateRelevantBanks( message );
        String responseMessage= message + "; relevant banks: "+ relevant_banks;

        channel.basicPublish( EXCHANGE_NAME, "relevant_banks", null, responseMessage.getBytes() );
        System.out.println( " [x] Sent request to GetBanks '" + responseMessage + "'" );

        channel.close();
        connection.close();
    }
}
