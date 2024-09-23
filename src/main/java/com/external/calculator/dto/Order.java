package com.external.calculator.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Order {

    private String orderId;

    private LocalDate orderDate;

    private List<Item> items;

    private PriceInfo priceInfo;

    @JsonIgnore
    private User userInfo;

}
