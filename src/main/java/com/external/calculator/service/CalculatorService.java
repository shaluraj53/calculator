package com.external.calculator.service;

import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.OrderPriceInput;

public interface CalculatorService {

    public Order calculateOrderPrice(final OrderPriceInput orderPriceInput);

    public ExchangeRate getExchangeRate(String baseCurrency);
}
