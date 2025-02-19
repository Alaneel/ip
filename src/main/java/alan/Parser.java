package alan;

import alan.exception.*;

public class Parser {
    public String[] parseCommand(String userInput) throws AlanException {
        if (userInput.isEmpty()) {
            throw new AlanException("Please enter a command. Type 'help' to see available commands.");
        }

        String[] parts = userInput.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        if (!isValidCommand(command)) {
            throw new InvalidCommandException();
        }

        return new String[]{command, arguments};
    }

    public void validateDescription(String description) throws AlanException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
    }

    public String[] parseDeadline(String arguments) throws AlanException {
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length != 2) {
            throw new InvalidTaskFormatException("deadline", "deadline [description] /by [time]");
        }
        validateDescription(parts[0]);
        validateTimeParameter(parts[1], "deadline");
        return parts;
    }

    public String[] parseEvent(String arguments) throws AlanException {
        String[] parts = arguments.split(" /from | /to ", 3);
        if (parts.length != 3) {
            throw new InvalidTaskFormatException("event", "event [description] /from [start] /to [end]");
        }
        validateDescription(parts[0]);
        validateTimeParameter(parts[1], "event");
        validateTimeParameter(parts[2], "event");
        return parts;
    }

    public int parseTaskNumber(String numberStr, int totalTasks) throws AlanException {
        try {
            int taskNumber = Integer.parseInt(numberStr.trim()) - 1;
            if (taskNumber < 0 || taskNumber >= totalTasks) {
                throw new InvalidTaskNumberException(totalTasks);
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(totalTasks);
        }
    }

    private boolean isValidCommand(String command) {
        return command.matches("bye|list|mark|unmark|todo|deadline|event|delete|help");
    }

    private void validateTimeParameter(String timeParam, String command) throws AlanException {
        if (timeParam.trim().isEmpty()) {
            throw new InvalidTimeFormatException(command, "YYYY-MM-DD HH:mm");
        }
    }
}