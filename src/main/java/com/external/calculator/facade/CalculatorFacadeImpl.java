package com.external.calculator.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.external.calculator.dto.Order;
import com.external.calculator.dto.OrderPriceInput;
import com.external.calculator.service.CalculatorService;

@Component
public class CalculatorFacadeImpl implements CalculatorFacade {

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorFacadeImpl(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public Order calculateOrderPrice(OrderPriceInput orderPriceInput) {
        return calculatorService.calculateOrderPrice(orderPriceInput);
    }

}
