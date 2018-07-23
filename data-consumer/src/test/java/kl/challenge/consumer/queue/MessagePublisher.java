package kl.challenge.consumer.queue;

/**
 * Created by kelvinleung on 22/7/2018.
 */
public interface MessagePublisher {
    void publish(final String message);
}
