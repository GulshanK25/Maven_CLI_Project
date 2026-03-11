package service;

import model.Currency;
import model.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

// Favourite Conversion Service
public class FavouriteConversion {

    private final List<CurrencyPair> favourites = new ArrayList<>();

    private CurrencyPair pair(Currency from, Currency to) {
        return new CurrencyPair(from, to);
    }

    public void addFavourite(Currency from, Currency to) {
        CurrencyPair favouriteCurrencyPair = pair(from, to);
        if (!favourites.contains(favouriteCurrencyPair)) {
            favourites.add(favouriteCurrencyPair);
        }
    }

    public void removeFavourite(Currency from, Currency to) {
        favourites.remove(pair(from, to));
    }

    public boolean isFavourite(Currency from, Currency to) {
        return favourites.contains(pair(from, to));
    }

    public List<CurrencyPair> getFavourites() {
        return favourites;
    }

    public void clearFavourites() {
        favourites.clear();
    }
}