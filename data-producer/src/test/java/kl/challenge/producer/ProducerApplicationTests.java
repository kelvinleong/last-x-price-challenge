package kl.challenge.producer;

import com.google.gson.Gson;
import kl.challenge.producer.configuration.RedisConfig;
import kl.challenge.producer.configuration.TestConfig;
import kl.challenge.producer.domain.Price;
import kl.challenge.producer.queue.PricePublisher;
import kl.challenge.producer.queue.PriceSubscriber;
import kl.challenge.producer.service.PublishService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RedisConfig.class, TestConfig.class})
@SpringBootTest(classes = ProducerApplication.class)
@TestPropertySource(locations="classpath:test.properties")
public class ProducerApplicationTests {

	@Autowired
	private PricePublisher pricePublisher;
	@Autowired
	private PublishService publishService;

	@Test
	public void publishServiceTest() throws IOException, InterruptedException {
		Price price = new Price();
		price.setTimestamp(System.currentTimeMillis());
		price.setPrice(publishService.makeRandomPrice());

		Gson gson = new Gson();
		String message = gson.toJson(price);
		pricePublisher.publish(message);
		TimeUnit.SECONDS.sleep(10);
		then(PriceSubscriber.messageList.get(0).contains(message));
	}
}
