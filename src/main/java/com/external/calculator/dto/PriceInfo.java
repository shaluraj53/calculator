package com.external.calculator.dto;

import lombok.Data;

@Data
public class PriceInfo {

    private double totalPrice;

    private double netPrice; // Price to be paid by customer after discount

    private DiscountInfo discountInfo;

}
