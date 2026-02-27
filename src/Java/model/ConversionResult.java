package model;


/**
 * Immutable result of a single currency conversion operation.
 */
public class ConversionResult {

    private final double   inputAmount;
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final double   convertedAmount;
    private final double   rateUsed;

    public ConversionResult(double inputAmount, Currency fromCurrency,
                            Currency toCurrency, double convertedAmount,
                            double rateUsed) {
        this.inputAmount     = inputAmount;
        this.fromCurrency    = fromCurrency;
        this.toCurrency      = toCurrency;
        this.convertedAmount = convertedAmount;
        this.rateUsed        = rateUsed;
    }

    public double   getInputAmount()     { return inputAmount; }
    public Currency getFromCurrency()    { return fromCurrency; }
    public Currency getToCurrency()      { return toCurrency; }
    public double   getConvertedAmount() { return convertedAmount; }
    public double   getRateUsed()        { return rateUsed; }

    @Override
    public String toString() {
        return String.format("%.2f %s = %.2f %s (rate: %.4f)",
                inputAmount, fromCurrency.name(),
                convertedAmount, toCurrency.name(), rateUsed);
    }
}
