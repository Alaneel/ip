package alan.parser;

import alan.command.*;
import alan.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Command parse(String fullCommand) throws AlanException {
        if (fullCommand.isEmpty()) {
            throw new AlanException("Please enter a command. Type 'help' to see available commands.");
        }

        String[] parts = fullCommand.split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandType) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(parseTaskNumber(arguments), true);
            case "unmark":
                return new MarkCommand(parseTaskNumber(arguments), false);
            case "todo":
                return new AddTodoCommand(arguments);
            case "deadline":
                String[] deadlineParts = parseDeadline(arguments);
                return new AddDeadlineCommand(deadlineParts[0], deadlineParts[1]);
            case "event":
                String[] eventParts = parseEvent(arguments);
                return new AddEventCommand(eventParts[0], eventParts[1], eventParts[2]);
            case "delete":
                return new DeleteCommand(parseTaskNumber(arguments));
            case "help":
                return new HelpCommand();
            case "find":
                if (arguments.isEmpty()) {
                    throw new AlanException("Please specify what to find. Use '/date YYYY-MM-DD' for date search or keywords for description search.");
                }
                if (arguments.startsWith("/date ")) {
                    return new FindCommand(arguments.substring(6).trim(), true);
                } else {
                    return new FindCommand(arguments, false);
                }
            default:
                throw new InvalidCommandException();
        }
    }

    private static int parseTaskNumber(String args) throws AlanException {
        try {
            return Integer.parseInt(args.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(0);
        }
    }

    private static String[] parseDeadline(String args) throws AlanException {
        String[] parts = args.split(" /by ", 2);
        if (parts.length != 2) {
            throw new InvalidTaskFormatException("deadline", "deadline [description] /by [time]");
        }
        return parts;
    }

    private static String[] parseEvent(String args) throws AlanException {
        String[] parts = args.split(" /from | /to ", 3);
        if (parts.length != 3) {
            throw new InvalidTaskFormatException("event", "event [description] /from [start] /to [end]");
        }
        return parts;
    }

    private static void validateDateTime(String dateTimeStr) throws AlanException {
        try {
            LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e) {
            throw new AlanException("Date/time must be in the format: YYYY-MM-DD HHMM");
        }
    }
}