package service;

public class CurrencyValidator {

    private static final double MAX_AMOUNT = 1_000_000_000.0;

    public boolean isValidAmount(double amount) {

        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            return false;
        }

        if (amount < 0) {
            return false;
        }

        if (amount > MAX_AMOUNT) {
            return false;
        }

        return true;
    }

    public boolean isValidAmountString(String input) {

        if (input == null) return false;

        input = input.trim();

        if (input.isEmpty()) return false;

        try {
            double value = Double.parseDouble(input);
            return isValidAmount(value);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getValidationError(String input) {

        if (input == null || input.trim().isEmpty()) {
            return "Input cannot be empty";
        }

        try {
            double value = Double.parseDouble(input);

            if (value < 0) {
                return "Negative values are not allowed";
            }

            if (value > MAX_AMOUNT) {
                return "Amount too large";
            }

            return "";

        } catch (NumberFormatException e) {
            return "Invalid number format";
        }
    }
}