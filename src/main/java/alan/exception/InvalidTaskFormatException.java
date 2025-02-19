package alan.exception;

/**
 * Represents an exception thrown when the user inputs an invalid task format.
 */
public class InvalidTaskFormatException extends AlanException {
    /**
     * Constructs an InvalidTaskFormatException with the specified task type and usage.
     * @param taskType
     * @param usage
     */
    public InvalidTaskFormatException(String taskType, String usage) {
        super(String.format("Invalid %s format. Usage: %s", taskType, usage));
    }
}