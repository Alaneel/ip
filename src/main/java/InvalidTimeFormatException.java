public class InvalidTimeFormatException extends AlanException {
    public InvalidTimeFormatException(String type, String format) {
        super(String.format("Invalid time format for. Expected format: %s", type, format));
    }
}