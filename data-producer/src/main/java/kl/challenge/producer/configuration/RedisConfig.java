package kl.challenge.producer.configuration;

import kl.challenge.producer.queue.MessagePublisher;
import kl.challenge.producer.queue.PricePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


/**
 * Created by kelvinleung on 21/7/2018.
 */
@Configuration
public class RedisConfig {
    public static final String REDIS_TEMPLATE = "redisTemplate";
    public static final String REDIS_PUBLISER = "redisPublisher";
    public static final String REDIS_CONN = "redisConnnectionFactory";
    public static final String REDIS_TOPIC = "redisTopic";

    @Value("${redis.server}")
    private String host;
    @Value("${redis.port:6379}")
    private int port;
    @Value(("${price.queue.name}"))
    private String priceQName;

    @Bean(name = REDIS_CONN)
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        JedisConnectionFactory jRedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        jRedisConnectionFactory.getPoolConfig().setMaxTotal(1000);
        jRedisConnectionFactory.getPoolConfig().setMaxIdle(10);
        jRedisConnectionFactory.getPoolConfig().setMinIdle(1);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = REDIS_TEMPLATE)
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean(name = REDIS_PUBLISER)
    MessagePublisher redisPublisher() {
        return new PricePublisher(redisTemplate(), topic());
    }

    @Bean(name = REDIS_TOPIC)
    ChannelTopic topic() {
        return new ChannelTopic(priceQName);
    }

}
