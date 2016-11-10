/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.what.bankws.BankWS;
import com.what.bankws.InterestRateService;
import com.what.bankws.JAXBException_Exception;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Tomoe
 */
public class WSTranslator implements IMessaging{
    
    private final RabbitMQConnector connector = new RabbitMQConnector();
 @WebServiceRef(wsdlLocation = 
      "http://localhost:8080/BankWS/InterestRateService?WSDL")
    private static InterestRateService service;

    private Channel channel;
    private String queueName;
    private final String EXCHANGENAME = "whatTranslator.bankWS";
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
            getInterestRate(data.getSsn(),data.getCreditScore(),data.getLoanAmoount(),data.getLoanDuration(),prop.getReplyTo());
            
            return true;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(RecipientList.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JAXBException_Exception ex) {
            Logger.getLogger(WSTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void close() throws IOException {
        connector.close(channel);
    }
   
    private static String getInterestRate(String ssn, int creditScore, double loanAmoount, int loanDuration,String replyTo) throws JAXBException_Exception {
        BankWS port = service.getBankWSPort();
        return port.getInterestRate(ssn,creditScore,loanAmoount,loanDuration,replyTo);
    }
}
    

