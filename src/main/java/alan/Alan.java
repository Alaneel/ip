package alan;

import alan.command.Command;
import alan.core.TaskList;
import alan.exception.*;
import alan.parser.Parser;
import alan.storage.Storage;
import alan.ui.Ui;

/**
 * Alan is a personal assistant chatbot that helps to keep track of various tasks.
 */
public class Alan {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor for Alan.
     */
    public Alan() {
        this.ui = new Ui();
        this.storage = new Storage(Storage.DATA_PATH);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (AlanException e) {
            ui.showError("Could not load tasks from file. Starting with empty task list.");
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the Alan program.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showDivider();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (AlanException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showDivider();
                ui.showNewLine();
            }
        }
    }

    /**
     * Main method for Alan.
     * @param args
     */
    public static void main(String[] args) {
        new Alan().run();
    }
}