package com.external.calculator.chain.processor.order;

import java.util.List;
import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.chain.processor.ChainProcessor;
import com.external.calculator.dto.Item;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.PriceInfo;
import com.external.calculator.utils.MathUtil;

@Component
public class OrderPriceComputationProcessor implements ChainProcessor {

    @Override
    public void process(ChainContext chainContext) {
        if (chainContext != null && chainContext.getOrder() != null) {
            Order order = chainContext.getOrder();
            List<Item> items = order.getItems();

            Map<String, PriceInfo> orderPriceInfo = order.getPriceInfo();
            computeOrderPrice(chainContext.getBaseCurrency(), orderPriceInfo, items);
            computeOrderPrice(chainContext.getForeignCurrency(), orderPriceInfo, items);

        }
    }

    private void computeOrderPrice(String currency, Map<String, PriceInfo> orderPriceInfo, List<Item> items) {
        if (orderPriceInfo != null && !items.isEmpty() && orderPriceInfo.get(currency) == null) {
            PriceInfo priceInfo = new PriceInfo();
            double totalPrice = items.stream().filter(i -> i.getPriceInfo() != null
                    && i.getPriceInfo().get(currency) != null)
                    .map(item -> item.getPriceInfo().get(currency).getTotalPrice()).reduce(0.0,
                            (x, y) -> x + y);
            double netPrice = items.stream().filter(i -> i.getPriceInfo() != null
                    && i.getPriceInfo().get(currency) != null)
                    .map(item -> item.getPriceInfo().get(currency).getNetPrice()).reduce(0.0,
                            (x, y) -> x + y);
            priceInfo.setTotalPrice(MathUtil.roundToTwoDecimalPoint(totalPrice).doubleValue());
            priceInfo.setNetPrice(MathUtil.roundToTwoDecimalPoint(netPrice).doubleValue());
            orderPriceInfo.put(currency, priceInfo);
        }

    }

}