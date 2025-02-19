package alan.core;

import alan.exception.AlanException;
import alan.exception.InvalidTaskNumberException;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws AlanException {
        validateTaskIndex(index);
        return tasks.remove(index);
    }

    public Task getTask(int index) throws AlanException {
        validateTaskIndex(index);
        return tasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    private void validateTaskIndex(int index) throws AlanException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException(tasks.size());
        }
    }
}