package kl.challenge.producer.configuration;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by kelvinleung on 21/7/2018.
 */
public class BaseAsynConfigurationUtil {
    public static Executor getAsyncExecutor(int minPoolSize, int maxPoolSize, int queueCapacity, String namePrefxi) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(minPoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(namePrefxi);
        executor.initialize();
        return executor;
    }
}
