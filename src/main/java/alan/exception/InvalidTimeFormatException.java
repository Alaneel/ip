package alan.exception;

/**
 * Exception thrown when the time format is invalid.
 */
public class InvalidTimeFormatException extends AlanException {
    /**
     * Constructor for InvalidTimeFormatException.
     * @param type
     * @param format
     */
    public InvalidTimeFormatException(String type, String format) {
        super(String.format("Invalid time format for. Expected format: %s", type, format));
    }
}