package alan.core;

/**
 * Represents a task that the user wants to do.
 */
public class Todo extends Task {
    /**
     * Creates a new Todo task with the given description.
     * @param description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task.
     * @return String representation of the Todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}