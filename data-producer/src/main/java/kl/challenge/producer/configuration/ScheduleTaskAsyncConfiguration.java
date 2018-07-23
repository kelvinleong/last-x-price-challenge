package kl.challenge.producer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Configuration
@EnableAsync
public class ScheduleTaskAsyncConfiguration {
    @Value("${price.schedule.task.pool.max.size:10}")
    private int maxPoolSize;
    @Value("${price.schedule.task.pool.min.size:10}")
    private int minPoolSize;
    @Value("${price.schedule.task.pool.queue.capacity:3600000}")
    private int queueCapacity;
    public static final String EXECUTOR_NAME = "scheduleTaskPool";

    @Bean(name = EXECUTOR_NAME)
    public Executor getAsyncExecutor() {
        return BaseAsynConfigurationUtil.getAsyncExecutor(minPoolSize, maxPoolSize, queueCapacity, EXECUTOR_NAME + "-");
    }
}