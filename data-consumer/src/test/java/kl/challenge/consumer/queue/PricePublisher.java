package kl.challenge.consumer.queue;

import kl.challenge.consumer.configuration.RedisConfig;
import kl.challenge.consumer.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * Created by kelvinleung on 22/7/2018.
 */
@Service
public class PricePublisher implements MessagePublisher {
    @Autowired
    private RedisTemplate<String, Object> publishRedisTemplate;
    @Autowired
    private ChannelTopic topic;

    public PricePublisher() {
    }

    public PricePublisher(final RedisTemplate<String, Object> publishRedisTemplate, final ChannelTopic topic) {
        this.publishRedisTemplate = publishRedisTemplate;
        this.topic = topic;
    }

    public void publish(final String message) {
        publishRedisTemplate.convertAndSend(topic.getTopic(), message);
    }
}

