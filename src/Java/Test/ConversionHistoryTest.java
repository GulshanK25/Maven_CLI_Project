package Test;

import model.ConversionResult;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ConversionHistory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ConversionHistory.
 * Tests adding, retrieving, and managing conversion history entries.
 */
@DisplayName("ConversionHistory Unit Tests")
class ConversionHistoryTest {

    private ConversionHistory history;

    @BeforeEach
    void setUp() {
        history = new ConversionHistory();
    }

    // --- Constructor ---

    @Test
    @DisplayName("New history should be empty")
    void newHistory_shouldBeEmpty() {
        assertTrue(history.isEmpty());
    }

    @Test
    @DisplayName("New history should have size 0")
    void newHistory_sizeShouldBeZero() {
        assertEquals(0, history.size());
    }

    @Test
    @DisplayName("Default max size should be 5")
    void newHistory_defaultMaxSize_shouldBeFive() {
        assertEquals(5, history.getMaxSize());
    }

    @Test
    @DisplayName("Custom max size should be stored correctly")
    void newHistory_customMaxSize_shouldBeStored() {
        ConversionHistory custom = new ConversionHistory(10);
        assertEquals(10, custom.getMaxSize());
    }

    @Test
    @DisplayName("Max size less than 1 should throw")
    void newHistory_invalidMaxSize_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConversionHistory(0));
    }

    // --- add ---

    @Test
    @DisplayName("Adding a result should increase size by 1")
    void add_shouldIncreaseSize() {
        history.add(makeResult(100, Currency.USD, Currency.EUR, 92));
        assertEquals(1, history.size());
    }

    @Test
    @DisplayName("Adding null result should throw")
    void add_null_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> history.add(null));
    }

    @Test
    @DisplayName("Newest entry should be at index 0")
    void add_newestShouldBeFirst() {
        history.add(makeResult(100, Currency.USD, Currency.EUR, 92));
        history.add(makeResult(200, Currency.USD, Currency.GBP, 158));

        assertEquals(200.0, history.getAll().get(0).getInputAmount(), 0.001);
    }

    @Test
    @DisplayName("Adding more than max size should remove oldest entry")
    void add_exceedingMaxSize_shouldRemoveOldest() {
        ConversionHistory small = new ConversionHistory(3);
        small.add(makeResult(100, Currency.USD, Currency.EUR, 92));
        small.add(makeResult(200, Currency.USD, Currency.GBP, 158));
        small.add(makeResult(300, Currency.USD, Currency.SEK, 3126));
        small.add(makeResult(400, Currency.USD, Currency.JPY, 59800));

        assertEquals(3, small.size());
        assertEquals(400.0, small.getAll().get(0).getInputAmount(), 0.001);
    }

    // --- getLatest ---

    @Test
    @DisplayName("getLatest on empty history should return null")
    void getLatest_emptyHistory_shouldReturnNull() {
        assertNull(history.getLatest());
    }

    @Test
    @DisplayName("getLatest should return the most recent entry")
    void getLatest_shouldReturnMostRecent() {
        history.add(makeResult(100, Currency.USD, Currency.EUR, 92));
        history.add(makeResult(500, Currency.USD, Currency.JPY, 74750));

        assertEquals(500.0, history.getLatest().getInputAmount(), 0.001);
    }

    // --- clear ---

    @Test
    @DisplayName("Clear should empty the history")
    void clear_shouldEmptyHistory() {
        history.add(makeResult(100, Currency.USD, Currency.EUR, 92));
        history.add(makeResult(200, Currency.USD, Currency.GBP, 158));
        history.clear();

        assertTrue(history.isEmpty());
        assertEquals(0, history.size());
    }

    // --- getAll ---

    @Test
    @DisplayName("getAll should return unmodifiable list")
    void getAll_shouldReturnUnmodifiableList() {
        history.add(makeResult(100, Currency.USD, Currency.EUR, 92));
        assertThrows(UnsupportedOperationException.class,
                () -> history.getAll().add(makeResult(200, Currency.USD, Currency.GBP, 158)));
    }

    // --- Helper ---

    private ConversionResult makeResult(double amount, Currency from,
                                        Currency to, double converted) {
        double rate = converted / amount;
        return new ConversionResult(amount, from, to, converted, rate);
    }
}
