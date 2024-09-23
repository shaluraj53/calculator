package com.external.calculator.chain.context;

import java.time.LocalDate;

import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.Item;
import com.external.calculator.dto.Order;

import lombok.Data;

@Data
public class ChainContext {

    String userRole;

    String discountType;

    String baseCurrency;

    String foreignCurrency;

    Double conversionRate;

    double discountPercentage;

    Order order;

    Item currentItem;

    LocalDate userCreatedOn;

    public ChainContext(Order order) {
        this.order = order;
    }

}
