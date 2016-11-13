package com.mycompany.loanbroker.testDrivers;

import com.mycompany.loanbroker.reciplist.Data;
import com.mycompany.loanbroker.reciplist.ReceiveData;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSender {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory confactory = new ConnectionFactory();
        confactory.setHost("datdb.cphbusiness.dk");
        confactory.setUsername("what");
        confactory.setPassword("what");
        Connection connection = confactory.newConnection();

        Channel channel = connection.createChannel();

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.replyTo("whatRecipientList");
        AMQP.BasicProperties prop = builder.build();
        List<String> testList = new ArrayList();
        testList.add("bankJSON");
        testList.add("bankXML");
        Data data = new ReceiveData("123", 123, 10000, 3, testList);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(data);
        byte body[] = bos.toByteArray();

        channel.basicPublish("whatRecipientList","", prop, body);
        System.out.println(" [x] Sent '" + "'");

        channel.close();
        connection.close();
    }

}
