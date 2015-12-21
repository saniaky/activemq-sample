import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author saniaky
 * @since 12/19/15
 */
public class MsgProducer {

    private static final String BROKER_URL = "failover://tcp://127.0.0.1:61616";
    private static final String SUBJECT = "test-queue";

    public static void main(String[] args) throws JMSException, InterruptedException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination dest = session.createQueue(SUBJECT);
        MessageProducer producer = session.createProducer(dest);

        for (int i = 0; i < 100; i++) {
            TextMessage message = session.createTextMessage("Message " + i);
            producer.send(message);
            System.out.println("send message: " + i);
            Thread.sleep(3000);
        }

        connection.close();
    }
}
