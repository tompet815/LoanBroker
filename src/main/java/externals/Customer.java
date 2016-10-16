package externals;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Customer {

    //we can maybe use public keys and 1 exchange
    //to do the connection between the customer details and the credit score; to take the details from the customer queue and send them to the credit queue
    //I will look into command messages implementation with RabbitMQ; in this way we can maybe have 1 method for sending a request and getting the response
    
    private static final String EXCHANGE_NAME = "customer_exchange";

    public static void main( String[] argv ) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );

        String message = "I want to loan 3000$. I want to pay in 2 years";

        channel.basicPublish( EXCHANGE_NAME, "", null, message.getBytes() );
        System.out.println( " [x] Sent '" + message + "'" );

        channel.close();
        connection.close();
    }
}
