package externals;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import models.Data;
import com.mycompany.loanbroker.utilities.MessageUtility;
import java.util.ArrayList;

public class Customer {

    //to fix the rule base and get banks; make the RuleBase to GetBanks
    //to change the method calculateRelevantBanks in the RuleBase class. It should return an ArrayList, not a String
    //to look into command messages implementation with RabbitMQ; in this way we can maybe have 1 method for sending a request and getting the response
    private static final String EXCHANGE_NAME = "customer_direct_exchange";
    private static final MessageUtility msgUtility = new MessageUtility();

    public static void main( String[] argv ) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        Data data = new Data( "2309808219", 0, 3000, 2 );
        byte[] message = msgUtility.serializeBody( data );

        channel.basicPublish( EXCHANGE_NAME, "customer", null, message );
        System.out.println( " [x] Sent '" + data.toString() + "'" );

        channel.close();
        connection.close();
    }
}
