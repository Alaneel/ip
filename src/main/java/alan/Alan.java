package alan;

import alan.core.*;
import alan.exception.*;

public class Alan {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Alan() {
        this.ui = new Ui();
        this.storage = new Storage(Storage.DATA_PATH);
        this.parser = new Parser();
        try {
            this.tasks = new TaskList(storage.load());
        } catch (AlanException e) {
            ui.showError("Could not load tasks from file. Starting with empty task list.");
            this.tasks = new TaskList();
        }
    }

    public void start() {
        ui.showWelcome();
        processUserCommands();
    }

    private void processUserCommands() {
        boolean isRunning = true;
        while (isRunning) {
            String userInput = ui.readCommand();
            ui.showDivider();
            try {
                isRunning = processCommand(userInput);
            } catch (AlanException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showDivider();
                ui.showNewLine();
            }
        }
    }

    private boolean processCommand(String userInput) throws AlanException {
        String[] command = parser.parseCommand(userInput);
        String commandType = command[0];
        String arguments = command[1];

        switch (commandType) {
            case "bye":
                ui.showGoodbye();
                return false;
            case "list":
                ui.showTaskList(tasks.getTasks());
                break;
            case "mark":
                handleMarkTask(arguments, true);
                break;
            case "unmark":
                handleMarkTask(arguments, false);
                break;
            case "todo":
                handleTodo(arguments);
                break;
            case "deadline":
                handleDeadline(arguments);
                break;
            case "event":
                handleEvent(arguments);
                break;
            case "delete":
                handleDelete(arguments);
                break;
            case "help":
                ui.showHelp();
                break;
        }
        return true;
    }

    // Task handling methods remain in Alan.java for now
    private void handleMarkTask(String arguments, boolean markAsDone) throws AlanException {
        int taskNumber = parser.parseTaskNumber(arguments, tasks.size());
        Task task = tasks.getTask(taskNumber);
        if (markAsDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        ui.showMarkedTask(task, markAsDone);
        storage.save(tasks.getTasks());
    }

    private void handleTodo(String description) throws AlanException {
        parser.validateDescription(description);
        Task todo = new Todo(description);
        tasks.addTask(todo);
        ui.showTaskAdded(todo, tasks.size());
        storage.save(tasks.getTasks());
    }

    private void handleDeadline(String arguments) throws AlanException {
        String[] parts = parser.parseDeadline(arguments);
        Task deadline = new Deadline(parts[0], parts[1]);
        tasks.addTask(deadline);
        ui.showTaskAdded(deadline, tasks.size());
        storage.save(tasks.getTasks());
    }

    private void handleEvent(String arguments) throws AlanException {
        String[] parts = parser.parseEvent(arguments);
        Task event = new Event(parts[0], parts[1], parts[2]);
        tasks.addTask(event);
        ui.showTaskAdded(event, tasks.size());
        storage.save(tasks.getTasks());
    }

    private void handleDelete(String arguments) throws AlanException {
        int taskNumber = parser.parseTaskNumber(arguments, tasks.size());
        Task deletedTask = tasks.deleteTask(taskNumber);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    public static void main(String[] args) {
        new Alan().start();
    }
}