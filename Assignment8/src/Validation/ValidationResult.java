package Validation;

/**
 * The results of validation.
 */
public class ValidationResult {
    /**
     * Constructor defaults to successful validation.
     */
    public ValidationResult() {
        result = true;
        message = "";
    }

    /**
     * The results of the validation. True for success. False for failure.
     */
    public boolean result;

    /**
     * Messages states why the validation failed. If validation did not fail, then message is blank.
     */
    public String message;
}
