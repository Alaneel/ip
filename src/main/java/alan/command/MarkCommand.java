package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Task;
import alan.exception.AlanException;

public class MarkCommand extends Command {
    private final int taskNumber;
    private final boolean markAsDone;

    public MarkCommand(int taskNumber, boolean markAsDone) {
        this.taskNumber = taskNumber;
        this.markAsDone = markAsDone;
    }

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