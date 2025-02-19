package alan.exception;

/**
 * Represents an exception specific to Alan.
 */
public class AlanException extends Exception {
    /**
     * Constructs an AlanException with the specified message.
     * @param message
     */
    public AlanException(String message) {
        super(message);
    }
}