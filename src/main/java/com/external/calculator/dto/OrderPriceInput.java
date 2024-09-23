package com.external.calculator.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderPriceInput {

    private String orderId;

    private String userId;

    private String userRole;

    private String baseCurrency;

    private String foreignCurrency;

    private LocalDate userCreatedOn;

    private LocalDate date;

    private List<Item> items;

}
