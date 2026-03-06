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

    private static final Map<Currency, Double> RATES_FROM_USD;

    static {
        Map<Currency, Double> rates = new EnumMap<>(Currency.class);
        rates.put(Currency.USD, 1.0);
        rates.put(Currency.EUR, 0.92);
        rates.put(Currency.GBP, 0.79);
        rates.put(Currency.SEK, 10.42);
        rates.put(Currency.JPY, 149.50);
        rates.put(Currency.CAD, 1.36);
        rates.put(Currency.AUD, 1.53);
        rates.put(Currency.CHF, 0.90);
        rates.put(Currency.NOK, 10.55);
        rates.put(Currency.DKK, 6.88);
        RATES_FROM_USD = Collections.unmodifiableMap(rates);
    }

    /**
     * Returns the exchange rate from one currency to another.
     * Uses USD as the intermediate base.
     */
    public double getRate(Currency from, Currency to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Currencies cannot be null.");
        }
        double fromRate = RATES_FROM_USD.get(from);
        double toRate   = RATES_FROM_USD.get(to);
        return toRate / fromRate;
    }

    public Currency[] getAvailableCurrencies() {
        return Currency.values();
    }
}
