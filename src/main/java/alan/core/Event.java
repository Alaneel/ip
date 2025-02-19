package alan.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task.
 */
public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    /**
     * Constructor for Event.
     * @param description
     * @param startTime
     * @param endTime
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = parseDateTime(startTime);
        this.endTime = parseDateTime(endTime);
        validateTimeRange();
    }

    /**
     * Constructor for Event.
     * @return LocalDateTime object
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Parses a date string into a LocalDateTime object.
     * @return LocalDateTime object
     */
    public LocalDateTime getEndTime() {
        return endTime;
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
     * Validates the time range of the event.
     */
    private void validateTimeRange() {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
    }

    /**
     * Returns a string representation of the event task.
     * @return String representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " " + description
                + " (from: " + startTime.format(DISPLAY_FORMAT)
                + " to: " + endTime.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns a string representation of the event task for storage.
     * @return String representation of the event task for storage
     */
    public String getStorageString() {
        return String.format("%s | %d | %s | %s | %s",
                "E",
                isDone ? 1 : 0,
                description,
                startTime.format(INPUT_FORMAT),
                endTime.format(INPUT_FORMAT));
    }
}