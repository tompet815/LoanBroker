package patterns;

import models.Data;
import models.Bank;
import com.mycompany.loanbroker.utilities.MessageUtility;
import com.mycompany.loanbroker.utilities.RuleBaseRequest;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.File;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceRef;
import models.DataToRecipientList;
import rulebasews.IOException_Exception;
import rulebasews.JAXBException_Exception;
import rulebasews.RuleBase;
import rulebasews.RuleBaseWS;
import static patterns.GetCreditScore.*;

public class GetBanks {

    private static final String EXCHANGE_NAME_CUSTOMER = "customer_direct_exchange";
    private static final String EXCHANGENAME = "whatRecipientList"; //exchange used for the recipList
    private static MessageUtility messageUtility = new MessageUtility();
    @WebServiceRef(wsdlLocation
            = "http://localhost:8080/RuleeBasedWS/RuleBase?WSDL")
    private static RuleBase service = new RuleBase();

    public static void main(String[] argv) throws Exception {
        getCreditScore();

    }

    public static void getCreditScore() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME_CUSTOMER, "banks");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] message = delivery.getBody();
            Data objectMessage = (Data) messageUtility.deSerializeBody(message);
            int creditScore = objectMessage.getCreditScore();
            String corrId = delivery.getProperties().getCorrelationId();
            System.out.println(" [x] Received from the credit score '" + objectMessage.toString() + "'");
            objectMessage.setBanks(getRelevantBanks(creditScore));
            sendRequestRecipList(objectMessage, corrId);
        }
    }

    public static ArrayList<Bank> getRelevantBanks(int creditScore) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, IOException_Exception, JAXBException_Exception, JAXBException {
        RuleBaseWS rbws = service.getRuleBaseWSPort();
        String xmlString = rbws.getRelevantBanks(creditScore);
        System.out.println("This is the xml string: " + xmlString);

        java.io.FileWriter fw = new java.io.FileWriter("relevantBanks.xml");
        fw.write(xmlString);
        fw.close();

        JAXBContext jc = JAXBContext.newInstance(RuleBaseRequest.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("relevantBanks.xml");
        RuleBaseRequest root = (RuleBaseRequest) unmarshaller.unmarshal(xml);

        System.out.println("The first bank is " + root.getRelevantBanks().get(0).getBankName() + ", " + root.getRelevantBanks().get(0).getType());
        System.out.println("The second bank is " + root.getRelevantBanks().get(1).getBankName() + ", " + root.getRelevantBanks().get(1).getType());
        return root.getRelevantBanks();
    }

    public static void sendRequestRecipList(Data objectToSend, String corrId) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.correlationId(corrId);
        AMQP.BasicProperties prop = builder.build();
        byte[] dataToSend = messageUtility.serializeBody(convertDataToXML(objectToSend));
        channel.basicPublish(EXCHANGENAME, "", prop, dataToSend);
        System.out.println(" [x] Sent request to recipient list: " + convertDataToXML(objectToSend) + "'");

        channel.close();
        connection.close();
    }

    public static String convertDataToXML(Data objectToSend) {
        DataToRecipientList dataToRecipientList = new DataToRecipientList(
                objectToSend.getBanks(), objectToSend.getSsn(), objectToSend.getCreditScore(),
                objectToSend.getLoanAmount(), objectToSend.getLoanDuration());

        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(DataToRecipientList.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            JAXBElement<DataToRecipientList> je2 = new JAXBElement(new QName(
                    "Data"), DataToRecipientList.class, dataToRecipientList);
            StringWriter sw = new StringWriter();
            marshaller.marshal(je2, sw);
            String xmlString = sw.toString();
            System.out.println("xml" + xmlString);
            return xmlString;
        }
        catch (JAXBException ex) {
            Logger.getLogger(GetBanks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Exception thrown";
    }

}
