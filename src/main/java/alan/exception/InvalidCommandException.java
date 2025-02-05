package alan.exception;

public class InvalidCommandException extends AlanException {
    public InvalidCommandException() {
        super("Unrecognized command. Type 'help' to see available commands.");
    }
}