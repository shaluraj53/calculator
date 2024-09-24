package com.external.calculator.service;

import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.ExchangeRateAPIResponse;
import com.external.calculator.utils.RestUtils;

import java.util.*;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final Set<ExchangeRate> exchangeRates;
    private RestUtils restUtils;
    @Value("${service.configs.exchangeRateEndpoint}")
    private String exchangeRateBaseURL;
    @Value("${service.configs.exchangeRateEndpointKey}")
    private String exchangeRateAPIKey;

    @Autowired
    public ExchangeRateServiceImpl(Set<ExchangeRate> exchangeRates, RestUtils restUtils) {
        this.exchangeRates = exchangeRates;
        this.restUtils = restUtils;
    }

    @Override
    public ExchangeRate getExchangeRate(String baseCurrency) {
        Optional<ExchangeRate> exchangeRateOptnl = exchangeRates.stream()
                .filter(e -> StringUtils.equalsIgnoreCase(e.getBaseCurrency(), baseCurrency)).findFirst();
        if (exchangeRateOptnl.isPresent() &&
                exchangeRateOptnl.get().getUpdatedOn().isEqual(LocalDate.now())) {
            return exchangeRateOptnl.get();
        }
        if (!exchangeRateOptnl.isPresent() ||
                exchangeRateOptnl.get().getUpdatedOn().isBefore(LocalDate.now())) {
            String erAPIURL = new StringBuilder(exchangeRateBaseURL).append(exchangeRateAPIKey)
                    .append("/latest/").append(baseCurrency).toString();
            ExchangeRateAPIResponse erAPIResponse = restUtils.get(erAPIURL, ExchangeRateAPIResponse.class);
            if (erAPIResponse != null && StringUtils.equalsIgnoreCase(erAPIResponse.getResult(), "success")) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setBaseCurrency(baseCurrency);
                exchangeRate.setUpdatedOn(LocalDate.now());
                exchangeRate.setConversionRates(erAPIResponse.getConversionRates());
                exchangeRates.add(exchangeRate);
                return exchangeRate;
            }
        }

        return null;
    }

}
