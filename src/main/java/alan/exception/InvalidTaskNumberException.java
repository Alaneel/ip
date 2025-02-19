package alan.exception;

/**
 * Represents an exception thrown when the user inputs an invalid task number.
 */
public class InvalidTaskNumberException extends AlanException {
    /**
     * Constructs an InvalidTaskNumberException with the specified maximum number of tasks.
     * @param maxTasks
     */
    public InvalidTaskNumberException(int maxTasks) {
        super(String.format("Task number must be between 1 and %d.", maxTasks));
    }
}