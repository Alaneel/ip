package alan.command;

import alan.storage.Storage;
import alan.core.TaskList;
import alan.ui.Ui;
import alan.core.Task;
import alan.core.Todo;
import alan.exception.AlanException;
import alan.exception.EmptyDescriptionException;

/**
 * Represents a command to add a todo task.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Constructor for AddTodoCommand.
     * @param description
     * @throws AlanException
     */
    public AddTodoCommand(String description) throws AlanException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        this.description = description;
    }

    /**
     * Adds a todo task to the task list.
     * @param tasks
     * @param ui
     * @param storage
     * @throws AlanException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlanException {
        Task todo = new Todo(description);
        tasks.addTask(todo);
        ui.showTaskAdded(todo, tasks.size());
        storage.save(tasks.getTasks());
    }
}