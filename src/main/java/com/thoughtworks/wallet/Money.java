package com.thoughtworks.wallet;

import com.thoughtworks.exception.InvalidAmountException;

public class Money {
    private final double amount;
    private final Currency currency;

    Money(double amount, Currency currency) throws InvalidAmountException {
        if (amount < 0)
            throw new InvalidAmountException();
        this.amount = amount;
        this.currency = currency;
    }

    public static Money rupee(double amount) throws InvalidAmountException {
        return new Money(amount, Currency.RUPEE);
    }

    public static Money dollar(double amount) throws InvalidAmountException {
        return new Money(amount, Currency.DOLLAR);
    }

    Money add(Money money) throws InvalidAmountException {
        money = money.convertTo(this.currency);
        return new Money(this.amount + money.amount, this.currency);
    }

    double amount() {
        return amount;
    }

    double amount(Currency currency) {
        return this.currency.convertTo(amount, currency);
    }

    Currency currency() {
        return currency;
    }

    Money convertTo(Currency currency) throws InvalidAmountException {
        if(currency == this.currency)
            return this;
        return new Money(amount(currency), currency);
    }

    Money subtract(Money money) throws InvalidAmountException {
        money = money.convertTo(this.currency);
        return new Money(this.amount - money.amount, this.currency);
    }
}
