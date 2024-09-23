package com.external.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.external.calculator.dto.OrderPriceInput;
import com.external.calculator.facade.CalculatorFacade;
import com.external.calculator.utils.CalculatorConstants;
import com.external.calculator.utils.RestResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/api")
public class CalculatorController {

    private final CalculatorFacade calculatorFacade;

    @Autowired
    public CalculatorController(CalculatorFacade calculatorFacade) {
        this.calculatorFacade = calculatorFacade;
    }

    @PostMapping(value = "/calculate")
    public ResponseEntity<RestResponse> calculateOrderPrice(
            @RequestBody final OrderPriceInput orderPriceInput,
            final HttpServletRequest request) {
        return new ResponseEntity<>(
                new RestResponse(Boolean.TRUE,
                        CalculatorConstants.RESPONSE_MESSAGE_ORDER_PRICE_RETRIEVED_SUCCESS,
                        calculatorFacade.calculateOrderPrice(orderPriceInput)),
                HttpStatus.OK);
    }
}
