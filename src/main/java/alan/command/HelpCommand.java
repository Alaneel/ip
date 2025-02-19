package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;

/**
 * Represents a command to show the help message.
 */
public class HelpCommand extends Command {
    /**
     * Executes the command to show the help message.
     * @param tasks
     * @param ui
     * @param storage
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showHelp();
    }
}