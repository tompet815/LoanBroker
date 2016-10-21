package externals;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Customer {

    //to fix the rule base and get banks; make the RuleBase to GetBanks
    //to change the method calculateRelevantBanks in the RuleBase class. It should return an ArrayList, not a String
    //to look into command messages implementation with RabbitMQ; in this way we can maybe have 1 method for sending a request and getting the response
    
    
    private static final String EXCHANGE_NAME = "customer_direct_exchange";

    public static void main( String[] argv ) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = "I want to loan 3000$. I want to pay in 2 years";

        channel.basicPublish( EXCHANGE_NAME, "customer", null, message.getBytes() );
        System.out.println( " [x] Sent '" + message + "'" );

        channel.close();
        connection.close();
    }
}
