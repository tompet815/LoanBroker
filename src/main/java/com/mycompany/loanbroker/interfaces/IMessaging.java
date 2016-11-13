package com.mycompany.loanbroker.interfaces;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;

public interface IMessaging {

    boolean receive() throws IOException;

    boolean send(BasicProperties prop, byte[] body) throws IOException;

    void connect() throws IOException;

    void close() throws IOException;
}
