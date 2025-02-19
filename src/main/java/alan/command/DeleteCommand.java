package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Task;
import alan.exception.AlanException;

public class DeleteCommand extends Command {
    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task deletedTask = tasks.deleteTask(taskNumber);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks.getTasks());
    }
}