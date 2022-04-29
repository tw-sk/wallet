package com.thoughtworks.wallet;

import com.thoughtworks.exception.InsufficientBalanceException;
import com.thoughtworks.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static com.thoughtworks.wallet.Money.dollar;
import static com.thoughtworks.wallet.Money.rupee;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTest {

    @Test
    void shouldCreditMoneyInRupeeInWalletWhenThereIsNoMoneyInWallet() throws InvalidAmountException {
        Wallet wallet = new Wallet();
        Money oneRupee = rupee(1.0);

        wallet.credit(oneRupee);

        assertThat(wallet.getBalance(Currency.RUPEE), is(closeTo(1, 0.001)));
    }

    @Test
    void shouldCreditMoneyInDollarsInWalletWhenThereIsMoneyInRupeeInWallet() throws InvalidAmountException {
        Wallet wallet = new Wallet();
        Money oneRupee = rupee(1.0);
        Money oneDollar = dollar(1.0);

        wallet.credit(oneRupee);
        wallet.credit(oneDollar);

        assertThat(wallet.getBalance(Currency.RUPEE), is(closeTo(75.85, 0.001)));
        assertThat(wallet.getBalance(Currency.DOLLAR), is(closeTo(1.0134, 0.001)));
    }

    @Test
    void shouldDebitMoneyInRupeeFromWalletWhenThereIsEnoughMoneyInRupeeInWallet()
            throws InvalidAmountException, InsufficientBalanceException {
        Wallet wallet = new Wallet();
        Money tenRupee = rupee(10.0);
        Money oneRupee = rupee(1.0);

        wallet.credit(tenRupee);
        wallet.debit(oneRupee);

        assertThat(wallet.getBalance(Currency.RUPEE), is(closeTo(9.0, 0.001)));
    }

    @Test
    void shouldNotDebitMoneyInRupeeFromWalletWhenThereIsNotEnoughMoneyInRupeeInWallet() throws InvalidAmountException {
        Wallet wallet = new Wallet();
        Money tenRupee = rupee(10.0);
        Money oneRupee = rupee(1.0);

        wallet.credit(oneRupee);

        assertThrows(InsufficientBalanceException.class, () -> wallet.debit(tenRupee));
    }

    @Test
    void shouldDebitMoneyInRupeeFromWalletWhenThereIsEnoughMoneyInDollarInWallet()
            throws InvalidAmountException, InsufficientBalanceException {
        Wallet wallet = new Wallet();
        Money tenRupee = rupee(10.0);
        Money oneDollar = dollar(1.0);

        wallet.credit(oneDollar);
        wallet.debit(tenRupee);

        assertThat(wallet.getBalance(Currency.DOLLAR), is(closeTo(0.8664, 0.001)));
        assertThat(wallet.getBalance(Currency.RUPEE), is(closeTo(64.85, 0.001)));
    }

    @Test
    void shouldReturnBalanceWhenCurrencyIsGiven() throws InvalidAmountException {
        Wallet wallet = new Wallet();
        Money fiftyRupee = rupee(50.0);
        Money oneDollar = dollar(1.0);

        wallet.credit(oneDollar);
        wallet.credit(fiftyRupee);

        assertThat(wallet.getBalance(Currency.RUPEE), is(closeTo(124.85, 0.001)));
    }

}
