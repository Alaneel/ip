public class TaskListFullException extends AlanException {
    public TaskListFullException() {
        super("Task list has reached its maximum capacity. Please complete some tasks before adding new ones.");
    }
}