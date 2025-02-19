package alan.storage;

import alan.core.Deadline;
import alan.core.Event;
import alan.core.Task;
import alan.core.Todo;
import alan.exception.AlanException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents the storage of tasks in the hard disk.
 */
public class Storage {
    public static final String DATA_DIRECTORY = "data";
    public static final String DATA_FILE = "tasks.txt";
    public static final Path DATA_PATH = Paths.get(DATA_DIRECTORY, DATA_FILE);

    private final Path filePath;

    /**
     * Creates a new Storage object with the default file path.
     */
    public Storage() {
        this(DATA_PATH);
    }

    /**
     * Creates a new Storage object with the specified file path.
     * @param filePath
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * @return ArrayList of tasks
     * @throws AlanException
     */
    public ArrayList<Task> load() throws AlanException {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return new ArrayList<>();
            }

            ArrayList<Task> tasks = new ArrayList<>();
            for (String line : Files.readAllLines(filePath)) {
                tasks.add(parseTaskFromString(line));
            }
            return tasks;
        } catch (IOException | IllegalArgumentException e) {
            throw new AlanException("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Saves tasks to the file.
     * @param tasks
     * @throws AlanException
     */
    public void save(ArrayList<Task> tasks) throws AlanException {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
            ArrayList<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(convertTaskToString(task));
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new AlanException("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Parses a task from a string.
     * @param line
     * @return Task
     */
    private Task parseTaskFromString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format");
        }

        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String description = parts[2];

        try {
            Task task;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) throw new IllegalArgumentException("Invalid deadline format");
                    task = new Deadline(description, parts[3]);
                    break;
                case "E":
                    if (parts.length < 5) throw new IllegalArgumentException("Invalid event format");
                    task = new Event(description, parts[3], parts[4]);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown task type: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format in stored task");
        }
    }

    /**
     * Converts a task to a string.
     * @param task
     * @return
     */
    private String convertTaskToString(Task task) {
        StringBuilder sb = new StringBuilder();

        if (task instanceof Todo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }

        sb.append(" | ")
                .append(task.isDone() ? "1" : "0")
                .append(" | ")
                .append(task.getDescription());

        DateTimeFormatter storageFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ")
                    .append(deadline.getBy().format(storageFormat));
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ")
                    .append(event.getStartTime().format(storageFormat))
                    .append(" | ")
                    .append(event.getEndTime().format(storageFormat));
        }

        return sb.toString();
    }
}