package service;

import model.ConversionResult;
import model.Currency;


public class CurrencyConverterService {

    private final ExchangeRateRepository rateRepository;

    public CurrencyConverterService(ExchangeRateRepository rateRepository) {
        if (rateRepository == null) {
            throw new IllegalArgumentException("Rate repository cannot be null.");
        }
        this.rateRepository = rateRepository;
    }

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount       must be >= 0
     * @param fromCurrency source currency (not null)
     * @param toCurrency   target currency (not null)
     * @return ConversionResult with all conversion details
     */


    public ConversionResult convert(double amount, Currency fromCurrency, Currency toCurrency) {
        // Throws error stating that the function is not yet implemented
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns a human-readable rate string.
     * Example: "1 USD = 0.9200 EUR"
     */
    public String getFormattedRate(Currency from, Currency to) {
        // Throws error stating that the function is not yet implemented
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
