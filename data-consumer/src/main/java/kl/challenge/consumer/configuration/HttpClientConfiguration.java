package kl.challenge.consumer.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kelvinleung on 22/7/2018.
 */
@Configuration
public class HttpClientConfiguration {
    @Autowired
    @Value("${spring.httpclient.pool.size.max:10}")
    private Integer poolMaxTotal;

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        // Get the poolMaxTotal value from our application[-?].xml or default to 10 if not explicitly set
        connectionManager.setMaxTotal(poolMaxTotal);

        return HttpClientBuilder
                .create()
                .setConnectionManager(connectionManager)
                .build();
    }
}