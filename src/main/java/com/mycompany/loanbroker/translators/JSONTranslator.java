package com.mycompany.loanbroker.translators;

import com.google.gson.Gson;
import com.mycompany.loanbroker.connector.RabbitMQConnector;
import com.mycompany.loanbroker.interfaces.IMessaging;
import com.mycompany.loanbroker.reciplist.Data;
import com.mycompany.loanbroker.reciplist.RecipientList;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

public class JSONTranslator implements IMessaging {

    private final RabbitMQConnector connector = new RabbitMQConnector();

    private Channel channel;
    private String queueName;
    private final String EXCHANGENAME = "whatTranslator.bankJSON";
    private final String BANKEXCHANGENAME = "cphbusiness.bankJSON";
    private final MessageUtility util = new MessageUtility();

    @Override
    public void connect() throws IOException {
        channel = connector.getChannel();
        channel.exchangeDeclare(EXCHANGENAME, "direct");
        queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGENAME, "");
    }

    @Override
    public boolean receive() throws IOException {

        System.out.println(" [*] Waiting for messages.");
        final Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println(" [x] Received ");
                send(properties, body);
            }
        };
        channel.basicConsume(queueName, true, consumer);
        return true;
    }

    @Override
    public boolean send(AMQP.BasicProperties prop, byte[] body) throws IOException {

        try {

            Data data = (Data) util.deSerializeBody(body);
            int months=data.getLoanDuration()*12;
            data.setLoanDuration(months);
            Gson gson = new Gson();
            String jsonString = gson.toJson(data);
            System.out.println("JSON:" + jsonString);
            //channel.basicPublish(BANKEXCHANGENAME, "", prop, jsonString.getBytes());
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
