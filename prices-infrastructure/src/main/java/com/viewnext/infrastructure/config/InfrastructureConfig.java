package com.viewnext.infrastructure.config;

import com.viewnext.infrastructure.mapper.PriceMapper;
import com.viewnext.infrastructure.mapper.PriceMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfig {

    @Bean
    public PriceMapper priceMapper() {
        return new PriceMapperImpl();
    }
}