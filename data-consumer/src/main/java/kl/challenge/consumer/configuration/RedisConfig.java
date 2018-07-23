package kl.challenge.consumer.configuration;

import kl.challenge.consumer.queue.PriceSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Configuration
public class RedisConfig {
    public static final String REDIS_SUBSCRIBER_POOL = "redisSubscriberPool";
    public static final String REDIS_CONN = "redisConnnectionFactory";
    public static final String REDIS_TEMPLATE = "redisTemplate";
    public static final String REDIS_POOL_CONFIG = "redisPoolConfig";
    public static final String REDIS_TOPIC = "redisTopic";

    @Value("${redis.server}")
    private String host;
    @Value("${redis.port:6379}")
    private int port;
    @Value("${price.topic}")
    private String priceTopic;

    @Autowired
    private PriceSubscriber priceSubscriber;

    @Bean(name = REDIS_POOL_CONFIG)
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1000);
        config.setMaxIdle(10);
        config.setMinIdle(1);
        return config;
    }

    @Bean(name = REDIS_SUBSCRIBER_POOL)
    public JedisPool getSubscriberPool() {
        return new JedisPool(getJedisPoolConfig(), host, port, 100000);
    }

    @Bean(name = REDIS_CONN)
    public RedisConnectionFactory getConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = REDIS_TEMPLATE)
    public <String,V> RedisTemplate<String,V> getRedisTemplate(){
        RedisTemplate<String,V> redisTemplate =  new RedisTemplate<String, V>();
        redisTemplate.setConnectionFactory(getConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = REDIS_TOPIC)
    ChannelTopic topic() {
        return new ChannelTopic(priceTopic);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(getConnectionFactory());
        container.addMessageListener(priceSubscriber, topic());
        return container;
    }
}
