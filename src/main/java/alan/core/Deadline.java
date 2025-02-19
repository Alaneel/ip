package alan.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    public LocalDateTime getBy() {
        return by;
    }

    private LocalDateTime parseDateTime(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in the format: YYYY-MM-DD HHMM");
        }
    }

    @Override
    public String toString() {
        return "[D]" + " " + description
                + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }
    public String getStorageString() {
        return String.format("%s | %d | %s | %s",
                "D",
                isDone ? 1 : 0,
                description,
                by.format(INPUT_FORMAT));
    }
}