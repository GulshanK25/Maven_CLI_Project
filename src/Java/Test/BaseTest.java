package Test;

import org.junit.jupiter.api.BeforeEach;
import service.CurrencyConverterService;
import service.ExchangeRateRepository;

// Base Test provides shared setup for all tests
public class BaseTest {
    protected ExchangeRateRepository repository;
    protected CurrencyConverterService service;

    @BeforeEach
    void setUp() {
        repository = new ExchangeRateRepository();
        service = new CurrencyConverterService(repository);
    }

}
