package kl.challenge.producer.queue;

/**
 * Created by kelvinleung on 21/7/2018.
 */
public interface MessagePublisher {
    void publish(final String message);
}
