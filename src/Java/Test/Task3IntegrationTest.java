package Test;

import model.ConversionResult;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ConversionHistory;
import service.CurrencyConverterService;
import service.CurrencyValidator;
import service.ExchangeRateRepository;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Task 3.
 *
 * Tests that CurrencyValidator, CurrencyConverterService,
 * and ConversionHistory all work together correctly
 * in realistic end-to-end scenarios.
 */
@DisplayName("Task 3 - Full System Integration Tests")
class Task3IntegrationTest {

    private CurrencyConverterService service;
    private CurrencyValidator validator;
    private ConversionHistory history;

    @BeforeEach
    void setUp() {
        service   = new CurrencyConverterService(new ExchangeRateRepository());
        validator = new CurrencyValidator();
        history   = new ConversionHistory();
    }



    @Test
    @DisplayName("Valid input should pass validation and convert successfully")
    void validInput_shouldPassValidationAndConvert() {
        String input = "100";
        assertTrue(validator.isValidAmountString(input));

        double amount = Double.parseDouble(input);
        ConversionResult result = service.convert(amount, Currency.USD, Currency.EUR);

        assertNotNull(result);
        assertTrue(result.getConvertedAmount() > 0);
    }

    @Test
    @DisplayName("Invalid input should fail validation and not reach service")
    void invalidInput_shouldFailValidationBeforeService() {
        String input = "abc";
        assertFalse(validator.isValidAmountString(input));
        // Service never called — no exception from service
    }

    @Test
    @DisplayName("Negative input should fail validation")
    void negativeInput_shouldFailValidation() {
        String input = "-50";
        assertFalse(validator.isValidAmountString(input));
        assertFalse(validator.getValidationError(input).isEmpty());
    }

    // --- Service + History ---

    @Test
    @DisplayName("Successful conversion should be stored in history")
    void conversion_shouldBeStoredInHistory() {
        ConversionResult result = service.convert(100, Currency.USD, Currency.EUR);
        history.add(result);

        assertEquals(1, history.size());
        assertEquals(result, history.getLatest());
    }

    @Test
    @DisplayName("Multiple conversions should all appear in history")
    void multipleConversions_shouldAllAppearInHistory() {
        history.add(service.convert(100, Currency.USD, Currency.EUR));
        history.add(service.convert(200, Currency.USD, Currency.GBP));
        history.add(service.convert(300, Currency.USD, Currency.SEK));

        assertEquals(3, history.size());
    }

    @Test
    @DisplayName("History should keep only last 5 conversions")
    void history_shouldKeepOnlyLastFive() {
        for (int i = 1; i <= 7; i++) {
            history.add(service.convert(i * 100, Currency.USD, Currency.EUR));
        }
        assertEquals(5, history.size());
    }

    @Test
    @DisplayName("Latest history entry should match last conversion")
    void latestHistory_shouldMatchLastConversion() {
        history.add(service.convert(100, Currency.USD, Currency.EUR));
        ConversionResult last = service.convert(999, Currency.USD, Currency.JPY);
        history.add(last);

        assertEquals(999.0, history.getLatest().getInputAmount(), 0.001);
        assertEquals(Currency.JPY, history.getLatest().getToCurrency());
    }



    @Test
    @DisplayName("Full workflow: validate, convert, store in history")
    void fullWorkflow_validateConvertStore() {
        String userInput = "250";


        assertTrue(validator.isValidAmountString(userInput));


        double amount = Double.parseDouble(userInput);
        ConversionResult result = service.convert(amount, Currency.USD, Currency.SEK);


        history.add(result);


        assertAll(
            () -> assertEquals(1, history.size()),
            () -> assertEquals(250.0, history.getLatest().getInputAmount(), 0.001),
            () -> assertEquals(Currency.USD, history.getLatest().getFromCurrency()),
            () -> assertEquals(Currency.SEK, history.getLatest().getToCurrency()),
            () -> assertTrue(history.getLatest().getConvertedAmount() > 0)
        );
    }

    @Test
    @DisplayName("Clearing history should allow fresh start")
    void clearHistory_shouldAllowFreshStart() {
        history.add(service.convert(100, Currency.USD, Currency.EUR));
        history.add(service.convert(200, Currency.USD, Currency.GBP));
        history.clear();

        assertTrue(history.isEmpty());


        history.add(service.convert(300, Currency.USD, Currency.JPY));
        assertEquals(1, history.size());
    }
}
