package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Deadline;
import alan.core.Task;
import alan.exception.AlanException;
import alan.exception.EmptyDescriptionException;
import alan.exception.InvalidTimeFormatException;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    public AddDeadlineCommand(String description, String by) throws AlanException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }
        if (by.isEmpty()) {
            throw new InvalidTimeFormatException("deadline", "YYYY-MM-DD HH:mm");
        }
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task deadline = new Deadline(description, by);
        tasks.addTask(deadline);
        ui.showTaskAdded(deadline, tasks.size());
        storage.save(tasks.getTasks());
    }
}