package kl.challenge.producer.configuration;

import kl.challenge.producer.queue.PriceSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Created by kelvinleung on 22/7/2018.
 */
@Configuration
public class TestConfig {
    @Autowired
    @Qualifier(RedisConfig.REDIS_CONN)
    private JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    @Qualifier(RedisConfig.REDIS_TOPIC)
    private ChannelTopic channelTopic;

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new PriceSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(messageListener(), channelTopic);
        return container;
    }

}
