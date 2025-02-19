package alan.core;

import alan.exception.AlanException;
import alan.exception.InvalidTaskNumberException;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given tasks.
     * @param tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the TaskList.
     * @param task
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the TaskList.
     * @param index
     * @return The deleted task.
     * @throws AlanException
     */
    public Task deleteTask(int index) throws AlanException {
        validateTaskIndex(index);
        return tasks.remove(index);
    }

    /**
     * Gets a task from the TaskList.
     * @param index
     * @return The task.
     * @throws AlanException
     */
    public Task getTask(int index) throws AlanException {
        validateTaskIndex(index);
        return tasks.get(index);
    }

    /**
     * Gets all tasks from the TaskList.
     * @return A list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Gets the number of tasks in the TaskList.
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    private void validateTaskIndex(int index) throws AlanException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException(tasks.size());
        }
    }
}