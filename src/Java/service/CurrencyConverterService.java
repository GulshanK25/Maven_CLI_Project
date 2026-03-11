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
        throw new UnsupportedOperationException("Not Yet Implemented");
        /*
        if (amount < 0)          throw new IllegalArgumentException("Amount cannot be negative.");
        if (fromCurrency == null) throw new IllegalArgumentException("Source currency cannot be null.");
        if (toCurrency == null)   throw new IllegalArgumentException("Target currency cannot be null.");

        double rate            = rateRepository.getRate(fromCurrency, toCurrency);
        double convertedAmount = Math.round(amount * rate * 100.0) / 100.0;

        return new ConversionResult(amount, fromCurrency, toCurrency, convertedAmount, rate);

         */
    }

    /**
     * Returns a human-readable rate string.
     * Example: "1 USD = 0.9200 EUR"
     */
    public String getFormattedRate(Currency from, Currency to) {
        throw new UnsupportedOperationException("Not Yet Implemented");
        /*
        if (from == null || to == null) {
            throw new IllegalArgumentException("Currencies cannot be null.");
        }
        double rate = rateRepository.getRate(from, to);
        return String.format("1 %s = %.4f %s", from.name(), rate, to.name());
        
         */
    }
}
