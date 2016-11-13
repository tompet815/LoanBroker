package com.mycompany.loanbroker.translators;

import com.mycompany.loanbroker.connector.RabbitMQConnector;
import com.mycompany.loanbroker.interfaces.IMessaging;
import com.mycompany.loanbroker.reciplist.Data;
import com.mycompany.loanbroker.reciplist.RecipientList;
import com.mycompany.loanbroker.reciplist.XMLData;
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

public class XMLTranslator implements IMessaging {

    private final RabbitMQConnector connector = new RabbitMQConnector();

    private Channel channel;
    private String queueName;
    private final String EXCHANGENAME = "whatTranslator.bankXML";
    private final String BANKEXCHANGENAME = "cphbusiness.bankXML";
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

            JAXBContext jc = JAXBContext.newInstance(XMLData.class);
            Data data = (Data) util.deSerializeBody(body);
            XMLData xmlData=util.convertToXMLData(data);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            JAXBElement<XMLData> je2 = new JAXBElement(new QName("LoanRequest"), XMLData.class, xmlData);
            StringWriter sw = new StringWriter();
            marshaller.marshal(je2, sw);
            String xmlString = sw.toString();
            System.out.println("xml" + xmlString);
            channel.basicPublish(BANKEXCHANGENAME, "", prop, xmlString.getBytes());
            return true;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(RecipientList.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JAXBException ex) {
            Logger.getLogger(XMLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        connector.close(channel);
    }
}
