package com.microservices.workshop.orders.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // enable async processing in the current project
public class AsyncConfig implements AsyncConfigurer {

    private final Executor asyncExecutor;

    @Autowired
    public AsyncConfig(final Executor asyncExecutor) {
        this.asyncExecutor = asyncExecutor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncExecutor; // will become the default async executor
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncUncaughtExceptionHandler();
    }
}
