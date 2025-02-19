package alan.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task.
 */
public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    /**
     * Constructor for Deadline.
     * @param description
     * @param by
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Constructor for Deadline.
     * @return LocalDateTime object
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Parses a date string into a LocalDateTime object.
     * @param dateStr
     * @return LocalDateTime object
     */
    private LocalDateTime parseDateTime(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in the format: YYYY-MM-DD HHMM");
        }
    }

    /**
     * Returns a string representation of the deadline task.
     * @return String representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " " + description
                + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns a string representation of the deadline task for storage.
     * @return
     */
    public String getStorageString() {
        return String.format("%s | %d | %s | %s",
                "D",
                isDone ? 1 : 0,
                description,
                by.format(INPUT_FORMAT));
    }
}