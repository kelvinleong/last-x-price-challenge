package kl.challenge.consumer.queue;

import com.google.gson.Gson;
import kl.challenge.consumer.dao.PriceRepository;
import kl.challenge.consumer.domain.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Service
public class PriceSubscriber implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${price.queue.name}")
    private String queueName;

    @Autowired
    private PriceRepository priceRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Gson gson = new Gson();
        Price price = gson.fromJson(message.toString(), Price.class);
        logger.info("Received: " +message.toString());

        // lpush to redis server queue
        priceRepository.leftPush(queueName, price);
    }

}
