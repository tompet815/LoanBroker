//package externals;
//
//import com.mycompany.loanbroker.reciplist.Data;
//import com.mycompany.loanbroker.utilities.MessageUtility;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.QueueingConsumer;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.concurrent.TimeoutException;
//
//public class RuleBase {
//
//    private static ArrayList<String> allBanks;
//    private static ArrayList<String> relevantBanks;
//    private static final String EXCHANGE_NAME = "customer_direct_exchange";
//    private static MessageUtility messageUtility = new MessageUtility();
//
//    public static void main( String[] args ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
//        getRequestFromGetBanks();
//    }
//
//    public RuleBase() {
//
//    }
//
//    public static void getRequestFromGetBanks() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost( "datdb.cphbusiness.dk" );
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind( queueName, EXCHANGE_NAME, "rule_base" );
//
//        QueueingConsumer consumer = new QueueingConsumer( channel );
//        channel.basicConsume( queueName, true, consumer );
//        while ( true ) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            byte[] message = delivery.getBody();
//
//            System.out.println( " [x] Received from the get banks '" + messageUtility.deSerializeBody( message ).toString() + "'" );
//
//            sendRelevantBanks( message );
//
//        }
//    }
//
//    public static ArrayList<String> calculateRelevantBanks( byte[] message ) throws IOException, ClassNotFoundException {
//        allBanks = new ArrayList<>();
//        allBanks.add( "Danske Bank" );
//        allBanks.add( "Nordea" );
//        allBanks.add( "Jyske Bank" );
//        relevantBanks = new ArrayList<String>();
//        Data objectMessage = ( Data ) messageUtility.deSerializeBody( message );
//        int credScore = objectMessage.getCreditScore();
//        if ( credScore >= 500 ) {
//            relevantBanks.add( allBanks.get( 0 ) );
//            relevantBanks.add( allBanks.get( 1 ) );
//        } else if ( credScore >= 300 ) {
//            relevantBanks.add( allBanks.get( 1 ) );
//            relevantBanks.add( allBanks.get( 2 ) );
//        } else {
//            relevantBanks.add( allBanks.get( 0 ) );
//            relevantBanks.add( allBanks.get( 2 ) );
//        }
//        return relevantBanks;
//    }
//
//    public static void sendRelevantBanks( byte[] inputMessage ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost( "datdb.cphbusiness.dk" );
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        ArrayList<String> relevant_banks = calculateRelevantBanks( inputMessage );
//        Data inputObject = ( Data ) messageUtility.deSerializeBody( inputMessage );
//        inputObject.setBanks( relevant_banks );
//        byte[] responseMessage= messageUtility.serializeBody( inputObject);
//
//        channel.basicPublish( EXCHANGE_NAME, "relevant_banks", null, responseMessage );
//        System.out.println( " [x] Sent request to GetBanks '" 
//                + messageUtility.deSerializeBody( responseMessage).toString() + "'" );
//
//        channel.close();
//        connection.close();
//    }
//}
