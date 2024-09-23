package com.external.calculator.dto;

import java.time.LocalDate;
import java.util.Map;

import lombok.Data;

@Data
public class ExchangeRate {
    private String baseCurrency;
    private LocalDate updatedOn;
    private Map<String, Double> conversionRates;
}
