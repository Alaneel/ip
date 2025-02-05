package alan.exception;

public class InvalidTaskFormatException extends AlanException {
    public InvalidTaskFormatException(String taskType, String usage) {
        super(String.format("Invalid %s format. Usage: %s", taskType, usage));
    }
}