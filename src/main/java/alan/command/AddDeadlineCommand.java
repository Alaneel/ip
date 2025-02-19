package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Deadline;
import alan.core.Task;
import alan.exception.AlanException;
import alan.exception.EmptyDescriptionException;
import alan.exception.InvalidTimeFormatException;

/**
 * Represents a command to add a deadline task.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    /**
     * Constructor for AddDeadlineCommand.
     * @param description
     * @param by
     * @throws AlanException
     */
    public AddDeadlineCommand(String description, String by) throws AlanException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }
        if (by.isEmpty()) {
            throw new InvalidTimeFormatException("deadline", "yyyy-MM-dd HHmm");
        }
        this.description = description;
        this.by = by;
    }

    /**
     * Adds a deadline task to the task list.
     * @param tasks
     * @param ui
     * @param storage
     * @throws AlanException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task deadline = new Deadline(description, by);
        tasks.addTask(deadline);
        ui.showTaskAdded(deadline, tasks.size());
        storage.save(tasks.getTasks());
    }
}