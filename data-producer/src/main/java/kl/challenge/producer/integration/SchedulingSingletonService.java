package kl.challenge.producer.integration;

import kl.challenge.producer.service.PublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Service
@ConditionalOnProperty("price.publish.enable")
public class SchedulingSingletonService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String ZONE_ID_HK = "Asia/Hong_Kong";

    @Autowired
    private PublishService publishService;

    @Scheduled(cron = "${price.publish.scheduling}", zone = ZONE_ID_HK)
    public void publishPriceTask() {
        try {
            publishService.publishPrice();
        } catch (Exception e) {
            logger.error("Publish Price Task Error. " + e.getMessage());
        }
    }

}
