package patterns;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import externals.CreditBureau;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class GetCreditcore {
      private static final String EXCHANGE_NAME_CUSTOMER = "customer_exchange"; 
//    private static final String EXCHANGE_NAME_CREDIT_BUREAU = "customer_exchange";
    private static CreditBureau creditBureau;

    public static void main( String[] argv ) throws IOException, TimeoutException, InterruptedException {
        getCustomerRequest();
        sendRequestCreditScore();
        GetRequestCreditScore();
    }

    public static void getCustomerRequest() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME_CUSTOMER, "fanout" );
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME_CUSTOMER, "" );
        System.out.println( " [*] Waiting for messages. To exit press CTRL+C" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String( delivery.getBody() );

            System.out.println( " [x] Received from the customer '" + message + "'" );
        }
    }

    public static void sendRequestCreditScore() throws IOException, TimeoutException, InterruptedException { 
        creditBureau = new CreditBureau();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME_CUSTOMER, "fanout" );

        String message = "Give me the credit score for 3000$/2 years";

        channel.basicPublish( EXCHANGE_NAME_CUSTOMER, "", null, message.getBytes() ); //the message should be a command
        System.out.println( " [x] Sent request for credit score '" + message + "'" );

        creditBureau.getCreditInfo();

        channel.close();
        connection.close();
    }

    public static void GetRequestCreditScore() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "datdb.cphbusiness.dk" );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME_CUSTOMER, "fanout" );
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME_CUSTOMER, "" );
        System.out.println( "Queue name: " + queueName );
        System.out.println( " [*] Waiting for messages. To exit press CTRL+C" );

        QueueingConsumer consumer = new QueueingConsumer( channel );
        channel.basicConsume( queueName, true, consumer );
        while ( true ) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String( delivery.getBody() );

            System.out.println( " [x] Received credit score'" + message + "'" );
        }
    }

}
