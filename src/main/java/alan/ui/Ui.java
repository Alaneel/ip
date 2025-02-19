package alan.ui;

import alan.core.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showDivider();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        showDivider();
        showNewLine();
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showDivider() {
        System.out.println(DIVIDER);
    }

    public void showNewLine() {
        System.out.println();
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

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

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void showMarkedTask(Task task, boolean isDone) {
        System.out.println(isDone ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  todo [description] - Create a todo task");
        System.out.println("  deadline [description] /by [time] - Create a deadline task");
        System.out.println("  event [description] /from [start] /to [end] - Create an event");
        System.out.println("  list - Show all tasks");
        System.out.println("  mark [task number] - Mark a task as done");
        System.out.println("  unmark [task number] - Mark a task as not done");
        System.out.println("  delete [task number] - Delete a task from the list");
        System.out.println("  bye - Exit the program");
    }

    public void close() {
        scanner.close();
    }
}