package com.example.productservice.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfigurations {
    @Bean
    @LoadBalanced // Client side Load Balancing
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
