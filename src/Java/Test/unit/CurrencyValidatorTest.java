package Test.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import service.CurrencyValidator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CurrencyValidator.
 * Tests all validation rules for amounts and string inputs.
 */
@DisplayName("CurrencyValidator Unit Tests")
class CurrencyValidatorTest {

    private CurrencyValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CurrencyValidator();
    }



    @Test
    @DisplayName("Zero amount should be valid")
    void isValidAmount_zero_shouldBeValid() {
        assertTrue(validator.isValidAmount(0.0));
    }

    @Test
    @DisplayName("Positive amount should be valid")
    void isValidAmount_positive_shouldBeValid() {
        assertTrue(validator.isValidAmount(100.0));
    }

    @Test
    @DisplayName("Negative amount should be invalid")
    void isValidAmount_negative_shouldBeInvalid() {
        assertFalse(validator.isValidAmount(-1.0));
    }

    @Test
    @DisplayName("Amount exceeding max should be invalid")
    void isValidAmount_tooLarge_shouldBeInvalid() {
        assertFalse(validator.isValidAmount(2_000_000_000.0));
    }

    @Test
    @DisplayName("NaN should be invalid")
    void isValidAmount_nan_shouldBeInvalid() {
        assertFalse(validator.isValidAmount(Double.NaN));
    }

    @Test
    @DisplayName("Infinite value should be invalid")
    void isValidAmount_infinite_shouldBeInvalid() {
        assertFalse(validator.isValidAmount(Double.POSITIVE_INFINITY));
    }

    @Test
    @DisplayName("Maximum allowed amount should be valid")
    void isValidAmount_maxAllowed_shouldBeValid() {
        assertTrue(validator.isValidAmount(1_000_000_000.0));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1.0, 100.0, 9999.99, 1_000_000.0})
    @DisplayName("Various valid amounts should all pass validation")
    void isValidAmount_variousValid_shouldAllPass(double amount) {
        assertTrue(validator.isValidAmount(amount));
    }


    @Test
    @DisplayName("Valid number string should be valid")
    void isValidAmountString_validNumber_shouldBeValid() {
        assertTrue(validator.isValidAmountString("100"));
    }

    @Test
    @DisplayName("Valid decimal string should be valid")
    void isValidAmountString_validDecimal_shouldBeValid() {
        assertTrue(validator.isValidAmountString("99.99"));
    }

    @Test
    @DisplayName("Null string should be invalid")
    void isValidAmountString_null_shouldBeInvalid() {
        assertFalse(validator.isValidAmountString(null));
    }

    @Test
    @DisplayName("Empty string should be invalid")
    void isValidAmountString_empty_shouldBeInvalid() {
        assertFalse(validator.isValidAmountString(""));
    }

    @Test
    @DisplayName("Blank string should be invalid")
    void isValidAmountString_blank_shouldBeInvalid() {
        assertFalse(validator.isValidAmountString("   "));
    }

    @Test
    @DisplayName("Letter string should be invalid")
    void isValidAmountString_letters_shouldBeInvalid() {
        assertFalse(validator.isValidAmountString("abc"));
    }

    @Test
    @DisplayName("Mixed string should be invalid")
    void isValidAmountString_mixed_shouldBeInvalid() {
        assertFalse(validator.isValidAmountString("100abc"));
    }

    @Test
    @DisplayName("Negative number string should be invalid")
    void isValidAmountString_negative_shouldBeInvalid() {
        assertFalse(validator.isValidAmountString("-50"));
    }

    // --- getValidationError ---

    @Test
    @DisplayName("Empty input should return empty error message")
    void getValidationError_empty_shouldReturnEmptyMessage() {
        String error = validator.getValidationError("");
        assertFalse(error.isEmpty());
    }

    @Test
    @DisplayName("Null input should return error message")
    void getValidationError_null_shouldReturnMessage() {
        String error = validator.getValidationError(null);
        assertFalse(error.isEmpty());
    }

    @Test
    @DisplayName("Letter input should return error message")
    void getValidationError_letters_shouldReturnMessage() {
        String error = validator.getValidationError("abc");
        assertFalse(error.isEmpty());
        assertTrue(error.toLowerCase().contains("invalid") ||
                   error.toLowerCase().contains("number"));
    }

    @Test
    @DisplayName("Valid input should return empty error message")
    void getValidationError_validInput_shouldReturnEmpty() {
        String error = validator.getValidationError("100");
        assertTrue(error.isEmpty());
    }

    @Test
    @DisplayName("Negative input should mention negative in error")
    void getValidationError_negative_shouldMentionNegative() {
        String error = validator.getValidationError("-50");
        assertFalse(error.isEmpty());
        assertTrue(error.toLowerCase().contains("negative"));
    }
    
}
