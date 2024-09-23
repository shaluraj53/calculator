package com.external.calculator.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExchangeRateAPIResponse {
    private String result;
    @JsonProperty("base_code")
    private String baseCurrency;
    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates;
}
