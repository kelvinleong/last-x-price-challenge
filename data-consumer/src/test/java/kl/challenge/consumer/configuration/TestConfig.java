package kl.challenge.consumer.configuration;

import kl.challenge.consumer.domain.Price;
import kl.challenge.consumer.queue.MessagePublisher;
import kl.challenge.consumer.queue.PricePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by kelvinleung on 22/7/2018.
 */
@Configuration
public class TestConfig {
    @Autowired
    @Qualifier(RedisConfig.REDIS_TOPIC)
    private ChannelTopic topic;

    @Autowired
    @Qualifier(RedisConfig.REDIS_CONN)
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public <String,V> RedisTemplate<String,V> publishRedisTemplate(){
        RedisTemplate<String,V> redisTemplate =  new RedisTemplate<String, V>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new PricePublisher(publishRedisTemplate(), topic);
    }
}
