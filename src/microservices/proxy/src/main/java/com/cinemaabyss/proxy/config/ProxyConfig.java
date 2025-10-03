package com.cinemaabyss.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProxyConfig {

    @Bean
    public WebClient monolithWebClient(@Value("${integration.url.monolith}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient moviesWebClient(@Value("${integration.url.movies}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient eventsWebClient(@Value("$${integration.url.events}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}



