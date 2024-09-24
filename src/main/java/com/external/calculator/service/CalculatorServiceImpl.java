package com.external.calculator.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.chain.manager.ChainManager;
import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.OrderPriceInput;
import com.external.calculator.helper.PricingHelper;

@Component
public class CalculatorServiceImpl implements CalculatorService {

    private ChainManager chainManager;
    private PricingHelper pricingHelper;
    private ExchangeRateService exchangeRateService;

    @Autowired
    public CalculatorServiceImpl(PricingHelper pricingHelper, ChainManager chainManager,
            ExchangeRateService exchangeRateService) {
        this.pricingHelper = pricingHelper;
        this.chainManager = chainManager;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public Order calculateOrderPrice(OrderPriceInput orderPriceInput) {
        Order order = pricingHelper.convertPriceInputToOrder(orderPriceInput);
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(orderPriceInput.getBaseCurrency());
        if (order != null) {
            populateChainContext(orderPriceInput, order);
            ChainContext chainContext = populateChainContext(orderPriceInput, order);

            Double conversionRate = exchangeRate != null &&
                    exchangeRate.getConversionRates() != null
                            ? exchangeRate.getConversionRates().get(orderPriceInput.getForeignCurrency())
                            : 1;
            chainContext.setConversionRate(conversionRate);

            chainManager.processChain(chainContext);
            return chainContext.getOrder();
        }
        return null;
    }

    private ChainContext populateChainContext(OrderPriceInput orderPriceInput, Order order) {
        ChainContext chainContext = new ChainContext(order);
        BeanUtils.copyProperties(orderPriceInput, chainContext);
        return chainContext;
    }
}
