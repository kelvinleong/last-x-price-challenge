package kl.challenge.producer.queue;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.connection.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvinleung on 22/7/2018.
 */
@Service
public class PriceSubscriber implements MessageListener {
    public static List<String> messageList = new ArrayList<String>();

    public void onMessage(final Message message, final byte[] pattern) {
        messageList.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}
