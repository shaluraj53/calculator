package com.external.calculator.chain.calculator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.dto.DiscountInfo;
import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.PriceInfo;
import com.external.calculator.service.ExchangeRateService;
import com.external.calculator.utils.CalculatorConstants;
import com.external.calculator.utils.MathUtil;

import java.util.*;

@Component
public class OrderDiscountCalculator implements Calculator {

    private double amountOff = 5.0; // Amountoff for every $100 in netprice
    private ExchangeRateService exchangeRateService;

    @Autowired
    public OrderDiscountCalculator(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void compute(ChainContext chainContext) {

        Order order = chainContext.getOrder();
        String baseCurrency = chainContext.getBaseCurrency();
        String foreignCurrency = chainContext.getForeignCurrency();
        Map<String, PriceInfo> priceInfo = order.getPriceInfo();
        if (priceInfo != null) {
            PriceInfo basePriceInfo = priceInfo.get(baseCurrency);
            PriceInfo foreignPriceInfo = priceInfo.get(foreignCurrency);
            // we have $5 off for every $100 in bill
            if (basePriceInfo != null) {
                computeOrderDiscount(baseCurrency, basePriceInfo);
            }
            if (foreignPriceInfo != null) {
                computeOrderDiscount(foreignCurrency, foreignPriceInfo);
            }
        }

    }

    private void computeOrderDiscount(String currency, PriceInfo priceInfo) {
        double netPrice = priceInfo.getNetPrice();
        double conversionRate = getExchangeConversionRate(currency, "USD");
        double netPriceInUSD = netPrice * conversionRate;
        int hundreds = (int) (netPriceInUSD / 100);
        double discountInUSD = hundreds * amountOff;
        netPriceInUSD = netPriceInUSD - discountInUSD;

        netPrice = netPriceInUSD / conversionRate;
        double discount = discountInUSD / conversionRate;
        priceInfo.setNetPrice(MathUtil.roundToTwoDecimalPoint(netPrice).doubleValue());

        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setAmount(MathUtil.roundToTwoDecimalPoint(discount).doubleValue());
        discountInfo.setType(CalculatorConstants.ORDER_DISCOUNT);
        priceInfo.setDiscountInfo(discountInfo);

    }

    private double getExchangeConversionRate(String base, String target) {
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(base);
        return exchangeRate != null && exchangeRate.getConversionRates() != null
                ? exchangeRate.getConversionRates().get(target)
                : 1;
    }

}
