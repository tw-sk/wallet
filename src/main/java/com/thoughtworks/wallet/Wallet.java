package com.thoughtworks.wallet;

import com.thoughtworks.exception.InsufficientBalanceException;
import com.thoughtworks.exception.InvalidAmountException;

public class Wallet {
    private Money balance;

    Wallet() {
        try {
            balance = Money.rupee(0);
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        }
    }

    public void credit(Money money) throws InvalidAmountException {
        balance = balance.add(money);
    }

    public double getBalance(Currency currency) {
        return balance.amount(currency);
    }

    public void debit(Money money) throws InsufficientBalanceException, InvalidAmountException {
        Currency currency = money.currency();
        double currentBalance = getBalance(currency);
        if (money.amount() > currentBalance) {
            throw new InsufficientBalanceException();
        }
        balance = balance.subtract(money);
    }
}
