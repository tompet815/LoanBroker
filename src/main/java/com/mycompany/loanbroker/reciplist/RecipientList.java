package com.mycompany.loanbroker.reciplist;

import com.mycompany.loanbroker.connector.RabbitMQConnector;
import com.mycompany.loanbroker.interfaces.IMessaging;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipientList implements IMessaging {
    private final RabbitMQConnector connector =new RabbitMQConnector();
    private Channel channel;
    private String queueName;
    private final String EXCHANGENAME = "whatRecipientList";
    private final MessageUtility util = new MessageUtility();

    @Override
    public void connect() throws IOException {
        channel =connector.getChannel();
        channel.exchangeDeclare(EXCHANGENAME, "direct");
        queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGENAME, "");
    }

    @Override
    public boolean receive() throws IOException {

        System.out.println(" [*] Waiting for messages.");
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" [x] Received ");
                send(properties, body);
            }
        };
        channel.basicConsume(queueName, true, consumer);
        return true;
    }

    @Override
    public boolean send(BasicProperties prop, byte[] body) throws IOException {
        try {
            ReceiveData data = (ReceiveData) util.deSerializeBody(body);
            Data d=new Data(data.getSsn(),data.getCreditScore(),data.getLoanAmoount(),data.getLoanDuration());
            List<String> bankExchangeNames = data.getBankExchangeNames();
            for (String name : bankExchangeNames) {
                String translatorExchangeName = "whatTranslator." + name;              
                body = util.serializeBody(d);
                System.out.println("sending from rl: "+(d).toString());
                channel.basicPublish(translatorExchangeName, "", prop, body);
                
                //create Aggregator here 
            }
            return true;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(RecipientList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        connector.close(channel);
    }

}
