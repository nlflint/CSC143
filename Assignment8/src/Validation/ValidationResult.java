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
     * Messages says why the validation failed. If validation is successful, then message is blank.
     */
    public String message;
}
