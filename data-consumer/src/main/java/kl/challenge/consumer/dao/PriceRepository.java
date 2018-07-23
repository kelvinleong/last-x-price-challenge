package kl.challenge.consumer.dao;

import kl.challenge.consumer.configuration.RedisConfig;
import kl.challenge.consumer.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Repository
public class PriceRepository implements IRedisList<String, Price>{
    @Autowired
    @Qualifier(RedisConfig.REDIS_TEMPLATE)
    private RedisTemplate<String, Price> redisTemplate;

    @Override
    public void leftPush(String key, Price value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Collection<Price> getAll(String key) {
        return redisTemplate.opsForList().range(key,0,-1);
    }

    @Override
    public List<Price> getLatestXPrices(String key, Long number) {
        return redisTemplate.opsForList().range(key, 0, number);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForList().size(key);
    }
}
