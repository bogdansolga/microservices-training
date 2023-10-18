package net.safedata.microservices.training.config;

import net.safedata.microservices.training.helper.MessagePublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class HelperBeansConfig {

    @Bean
    public MessagePublisher messagePublisher(StreamBridge streamBridge) {
        return new MessagePublisher(streamBridge);
    }
}
