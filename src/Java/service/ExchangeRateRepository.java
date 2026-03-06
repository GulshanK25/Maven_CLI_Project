package service;

import model.Currency;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Stores static exchange rates relative to USD as the base currency.
 * 1 USD = X of the listed currency.
 *
 * Static rates are used so the app works offline and tests are deterministic.
 * In a real application, these would be fetched from a live API.
 */
public class ExchangeRateRepository {
    public double getRate(Currency from, Currency to) { return 0; }
    public Currency[] getAvailableCurrencies() { return new Currency[0]; }
}