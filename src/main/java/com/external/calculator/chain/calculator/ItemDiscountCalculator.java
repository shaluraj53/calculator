package com.external.calculator.chain.calculator;

import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.dto.DiscountInfo;
import com.external.calculator.dto.Item;
import com.external.calculator.dto.PriceInfo;
import com.external.calculator.utils.MathUtil;

@Component
public class ItemDiscountCalculator implements Calculator {

    @Override
    public void compute(ChainContext chainContext) {
        String discountType = chainContext.getDiscountType();
        double discountPercentage = chainContext.getDiscountPercentage();

        Item item = chainContext.getCurrentItem();
        // PriceInfo priceInfo = item.getPriceInfo();
        // DiscountInfo discountInfo = priceInfo.getDiscountInfo();
        // if (discountInfo == null) {
        // discountInfo = new DiscountInfo();
        // }
        // double discountedValue =
        // MathUtil.computeValueFromPercentage(discountPercentage,
        // priceInfo.getTotalPrice())
        // .doubleValue();
        // discountInfo.setAmount(discountedValue);
        // discountInfo.setType(discountType);
        // priceInfo.setDiscountInfo(discountInfo);

        // double netPrice = priceInfo.getTotalPrice() - discountedValue;
        // priceInfo.setNetPrice(netPrice);
    }

}
