package service;

import model.Currency;

import java.util.Map;

public class CurrencyService {
    private static final Map<Currency,Map<Currency, Double>> CURRENCY_COEFFICIENT = Map.of(
            Currency.EUR, Map.of(
                    Currency.RUB, 100.0,
                    Currency.USD, 0.75
            ),
            Currency.USD, Map.of(
                    Currency.RUB, 75.0,
                    Currency.EUR, 1.25
            ),
            Currency.RUB, Map.of(
                    Currency.USD, 0.013,
                    Currency.EUR, 0.01
            )
    );

    public double transfer(double money, Currency from, Currency to) {
        return money * CURRENCY_COEFFICIENT.get(from).get(to);
    }
}
