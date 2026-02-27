package model;

/**
 * Supported currencies.
 * To add a new currency: add it here + add its rate in ExchangeRateRepository.
 */
public enum Currency {
    USD("US Dollar",         "$"),
    EUR("Euro",              "€"),
    GBP("British Pound",    "£"),
    SEK("Swedish Krona",    "kr"),
    JPY("Japanese Yen",     "¥"),
    CAD("Canadian Dollar",  "CA$"),
    AUD("Australian Dollar","A$"),
    CHF("Swiss Franc",      "Fr"),
    NOK("Norwegian Krone",  "kr"),
    DKK("Danish Krone",     "kr");

    private final String fullName;
    private final String symbol;

    Currency(String fullName, String symbol) {
        this.fullName = fullName;
        this.symbol   = symbol;
    }

    public String getFullName() { return fullName; }
    public String getSymbol()   { return symbol; }

    @Override
    public String toString() {
        return name() + "  —  " + fullName;
    }
}
