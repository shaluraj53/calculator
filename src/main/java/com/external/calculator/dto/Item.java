package com.external.calculator.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Item {

    private String id;

    private String name;

    private String category;

    private int quantity;

    private double unitPrice;

    private Map<String, PriceInfo> priceInfo;

}
