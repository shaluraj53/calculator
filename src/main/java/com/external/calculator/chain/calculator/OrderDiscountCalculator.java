package com.external.calculator.chain.calculator;

import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.dto.DiscountInfo;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.PriceInfo;
import com.external.calculator.utils.CalculatorConstants;

@Component
public class OrderDiscountCalculator implements Calculator {

    private double amountOff = 5.0; // Amountoff for every $100 in netprice

    @Override
    public void compute(ChainContext chainContext) {

        Order order = chainContext.getOrder();
        PriceInfo priceInfo = order.getPriceInfo();

        if (priceInfo != null) {
            double netPrice = priceInfo.getNetPrice();
            int hundreds = (int) (netPrice / 100);
            double discount = hundreds * amountOff;

            netPrice = netPrice - discount;
            priceInfo.setNetPrice(netPrice);

            DiscountInfo discountInfo = new DiscountInfo();
            discountInfo.setAmount(discount);
            discountInfo.setType(CalculatorConstants.ORDER_DISCOUNT);
            priceInfo.setDiscountInfo(discountInfo);

        }

    }

}
