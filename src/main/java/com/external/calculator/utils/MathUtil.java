package com.external.calculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface MathUtil {

    public static BigDecimal toBigDecimal(final Number n) {
        return (n instanceof BigDecimal ? (BigDecimal) n : new BigDecimal(n.toString()));
    }

    public static BigDecimal computeValueFromPercentage(final Number percentage, final Number value) {
        final BigDecimal percentageRate = toBigDecimal(percentage);
        final BigDecimal originalValue = toBigDecimal(value);
        return originalValue.multiply(percentageRate).divide(new BigDecimal(100));
    }

    public static BigDecimal roundToTwoDecimalPoint(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }

}
