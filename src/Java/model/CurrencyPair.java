package model;

import java.util.Objects;

public class CurrencyPair {

    private final Currency from;
    private final Currency to;

    public CurrencyPair(Currency from, Currency to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Currencies cannot be null.");
        }
        this.from = from;
        this.to = to;
    }

    public Currency getFrom() { return from; }
    public Currency getTo() { return to; }

    // Two pairs are equal if they have the same from and to currency
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyPair other)) return false;
        return from == other.from && to == other.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return from.name() + " to " + to.name();
    }
}