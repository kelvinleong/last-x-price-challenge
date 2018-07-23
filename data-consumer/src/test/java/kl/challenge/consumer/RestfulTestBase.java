package kl.challenge.consumer;

import kl.challenge.consumer.configuration.RedisConfig;
import kl.challenge.consumer.configuration.TestConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RedisConfig.class, TestConfig.class})
@SpringBootTest(classes = ConsumerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class RestfulTestBase {
    @LocalServerPort
    private int port;

    @Value("${management.port}")
    private int mgt;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    protected String getRequestBasePath () {
        return "http://localhost:" + this.port;
    }

    protected String getMgtBasePath () {
        return "http://localhost:" + this.mgt;
    }
}
