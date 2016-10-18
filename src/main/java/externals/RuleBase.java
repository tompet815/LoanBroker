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
        this.banks = new ArrayList<>();
        banks.add( "Danske Bank" );
        banks.add( "Nordea" );
        banks.add( "Jyske Bank" );
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

        }
    }

    public static String calculateRelevantBanks( String creditScore ) {
        int credScore = Integer.parseInt( creditScore );
        if ( credScore >= 500 ) {
            return banks.get( 0 );
        } else if ( credScore >= 300 ) {
            return banks.get( 1 );
        } else {
            return banks.get( 2 );
        }
    }

    public static void sendRelevantBanks( String creditScore ) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = creditScore;

        channel.basicPublish( EXCHANGE_NAME, "relevant_banks", null, message.getBytes() );
        System.out.println( " [x] Sent request to rule base '" + message + "'" );

        String relevant_bank = calculateRelevantBanks( message );

        channel.close();
        connection.close();
    }
}