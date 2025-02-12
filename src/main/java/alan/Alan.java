package alan;

import alan.core.Deadline;
import alan.core.Event;
import alan.core.Task;
import alan.core.Todo;
import alan.exception.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Alan {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_HELP = "help";

    private ArrayList<Task> tasks;
    private Scanner scanner;

    public Alan() {
        this.tasks = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printWelcomeMessage();
        processUserCommands();
        scanner.close();
    }

    private void processUserCommands() {
        boolean isRunning = true;
        while (isRunning) {
            String userInput = getUserInput();
            isRunning = processCommand(userInput);
        }
    }

    private void printWelcomeMessage() {
        System.out.println(DIVIDER);
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
        System.out.println();
    }

    private String getUserInput() {
        return scanner.nextLine().trim();
    }

    private boolean processCommand(String userInput) {
        System.out.println(DIVIDER);

        try {
            boolean shouldContinue = true;

            if (userInput.isEmpty()) {
                throw new AlanException("Please enter a command. Type 'help' to see available commands.");
            }

            switch (userInput.split(" ")[0].trim().toLowerCase()) {
                case COMMAND_BYE:
                    handleExit();
                    shouldContinue = false;
                    break;
                case COMMAND_LIST:
                    handleList();
                    break;
                case COMMAND_MARK:
                    handleMarkTask(userInput, true);
                    break;
                case COMMAND_UNMARK:
                    handleMarkTask(userInput, false);
                    break;
                case COMMAND_TODO:
                    handleTodo(userInput);
                    break;
                case COMMAND_DEADLINE:
                    handleDeadline(userInput);
                    break;
                case COMMAND_EVENT:
                    handleEvent(userInput);
                    break;
                case COMMAND_DELETE:
                    handleDelete(userInput);
                    break;
                case COMMAND_HELP:
                    printAvailableCommands();
                    break;
                default:
                    throw new AlanException("I don't recognize that command. Type 'help' to see available commands.");
            }

            return shouldContinue;
        } catch (EmptyDescriptionException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Hint: Add a description after the command.");
        } catch (InvalidCommandException e) {
            System.out.println("Error: " + e.getMessage());
            printAvailableCommands();
        } catch (InvalidTaskNumberException e) {
            System.out.println("Error: " + e.getMessage());
            handleList();
        } catch (InvalidTimeFormatException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidTaskFormatException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AlanException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        } finally {
            System.out.println(DIVIDER);
            System.out.println();
        }
        return true;
    }

    private void handleExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private void handleList() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    private void handleMarkTask(String userInput, boolean markAsDone) throws AlanException {
        String command = markAsDone ? COMMAND_MARK : COMMAND_UNMARK;
        String numberStr = userInput.substring(command.length()).trim();

        if (numberStr.isEmpty()) {
            throw new InvalidTaskNumberException(tasks.size());
        }

        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            if (!isValidTaskIndex(taskNumber)) {
                throw new InvalidTaskNumberException(tasks.size());
            }

            Task task = tasks.get(taskNumber);
            if (markAsDone) {
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:");
            } else {
                task.markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println("  " + task);
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(tasks.size());
        }
    }

    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    private void handleTodo(String userInput) throws AlanException {
        String description = userInput.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException(COMMAND_TODO);
        }

        addTask(new Todo(description));
    }

    private void handleDeadline(String userInput) throws AlanException {
        String[] parts = userInput.split(" /by ", 2);
        if (parts.length != 2) {
            throw new InvalidTaskFormatException(COMMAND_DEADLINE, "deadline [description] /by [time]");
        }

        String description = parts[0].substring(COMMAND_DEADLINE.length()).trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new EmptyDescriptionException(COMMAND_DEADLINE);
        }
        if (by.isEmpty()) {
            throw new InvalidTimeFormatException(COMMAND_DEADLINE, "YYYY-MM-DD HH:mm");
        }

        addTask(new Deadline(description, by));
    }

    private void handleEvent(String userInput) throws AlanException {
        String[] parts = userInput.split(" /from | /to ", 3);
        if (parts.length != 3) {
            throw new InvalidTaskFormatException(COMMAND_EVENT, "event [description] /from [start] /to [end]");
        }

        String description = parts[0].substring(COMMAND_EVENT.length()).trim();
        String startTime = parts[1].trim();
        String endTime = parts[2].trim();

        if (description.isEmpty()) {
            throw new EmptyDescriptionException(COMMAND_EVENT);
        }
        if (startTime.isEmpty() || endTime.isEmpty()) {
            throw new InvalidTimeFormatException(COMMAND_EVENT, "YYYY-MM-DD HH:mm");
        }

        addTask(new Event(description, startTime, endTime));
    }

    private void handleDelete(String userInput) throws AlanException {
        String numberStr = userInput.substring(COMMAND_DELETE.length()).trim();

        if (numberStr.isEmpty()) {
            throw new InvalidTaskNumberException(tasks.size());
        }

        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            if (!isValidTaskIndex(taskNumber)) {
                throw new InvalidTaskNumberException(tasks.size());
            }

            Task deletedTask = tasks.remove(taskNumber);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + deletedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(tasks.size());
        }
    }

    private void addTask(Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    private void printAvailableCommands() {
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

    public static void main(String[] args) {
        new Alan().start();
    }
}