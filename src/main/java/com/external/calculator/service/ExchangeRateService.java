package com.external.calculator.service;

import com.external.calculator.dto.ExchangeRate;

public interface ExchangeRateService {
    public ExchangeRate getExchangeRate(String baseCurrency);
}
