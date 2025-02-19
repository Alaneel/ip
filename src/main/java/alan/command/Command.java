package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.exception.AlanException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException;

    public boolean isExit() {
        return false;
    }
}