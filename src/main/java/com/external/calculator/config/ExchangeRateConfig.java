package com.external.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.external.calculator.dto.ExchangeRate;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ExchangeRateConfig {
    private final Set<ExchangeRate> exchangeRates = new HashSet<>();

    @Bean
    public Set<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void updateExchangeRateMapMap(ExchangeRate exchangeRate) {
        exchangeRates.add(exchangeRate);
    }
}
