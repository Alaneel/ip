package alan.exception;

/**
 * Represents an exception thrown when the description of a task is empty.
 */
public class EmptyDescriptionException extends AlanException {
    /**
     * Constructs an EmptyDescriptionException object.
     * @param taskType
     */
    public EmptyDescriptionException(String taskType) {
        super(String.format("The %s description cannot be empty. Please provide a description.", taskType));
    }
}