package com.thoughtworks.wallet;

public enum Currency {
    RUPEE,
    DOLLAR;

    private static final double dollarToRupeeConversionFactor = 74.85;

    public double convertTo(double amount, Currency currency) {
        if (this == currency) {
            return amount;
        }
        if (currency == DOLLAR) {
            return amount / Currency.dollarToRupeeConversionFactor;
        }
        return amount * Currency.dollarToRupeeConversionFactor;
    }
}
