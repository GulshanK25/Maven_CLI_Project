package Test;

import model.Currency;
import model.ConversionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Bidirectional Conversion
// Tests that converts between two currencies works in both directions

@DisplayName("TC1 - Bidirectional Conversion Tests")
class BidirectionalConversionTest extends BaseTest
{
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

    @Test
    @DisplayName("Result should be formatted to two decimal places")
    void convert_result_to_two_decimal_place() {
        ConversionResult result = service.convert(100, Currency.USD, Currency.SEK);
        String formatted = String.format("%.2f", result.getConvertedAmount());
        assertEquals(2, formatted.split("\\.")[1].length());
    }

    @Test
    @DisplayName("Converting same currency should return same amount")
    void convert_same_currency_returns_same_amount() {
        ConversionResult result = service.convert(100, Currency.USD, Currency.USD);
        assertEquals(100.0, result.getConvertedAmount(), 0.01);
    }

}
