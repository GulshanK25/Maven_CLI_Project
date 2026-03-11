package service;

import model.ConversionResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores and manages a history of currency conversions.
 * Keeps the last N conversions in memory.
 * This class is used by the GUI to show recent conversions.
 */
public class ConversionHistory {

    private static final int DEFAULT_MAX_SIZE = 5;

    private final List<ConversionResult> history;
    private final int maxSize;

    /**
     * Creates a history with the default max size of 5.
     */
    public ConversionHistory() {
        this(DEFAULT_MAX_SIZE);
    }

    /**
     * Creates a history with a custom max size.
     * @param maxSize maximum number of conversions to store
     * @throws IllegalArgumentException if maxSize is less than 1
     */
    public ConversionHistory(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Max size must be at least 1.");
        }
        this.maxSize  = maxSize;
        this.history  = new ArrayList<>();
    }

    /**
     * Adds a conversion result to the history.
     * If history is full, the oldest entry is removed.
     * @param result the conversion result to add (not null)
     * @throws IllegalArgumentException if result is null
     */
    public void add(ConversionResult result) {
        if (result == null) {
            throw new IllegalArgumentException("Conversion result cannot be null.");
        }
        if (history.size() >= maxSize) {
            history.remove(history.size() - 1);
        }
        history.add(0, result); // newest first
    }

    /**
     * Returns all history entries, newest first.
     * @return unmodifiable list of conversion results
     */
    public List<ConversionResult> getAll() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns the most recent conversion result.
     * @return most recent result or null if history is empty
     */
    public ConversionResult getLatest() {
        if (history.isEmpty()) return null;
        return history.get(0);
    }

    /**
     * Returns the number of entries in the history.
     */
    public int size() {
        return history.size();
    }

    /**
     * Returns true if history is empty.
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * Clears all history entries.
     */
    public void clear() {
        history.clear();
    }

    /**
     * Returns the maximum number of entries this history can hold.
     */
    public int getMaxSize() {
        return maxSize;
    }
}
