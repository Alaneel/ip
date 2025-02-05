package alan.exception;

public class InvalidTaskNumberException extends AlanException {
    public InvalidTaskNumberException(int maxTasks) {
        super(String.format("Task number must be between 1 and %d.", maxTasks));
    }
}