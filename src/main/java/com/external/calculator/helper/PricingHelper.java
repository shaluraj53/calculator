package com.external.calculator.helper;

import org.springframework.stereotype.Component;

import com.external.calculator.dto.Order;
import com.external.calculator.dto.OrderPriceInput;
import com.external.calculator.dto.PriceInfo;
import com.external.calculator.dto.User;

@Component
public class PricingHelper {

    public Order convertPriceInputToOrder(OrderPriceInput priceInput) {
        Order order = null;
        if (priceInput != null) {
            order = new Order();
            order.setOrderId(priceInput.getOrderId());
            order.setItems(priceInput.getItems());
            order.setOrderDate(priceInput.getDate());

            User user = new User();
            user.setId(priceInput.getUserId());
            user.setRole(priceInput.getUserRole());
            user.setCreatedOn(priceInput.getUserCreatedOn());

            order.setUserInfo(user);
            order.setPriceInfo(new PriceInfo());
        }

        return order;
    }

}
