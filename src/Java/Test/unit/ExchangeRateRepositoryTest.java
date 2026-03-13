package Test.unit;

import model.Currency;
import service.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ExchangeRateRepository.
 * Tests that rates exist, are valid, and are mathematically consistent.
 */
@DisplayName("ExchangeRateRepository Unit Tests")
class ExchangeRateRepositoryTest {

    private ExchangeRateRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ExchangeRateRepository();
    }



    @Test
    @DisplayName("USD to USD rate should be exactly 1.0")
    void getRate_sameCurrency_usd_shouldBeOne() {
        assertEquals(1.0, repository.getRate(Currency.USD, Currency.USD), 0.0001);
    }

    @Test
    @DisplayName("EUR to EUR rate should be exactly 1.0")
    void getRate_sameCurrency_eur_shouldBeOne() {
        assertEquals(1.0, repository.getRate(Currency.EUR, Currency.EUR), 0.0001);
    }



    @ParameterizedTest
    @EnumSource(Currency.class)
    @DisplayName("Every currency rate from USD should be greater than 0")
    void getRate_allCurrencies_fromUsd_shouldBePositive(Currency target) {
        double rate = repository.getRate(Currency.USD, target);
        assertTrue(rate > 0, "Rate for " + target + " should be positive");
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    @DisplayName("Every currency rate to USD should be greater than 0")
    void getRate_allCurrencies_toUsd_shouldBePositive(Currency source) {
        double rate = repository.getRate(source, Currency.USD);
        assertTrue(rate > 0, "Rate from " + source + " to USD should be positive");
    }



    @Test
    @DisplayName("Rate from A to B multiplied by rate from B to A should equal 1")
    void getRate_inverse_usdEur_shouldBeConsistent() {
        double usdToEur = repository.getRate(Currency.USD, Currency.EUR);
        double eurToUsd = repository.getRate(Currency.EUR, Currency.USD);
        assertEquals(1.0, usdToEur * eurToUsd, 0.0001);
    }

    @Test
    @DisplayName("Rate from USD to GBP inverse should be consistent")
    void getRate_inverse_usdGbp_shouldBeConsistent() {
        double usdToGbp = repository.getRate(Currency.USD, Currency.GBP);
        double gbpToUsd = repository.getRate(Currency.GBP, Currency.USD);
        assertEquals(1.0, usdToGbp * gbpToUsd, 0.0001);
    }



    @Test
    @DisplayName("1 USD should buy more than 1 SEK (dollar stronger than krona)")
    void getRate_usdToSek_dollarShouldBuyMoreThanOne() {
        double rate = repository.getRate(Currency.USD, Currency.SEK);
        assertTrue(rate > 1.0, "1 USD should buy more than 1 SEK");
    }

    @Test
    @DisplayName("1 USD should buy less than 1 GBP (pound stronger than dollar)")
    void getRate_usdToGbp_dollarShouldBuyLessThanOne() {
        double rate = repository.getRate(Currency.USD, Currency.GBP);
        assertTrue(rate < 1.0, "1 USD should buy less than 1 GBP");
    }

    @Test
    @DisplayName("1 USD should buy more than 100 JPY")
    void getRate_usdToJpy_shouldBeMoreThan100() {
        double rate = repository.getRate(Currency.USD, Currency.JPY);
        assertTrue(rate > 100.0, "1 USD should buy more than 100 JPY");
    }


    @Test
    @DisplayName("Null from-currency should throw IllegalArgumentException")
    void getRate_nullFrom_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> repository.getRate(null, Currency.EUR));
    }

    @Test
    @DisplayName("Null to-currency should throw IllegalArgumentException")
    void getRate_nullTo_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> repository.getRate(Currency.USD, null));
    }

    // --- Available currencies ---

    @Test
    @DisplayName("getAvailableCurrencies should return all defined currencies")
    void getAvailableCurrencies_shouldReturnAll() {
        Currency[] available = repository.getAvailableCurrencies();
        assertEquals(Currency.values().length, available.length);
    }
}
