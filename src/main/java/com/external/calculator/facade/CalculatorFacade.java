package com.external.calculator.facade;

import com.external.calculator.dto.Order;
import com.external.calculator.dto.OrderPriceInput;

public interface CalculatorFacade {

    public Order calculateOrderPrice(final OrderPriceInput orderPriceInput);
}
