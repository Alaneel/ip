package alan;

import alan.core.Deadline;
import alan.core.Event;
import alan.core.Task;
import alan.core.Todo;
import alan.exception.*;

import java.util.Scanner;

public class Alan {
    private static final String DIVIDER = "____________________________________________________________";
    private static final int MAX_TASKS = 100;
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_DELETE = "delete";

    private Task[] tasks;
    private int taskCount;
    private Scanner scanner;

    public Alan() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
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
                case COMMAND_HELP:
                    printAvailableCommands();
                    break;
                case COMMAND_DELETE:
                    handleDelete(userInput);
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
        } catch (TaskListFullException e) {
            System.out.println("Error: " + e.getMessage());
            handleList();
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
        if (taskCount == 0) {
            System.out.println("No tasks in the list.");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    private void handleMarkTask(String userInput, boolean markAsDone) throws AlanException {
        String command = markAsDone ? COMMAND_MARK : COMMAND_UNMARK;
        String numberStr = userInput.substring(command.length()).trim();

        if (numberStr.isEmpty()) {
            throw new InvalidTaskNumberException(taskCount);
        }

        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            if (!isValidTaskIndex(taskNumber)) {
                throw new InvalidTaskNumberException(taskCount);
            }

            if (markAsDone) {
                tasks[taskNumber].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
            } else {
                tasks[taskNumber].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println("  " + tasks[taskNumber]);
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(taskCount);
        }
    }

    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < taskCount;
    }

    private void handleTodo(String userInput) throws AlanException {
        if (isTaskListFull()) {
            throw new TaskListFullException();
        }

        String description = userInput.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException(COMMAND_TODO);
        }

        addTask(new Todo(description));
    }

    private void handleDeadline(String userInput) throws AlanException {
        if (isTaskListFull()) {
            throw new TaskListFullException();
        }

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
        if (isTaskListFull()) {
            throw new TaskListFullException();
        }

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
            throw new InvalidTaskNumberException(taskCount);
        }

        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            if (!isValidTaskIndex(taskNumber)) {
                throw new InvalidTaskNumberException(taskCount);
            }

            Task deletedTask = tasks[taskNumber];

            // Shift remaining tasks to fill the gap
            for (int i = taskNumber; i < taskCount - 1; i++) {
                tasks[i] = tasks[i + 1];
            }
            tasks[taskCount - 1] = null;
            taskCount--;

            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + deletedTask);
            System.out.println("Now you have " + taskCount + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(taskCount);
        }
    }

    private boolean isTaskListFull() {
        if (taskCount >= MAX_TASKS) {
            System.out.println("Task list is full. Cannot add more tasks.");
            return true;
        }
        return false;
    }

    private void addTask(Task task) {
        tasks[taskCount] = task;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        taskCount++;
        System.out.println("Now you have " + taskCount + " tasks in the list.");
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