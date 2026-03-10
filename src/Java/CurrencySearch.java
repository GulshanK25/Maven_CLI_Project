import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencySearch {

    private static final List<String> CURRENCIES = Arrays.asList(
            "USD", "EUR", "GBP", "SEK", "JPY",
            "INR", "AUD", "CAD", "CHF", "CNY"
    );

    public static List<String> search(String query) {
        if (query == null || query.isEmpty()) {
            return CURRENCIES;
        }
        return CURRENCIES.stream()
                .filter(c -> c.startsWith(query.toUpperCase()))
                .collect(Collectors.toList());
    }
}