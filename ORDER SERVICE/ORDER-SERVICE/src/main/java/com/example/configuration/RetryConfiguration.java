package com.example.configuration;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

@Configuration
public class RetryConfiguration {

	@Bean
    public Retry myServiceRetry() {
        return Retry.of("OrderFailiure", RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofSeconds(8))
            .retryExceptions(IOException.class)
            .ignoreExceptions(SocketTimeoutException.class)
            .build());
    }	
	
}
