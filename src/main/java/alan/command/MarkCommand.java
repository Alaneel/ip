package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Task;
import alan.exception.AlanException;

/**
 * Represents a command to mark a task as done or not done.
 */
public class MarkCommand extends Command {
    private final int taskNumber;
    private final boolean markAsDone;

    /**
     * Constructor for MarkCommand.
     * @param taskNumber
     * @param markAsDone
     */
    public MarkCommand(int taskNumber, boolean markAsDone) {
        this.taskNumber = taskNumber;
        this.markAsDone = markAsDone;
    }

    /**
     * Executes the command to mark a task as done or not done.
     * @param tasks
     * @param ui
     * @param storage
     * @throws AlanException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task task = tasks.getTask(taskNumber);
        if (markAsDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        ui.showMarkedTask(task, markAsDone);
        storage.save(tasks.getTasks());
    }
}