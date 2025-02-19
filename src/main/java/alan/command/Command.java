package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.exception.AlanException;

/**
 * Represents a command to be executed by the user.
 */
public abstract class Command {
    /**
     * Executes the command.
     * @param tasks
     * @param ui
     * @param storage
     * @throws AlanException
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException;

    /**
     * Returns true if the command is an exit command.
     * @return false
     */
    public boolean isExit() {
        return false;
    }
}