package com.external.calculator.chain.processor.item;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.calculator.ItemDiscountCalculator;
import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.chain.processor.ChainProcessor;
import com.external.calculator.dto.Item;
import com.external.calculator.utils.CalculatorConstants;
import com.external.calculator.enums.Role;

@Component
public class ItemDiscountProcessor implements ChainProcessor {

    @Autowired
    ItemDiscountCalculator itemDiscountCalculator;

    @Override
    public void process(ChainContext chainContext) {
        Item item = chainContext.getCurrentItem();

        if (StringUtils.equalsIgnoreCase(CalculatorConstants.GROCERY, item.getCategory())) {
            return; // No discounts for grocery items
        }

        if (StringUtils.equalsIgnoreCase(Role.EMPLOYEE.name(), chainContext.getUserRole())) {
            // 30 % discount
            calculateDiscount(chainContext, 30, CalculatorConstants.EMPLOYEE_DISCOUNT);
        } else if (StringUtils.equalsIgnoreCase(Role.AFFILIATE.name(), chainContext.getUserRole())) {
            // 10 % discount
            calculateDiscount(chainContext, 10, CalculatorConstants.AFFILIATE_DISCOUNT);
        } else if (StringUtils.equalsIgnoreCase(Role.CUSTOMER.name(), chainContext.getUserRole()) &&
                checkIfOldCustomer(chainContext.getUserCreatedOn())) {
            // 5 % discount
            calculateDiscount(chainContext, 5, CalculatorConstants.CUSTOMER_DISCOUNT);
        }
    }

    private void calculateDiscount(ChainContext chainContext, double discountPercentage, String discountType) {
        chainContext.setDiscountPercentage(discountPercentage);
        chainContext.setDiscountType(discountType);
        itemDiscountCalculator.compute(chainContext);
    }

    private boolean checkIfOldCustomer(LocalDate userCreatedOn) {
        boolean moreThanTwoYearOldCustomer = false;
        LocalDate today = LocalDate.now();
        moreThanTwoYearOldCustomer = userCreatedOn.isBefore(today.minusYears(2));
        return moreThanTwoYearOldCustomer;
    }

}
