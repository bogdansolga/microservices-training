package com.microservices.workshop.billing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync // enable async processing in the current project
public class AsyncConfig {
}
