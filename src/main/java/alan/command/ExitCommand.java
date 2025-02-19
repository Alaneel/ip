package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;

/**
 * Represents a command to exit the program.
 */
public class ExitCommand extends Command {
    /**
     * Executes the command to exit the program.
     * @param tasks
     * @param ui
     * @param storage
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns true to indicate that the program should exit.
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}