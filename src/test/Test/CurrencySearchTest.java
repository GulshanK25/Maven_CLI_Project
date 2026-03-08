import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Disabled;

@Disabled("CurrencySearch not implemented yet")
public class CurrencySearchTest {

    @Test
    public void testSearch_returnsMatchingCurrency() {
        List<String> results = CurrencySearch.search("US");
        assertTrue(results.contains("USD"),
                "Search for 'US' should return USD");
    }

    @Test
    public void testSearch_caseInsensitive() {
        List<String> results = CurrencySearch.search("us");
        assertTrue(results.contains("USD"),
                "Search should work regardless of case");
    }

    @Test
    public void testSearch_noMatch_returnsEmpty() {
        List<String> results = CurrencySearch.search("ZZZ");
        assertTrue(results.isEmpty(),
                "No match should return empty list");
    }
}