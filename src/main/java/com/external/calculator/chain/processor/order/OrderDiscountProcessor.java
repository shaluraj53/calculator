package com.external.calculator.chain.processor.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.calculator.OrderDiscountCalculator;
import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.chain.processor.ChainProcessor;
import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.Order;
import com.external.calculator.service.ExchangeRateService;

import java.util.Set;

@Component
public class OrderDiscountProcessor implements ChainProcessor {

    private OrderDiscountCalculator orderDiscountCalculator;

    private ExchangeRateService exchangeRateService;

    @Autowired
    public OrderDiscountProcessor(OrderDiscountCalculator orderDiscountCalculator,
            Set<ExchangeRate> exchangeRates, ExchangeRateService exchangeRateService) {
        this.orderDiscountCalculator = orderDiscountCalculator;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void process(ChainContext chainContext) {
        if (chainContext != null && chainContext.getOrder() != null) {
            Order order = chainContext.getOrder();

            String baseCurrency = chainContext.getBaseCurrency();
            ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(baseCurrency);
            double conversionRate = exchangeRate != null && exchangeRate.getConversionRates() != null
                    ? exchangeRate.getConversionRates().get("USD")
                    : 1;

            double netPrice = order.getPriceInfo() != null && order.getPriceInfo().get(baseCurrency) != null
                    ? order.getPriceInfo().get(baseCurrency).getNetPrice()
                    : 0;
            double netPriceInUSD = netPrice / conversionRate;
            if (netPriceInUSD < 100) {
                return; // No discount for orders less than $100
            }
            orderDiscountCalculator.compute(chainContext);
        }
    }

}
