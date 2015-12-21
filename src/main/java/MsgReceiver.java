import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author saniaky
 * @since 12/19/15
 */
public class MsgReceiver implements MessageListener {

    private static final String BROKER_URL = "failover://tcp://127.0.0.1:61616";
    private static final String SUBJECT = "test-queue";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection conn = connectionFactory.createConnection();
        conn.start();

        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = session.createQueue(SUBJECT);
        MessageConsumer consumer = session.createConsumer(dest);
        MsgReceiver msgConsumer = new MsgReceiver();
        consumer.setMessageListener(msgConsumer);
    }

    public void onMessage(Message message) {
        TextMessage txtMessage = (TextMessage) message;
        try {
            System.out.println("get message: " + txtMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
