package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Task;
import alan.exception.AlanException;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskNumber;

    /**
     * Constructs a DeleteCommand object.
     * @param taskNumber
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Deletes a task from the task list and saves the updated task list to the storage.
     * @param tasks
     * @param ui
     * @param storage
     * @throws AlanException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task deletedTask = tasks.deleteTask(taskNumber);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks.getTasks());
    }
}