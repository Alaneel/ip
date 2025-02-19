package alan.ui;

import alan.core.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles interactions with the user.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Creates a new Ui object.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        showDivider();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        showDivider();
        showNewLine();
    }

    /**
     * Reads the next command from the user.
     * @return The command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Shows a divider.
     */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Shows a new line.
     */
    public void showNewLine() {
        System.out.println();
    }

    /**
     * Shows an error message.
     * @param message
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Shows the task list.
     * @param tasks
     */
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Shows the task added message.
     * @param task
     * @param totalTasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Shows the task deleted message.
     * @param task
     * @param totalTasks
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Shows the marked task message.
     * @param task
     * @param isDone
     */
    public void showMarkedTask(Task task, boolean isDone) {
        System.out.println(isDone ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Shows the help message.
     */
    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  todo [description] - Create a todo task");
        System.out.println("  deadline [description] /by [time] (format: yyyy-MM-dd HHmm) - Create a deadline task");
        System.out.println("  event [description] /from [start] (format: yyyy-MM-dd HHmm) /to [end] (format: yyyy-MM-dd HHmm) - Create an event");
        System.out.println("  list - Show all tasks");
        System.out.println("  find [keyword] - Search for tasks containing the keyword");
        System.out.println("  find /date YYYY-MM-DD - Search for tasks on a specific date");
        System.out.println("  mark [task number] - Mark a task as done");
        System.out.println("  unmark [task number] - Mark a task as not done");
        System.out.println("  delete [task number] - Delete a task from the list");
        System.out.println("  bye - Exit the program");
    }

    /**
     * Shows the tasks for a specific date.
     * @param tasks
     * @param date
     */
    public void showTasksForDate(ArrayList<Task> tasks, LocalDate date) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for " +
                    date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
            return;
        }

        System.out.println("Here are the tasks for " +
                date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Shows the search results.
     * @param tasks
     * @param keyword
     */
    public void showSearchResults(ArrayList<Task> tasks, String keyword) {
        showDivider();

        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found for '" + keyword + "'.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }

        showDivider();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}