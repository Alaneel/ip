package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}