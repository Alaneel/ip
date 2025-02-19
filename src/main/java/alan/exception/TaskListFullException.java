package alan.exception;

/**
 * Represents an exception thrown when the user tries to add a task to a full task list.
 */
public class TaskListFullException extends AlanException {
    /**
     * Constructs a TaskListFullException with a default error message.
     */
    public TaskListFullException() {
        super("Task list has reached its maximum capacity. Please complete some tasks before adding new ones.");
    }
}