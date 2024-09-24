package com.external.calculator.chain.calculator;

import java.util.Map;

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
        Map<String, PriceInfo> priceInfo = item.getPriceInfo();
        PriceInfo basePriceInfo = priceInfo.get(chainContext.getBaseCurrency());
        PriceInfo foreignPriceInfo = priceInfo.get(chainContext.getForeignCurrency());
        computeItemDiscount(basePriceInfo, discountType, discountPercentage);
        computeItemDiscount(foreignPriceInfo, discountType, discountPercentage);

    }

    private void computeItemDiscount(PriceInfo priceInfo, String discountType, double discountPercentage) {
        DiscountInfo discountInfo = priceInfo.getDiscountInfo();
        if (discountInfo == null) {
            discountInfo = new DiscountInfo();
        }
        double discountedValue = MathUtil.computeValueFromPercentage(discountPercentage,
                priceInfo.getTotalPrice())
                .doubleValue();
        discountInfo.setAmount(MathUtil.roundToTwoDecimalPoint(discountedValue).doubleValue());
        discountInfo.setType(discountType);
        priceInfo.setDiscountInfo(discountInfo);

        double netPrice = priceInfo.getTotalPrice() - discountedValue;
        priceInfo.setNetPrice(MathUtil.roundToTwoDecimalPoint(netPrice).doubleValue());
    }

}
