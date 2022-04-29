package com.thoughtworks.wallet;

import com.thoughtworks.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void shouldCreateMoneyWhenProperAmountAndCurrenyIsPassed() {
        assertDoesNotThrow(()->{
            new Money(1,Currency.RUPEE);
        });
    }

    @Test
    void shouldThrowErrorWhenInValidAmountIsPassed() {
        assertThrows(InvalidAmountException.class,()->{
            new Money(-1,Currency.RUPEE);
        });
    }

    @Test
    void shouldCreateMoneyWithCurrencyRupeeWhenRupeeIsCalled() throws InvalidAmountException {
        Money money = Money.rupee(0);

        assertThat(money.currency(),is(Currency.RUPEE));
    }

    @Test
    void shouldCreateMoneyWithCurrencyDollarWhenDollarIsCalled() throws InvalidAmountException {
        Money money = Money.dollar(0);

        assertThat(money.currency(),is(Currency.DOLLAR));
    }

    @Test
    void shouldAddMoneyWhenBothMoneyIsOfSameCurrency() throws InvalidAmountException {
        Money oneDollar = Money.dollar(1);
        Money twoDollar = Money.dollar(2);

        Money threeDollar = oneDollar.add(twoDollar);

        assertThat(threeDollar.amount(),is(closeTo(3.0,0.001)));
    }

    @Test
    void shouldConvertAddMoneyWhenBothMoneyIsOfDifferentCurrency() throws InvalidAmountException {
        Money oneDollar = Money.dollar(1);
        Money oneRupee = Money.rupee(1);

        Money sum = oneRupee.add(oneDollar);

        assertThat(sum.amount(),is(closeTo(75.85,0.001)));
    }

    @Test
    void shouldSubtractMoneyWhenBothMoneyIsOfSameCurrency() throws InvalidAmountException {
        Money oneDollar = Money.dollar(1);
        Money twoDollar = Money.dollar(2);

        Money balance = twoDollar.subtract(oneDollar);

        assertThat(balance.amount(),is(closeTo(1.0,0.001)));
    }

    @Test
    void shouldConvertSubtractMoneyWhenBothMoneyIsOfDifferentCurrency() throws InvalidAmountException {
        Money oneDollar = Money.dollar(1);
        Money oneRupee = Money.rupee(1);

        Money balance = oneDollar.subtract(oneRupee);

        assertThat(balance.amount(),is(closeTo(0.986,0.001)));
    }

    @Test
    void shouldConvertOneDollarToEquivalentRupees() throws InvalidAmountException {
        Money oneDollar = Money.dollar(1);

        Money rupees = oneDollar.convertTo(Currency.RUPEE);

        assertThat(rupees.amount(),is(closeTo(74.85,0.001)));
    }

    @Test
    void shouldConvertHundredRupeesToEquivalentDollars() throws InvalidAmountException {
        Money hundredRupees = Money.rupee(100);

        Money dollars = hundredRupees.convertTo(Currency.DOLLAR);

        assertThat(dollars.amount(),is(closeTo(100/74.85,0.001)));
    }

    @Test
    void shouldReturnSameMoneyWhenConvertedToSameCurrency() throws InvalidAmountException {
        Money hundredRupees = Money.rupee(100);

        Money converted = hundredRupees.convertTo(Currency.RUPEE);

        assertThat(converted.amount(),is(closeTo(100,0.001)));
    }

}