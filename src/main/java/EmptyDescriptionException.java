public class EmptyDescriptionException extends AlanException {
    public EmptyDescriptionException(String taskType) {
        super(String.format("The %s description cannot be empty. Please provide a description.", taskType));
    }
}