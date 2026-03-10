package service;

import java.util.ArrayList;
import java.util.List;

public class CurrencySearch {

    public static List<String> search(String query) {

        List<String> currencies = List.of("USD", "EUR", "SEK", "INR", "GBP");
        List<String> result = new ArrayList<>();

        for (String currency : currencies) {
            if (currency.toLowerCase().contains(query.toLowerCase())) {
                result.add(currency);
            }
        }

        return result;
    }
}