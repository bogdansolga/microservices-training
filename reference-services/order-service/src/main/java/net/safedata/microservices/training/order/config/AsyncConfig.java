package net.safedata.microservices.training.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor customThreadPool() {
        ThreadPoolTaskExecutor customThreadPool = new ThreadPoolTaskExecutor();
        customThreadPool.setCorePoolSize(1);
        customThreadPool.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        return customThreadPool;
    }
}
