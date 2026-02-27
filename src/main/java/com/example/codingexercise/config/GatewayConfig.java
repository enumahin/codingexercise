package com.example.codingexercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class that exposes HTTP client beans used for calling external services.
 */
@Configuration
public class GatewayConfig {

    /**
     * Creates a {@link RestTemplate} to be injected wherever simple HTTP calls are required.
     *
     * @return configured {@link RestTemplate} instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
