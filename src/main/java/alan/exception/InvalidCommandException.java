package alan.exception;

/**
 * Represents an exception thrown when an invalid command is entered by the user.
 */
public class InvalidCommandException extends AlanException {
    /**
     * Constructs an InvalidCommandException object.
     */
    public InvalidCommandException() {
        super("Unrecognized command. Type 'help' to see available commands.");
    }
}