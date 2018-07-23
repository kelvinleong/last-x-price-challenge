package kl.challenge.producer.service;

import com.google.gson.Gson;
import kl.challenge.producer.configuration.ScheduleTaskAsyncConfiguration;
import kl.challenge.producer.domain.Price;
import kl.challenge.producer.queue.PricePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Service
public class PublishService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${min.price}")
    private Double minPrice;
    @Value("${max.price}")
    private Double maxPrice;

    @Autowired
    private PricePublisher pricePublisher;

    public Double makeRandomPrice() {
        Double rand = ThreadLocalRandom.current().nextDouble(minPrice, maxPrice);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(rand));
    }

    @Async(ScheduleTaskAsyncConfiguration.EXECUTOR_NAME)
    public void publishPrice() {
        logger.info("publish price....");
        Price price = new Price();
        price.setTimestamp(System.currentTimeMillis());
        price.setPrice(makeRandomPrice());

        Gson gson = new Gson();
        pricePublisher.publish(gson.toJson(price));
    }
}
