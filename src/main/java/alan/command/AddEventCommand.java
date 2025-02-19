package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Event;
import alan.core.Task;
import alan.exception.AlanException;
import alan.exception.EmptyDescriptionException;
import alan.exception.InvalidTimeFormatException;

/**
 * Represents a command to add an event task.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String startTime;
    private final String endTime;

    /**
     * Constructor for AddEventCommand.
     * @param description
     * @param startTime
     * @param endTime
     * @throws AlanException
     */
    public AddEventCommand(String description, String startTime, String endTime) throws AlanException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("event");
        }
        if (startTime.isEmpty() || endTime.isEmpty()) {
            throw new InvalidTimeFormatException("event", "yyyy-MM-dd HHmm");
        }
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Executes the command to add an event task.
     * @param tasks
     * @param ui
     * @param storage
     * @throws AlanException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task event = new Event(description, startTime, endTime);
        tasks.addTask(event);
        ui.showTaskAdded(event, tasks.size());
        storage.save(tasks.getTasks());
    }
}