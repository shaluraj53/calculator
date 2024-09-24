package com.external.calculator.chain.processor.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.chain.processor.ChainProcessor;
import com.external.calculator.dto.Item;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.PriceInfo;
import com.external.calculator.utils.MathUtil;

@Component
public class ItemPriceComputationProcessor implements ChainProcessor {

    @Autowired
    ItemDiscountProcessor itemDiscountProcessor;

    @Override
    public void process(ChainContext chainContext) {
        if (chainContext != null && chainContext.getOrder() != null) {
            Order order = chainContext.getOrder();
            List<Item> items = order.getItems();

            for (Item item : items) {
                Map<String, PriceInfo> priceInfo = item.getPriceInfo();
                if (priceInfo == null) {
                    priceInfo = new HashMap<>();
                    item.setPriceInfo(priceInfo);
                }
                double totalPrice = item.getQuantity() * item.getUnitPrice();
                double convertedTotalPrice = totalPrice * chainContext.getConversionRate();
                PriceInfo basePriceInfo = new PriceInfo();
                basePriceInfo.setTotalPrice(MathUtil.roundToTwoDecimalPoint(totalPrice).doubleValue());
                basePriceInfo.setNetPrice(MathUtil.roundToTwoDecimalPoint(totalPrice).doubleValue());
                priceInfo.put(chainContext.getBaseCurrency(), basePriceInfo);

                PriceInfo foreignPriceInfo = new PriceInfo();
                foreignPriceInfo.setTotalPrice(MathUtil.roundToTwoDecimalPoint(convertedTotalPrice).doubleValue());
                foreignPriceInfo.setNetPrice(MathUtil.roundToTwoDecimalPoint(convertedTotalPrice).doubleValue());
                priceInfo.put(chainContext.getForeignCurrency(), foreignPriceInfo);

                chainContext.setCurrentItem(item);
                itemDiscountProcessor.process(chainContext);

            }
        }
    }

}
