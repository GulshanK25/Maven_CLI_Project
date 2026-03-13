package Test.unit;

import model.Currency;
import model.ConversionResult;
import service.CurrencyConverterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import service.ExchangeRateRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CurrencyConverterService Unit Tests")
class CurrencyConverterServiceTest {

    private ExchangeRateRepository mockRepo;
    private CurrencyConverterService service;

    @BeforeEach
    void setUp() {
        mockRepo = mock(ExchangeRateRepository.class);  // allows to test CurrencyConverterService in isolation
        service = new CurrencyConverterService(mockRepo);
    }

    @Test
    @DisplayName("Rate repository being null should throw IllegalArgumentException")
    void rate_repository_constructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new CurrencyConverterService(null));
    }

    @Test
    @DisplayName("Normal conversion should return correct amount")
    void convert_amount_and_get_conversion_amount() {
        when(mockRepo.getRate(Currency.USD, Currency.EUR)).thenReturn(0.92);
        ConversionResult result = service.convert(100, Currency.USD, Currency.EUR);
        assertEquals(92.0, result.getConvertedAmount(), 0.01);
    }

    @Test
    @DisplayName("Zero amount should return zero")
    void convert_zero_returns_zero() {
        when(mockRepo.getRate(Currency.USD, Currency.EUR)).thenReturn(0.92);
        ConversionResult result = service.convert(0, Currency.USD, Currency.EUR);
        assertEquals(0.0, result.getConvertedAmount(), 0.01);
    }

    @Test
    @DisplayName("Negative amount should throw IllegalArgumentException")
    void convert_negative_amount_throws_error() {
        assertThrows(IllegalArgumentException.class,
                () -> service.convert(-50, Currency.USD, Currency.EUR));
    }

    @Test
    @DisplayName("Null 'from' Currency should throw IllegalArgumentException")
    void convert_null_from_currency_value_throws_error() {
        assertThrows(IllegalArgumentException.class,
                () -> service.convert(100, null, Currency.EUR));
    }

    @Test
    @DisplayName("Null 'to' Currency should throw IllegalArgumentException")
    void convert_null_to_currency_value_throws_error() {
        assertThrows(IllegalArgumentException.class,
                () -> service.convert(100, Currency.USD, null));
    }

    @Test
    @DisplayName("Formatted rate value for correct inputted value")
    void get_formatted_rate() {
        when(mockRepo.getRate(Currency.USD, Currency.EUR)).thenReturn(0.92);
        String result = service.getFormattedRate(Currency.USD, Currency.EUR);
        assertEquals("1 USD = 0,9200 EUR", result);
    }

    @Test
    @DisplayName("getFormattedRate null 'from' value should throw IllegalArgumentException")
    void get_formatted_rate_null_from_value_throws_error() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getFormattedRate(null, Currency.EUR));
    }

    @Test
    @DisplayName("getFormattedRate null 'to' value should throw IllegalArgumentException")
    void get_formatted_rate_null_to_value_throws_error() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getFormattedRate(Currency.USD, null));
    }

}
