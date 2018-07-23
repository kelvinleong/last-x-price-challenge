package kl.challenge.consumer.dao;

import java.util.Collection;
import java.util.List;

/**
 * Created by kelvinleung on 21/7/2018.
 */
public interface IRedisList<K,V> {
    void leftPush(K key, V value);

    Collection<V> getAll(K key);

    List<V> getLatestXPrices(K key, Long number);

    void delete(K key);

    Long size(K key);
}