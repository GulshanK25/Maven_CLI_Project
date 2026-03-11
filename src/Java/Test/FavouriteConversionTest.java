package Test;

import model.Currency;
import model.CurrencyPair;
import service.FavouriteConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FavouriteConversion Tests")
class FavouriteConversionTest {

    private FavouriteConversion favourites;

    @BeforeEach
    void setUp() {
        favourites = new FavouriteConversion();
    }

    @Test
    @DisplayName("Adding a pair should increase list size")
    void addFavourite_increases_list_size() {
        favourites.addFavourite(Currency.USD, Currency.EUR);
        assertEquals(1, favourites.getFavourites().size());
    }

    @Test
    @DisplayName("Adding duplicate pair should not add it twice")
    void addFavourite_duplicate_not_added_twice() {
        favourites.addFavourite(Currency.USD, Currency.EUR);
        favourites.addFavourite(Currency.USD, Currency.EUR);
        assertEquals(1, favourites.getFavourites().size());
    }

    @Test
    @DisplayName("Removing a pair should decrease list size")
    void removFavourite_decreases_list_size() {
        favourites.addFavourite(Currency.USD, Currency.EUR);
        favourites.removeFavourite(Currency.USD, Currency.EUR);
        assertEquals(0, favourites.getFavourites().size());
    }

    @Test
    @DisplayName("Removing a pair that does not exist should not throw error")
    void removing_nonexistent_favourite_does_not_throw() {
        assertDoesNotThrow(() -> favourites.removeFavourite(Currency.USD, Currency.EUR));
    }

    @Test
    @DisplayName("No favourites added should return empty list")
    void getFavourites_returns_empty_list() {
        assertTrue(favourites.getFavourites().isEmpty());
    }

    @Test
    @DisplayName("Return correct list after adding pairs")
    void getFavourites_returns_correct_list() {
        favourites.addFavourite(Currency.USD, Currency.EUR);
        favourites.addFavourite(Currency.GBP, Currency.SEK);
        assertEquals(2, favourites.getFavourites().size());
        assertTrue(favourites.getFavourites().contains(new CurrencyPair(Currency.USD, Currency.EUR)));
    }

    @Test
    @DisplayName("isFavourite should return true for added pair")
    void isFavourite_returns_true_for_added_pair() {
        favourites.addFavourite(Currency.USD, Currency.EUR);
        assertTrue(favourites.isFavourite(Currency.USD, Currency.EUR));
    }

    @Test
    @DisplayName("isFavourite should return false for pair not added")
    void isFavourite_returns_false_for_missing_pair() {
        assertFalse(favourites.isFavourite(Currency.USD, Currency.EUR));
    }

    @Test
    @DisplayName("clearFavourites should empty the list")
    void clearFavourites_empties_list() {
        favourites.addFavourite(Currency.USD, Currency.EUR);
        favourites.addFavourite(Currency.GBP, Currency.SEK);
        favourites.clearFavourites();
        assertTrue(favourites.getFavourites().isEmpty());
    }
}