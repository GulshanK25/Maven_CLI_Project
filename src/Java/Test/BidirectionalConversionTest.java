package Test;

import model.Currency;
import model.ConversionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Bidirectional Conversion
// Tests that ensures the possibility of converting between two currencies in both direction

@DisplayName("Bidirectional Conversion Tests")
class BidirectionalConversionTest extends BaseTest
{
    // Currency conversion
    @Test
    @DisplayName("Converting USD to EUR")
    void convert_usd_to_eur() {
        ConversionResult result = service.convert(100, Currency.USD, Currency.EUR);
        assertEquals(92.0, result.getConvertedAmount(), 0.01);
    }

    @Test
    @DisplayName("Converting EUR back to USD")
    void convert_eur_to_usd() {
        ConversionResult result = service.convert(92, Currency.EUR, Currency.USD);
        assertEquals(100.0, result.getConvertedAmount(), 0.01);
    }

    // Result formatting
    @Test
    @DisplayName("Result should be formatted to two decimal places")
    void convert_result_to_two_decimal_place() {
        ConversionResult result = service.convert(100, Currency.USD, Currency.SEK);
        assertEquals(1042.0, result.getConvertedAmount(), 0.01);
    }

    @Test
    @DisplayName("Converting same currency should return same amount")
    void convert_same_currency_returns_same_amount() {
        ConversionResult result = service.convert(100, Currency.USD, Currency.USD);
        assertEquals(100.0, result.getConvertedAmount(), 0.01);
    }

    // Edge cases
    @Test
    @DisplayName("Converting zero amount should return zero")
    void convert_zero_amount_returns_zero() {
        ConversionResult result = service.convert(0, Currency.USD, Currency.EUR);
        assertEquals(0.0, result.getConvertedAmount(), 0.01);
    }

    @Test
    @DisplayName("Converting large amount should return correct result")
    void convert_large_amount() {
        ConversionResult result = service.convert(1000000, Currency.USD, Currency.EUR);
        assertEquals(920000.0, result.getConvertedAmount(), 0.01);
    }

}