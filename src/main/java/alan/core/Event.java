package alan.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = parseDateTime(startTime);
        this.endTime = parseDateTime(endTime);
        validateTimeRange();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    private LocalDateTime parseDateTime(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in the format: YYYY-MM-DD HHMM");
        }
    }

    private void validateTimeRange() {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " " + description
                + " (from: " + startTime.format(DISPLAY_FORMAT)
                + " to: " + endTime.format(DISPLAY_FORMAT) + ")";
    }

    public String getStorageString() {
        return String.format("%s | %d | %s | %s | %s",
                "E",
                isDone ? 1 : 0,
                description,
                startTime.format(INPUT_FORMAT),
                endTime.format(INPUT_FORMAT));
    }
}