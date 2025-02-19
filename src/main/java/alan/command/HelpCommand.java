package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;

public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showHelp();
    }
}