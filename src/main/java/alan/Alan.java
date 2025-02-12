package alan;

import alan.core.*;
import alan.exception.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Alan {
    private static final String DIVIDER = "____________________________________________________________";

    private static final String DATA_DIRECTORY = "data";
    private static final String DATA_FILE = "tasks.txt";
    private static final Path DATA_PATH = Paths.get(DATA_DIRECTORY, DATA_FILE);

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
        loadTasks();
    }

    public void start() {
        printWelcomeMessage();
        processUserCommands();
        scanner.close();
    }

    private void loadTasks() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));

            if (!Files.exists(DATA_PATH)) {
                Files.createFile(DATA_PATH);
                return;
            }

            for (String line : Files.readAllLines(DATA_PATH)) {
                try {
                    Task task = parseTaskFromString(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Warning: Skipped corrupted task entry: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load tasks from file. Starting with empty task list.");
        }
    }

    private void saveTasks() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
            ArrayList<String> lines = new ArrayList<>();

            for (Task task : tasks) {
                lines.add(convertTaskToString(task));
            }

            Files.write(DATA_PATH, lines);
        } catch (IOException e) {
            System.out.println("Warning: Could not save tasks to file.");
        }
    }

    private Task parseTaskFromString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format");
        }

        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) throw new IllegalArgumentException("Invalid deadline format");
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) throw new IllegalArgumentException("Invalid event format");
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private String convertTaskToString(Task task) {
        StringBuilder sb = new StringBuilder();

        if (task instanceof Todo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }

        sb.append(" | ")
                .append(task.isDone() ? "1" : "0")
                .append(" | ")
                .append(task.getDescription());

        if (task instanceof Deadline) {
            sb.append(" | ").append(((Deadline) task).getBy());
        } else if (task instanceof Event) {
            sb.append(" | ")
                    .append(((Event) task).getStartTime())
                    .append(" | ")
                    .append(((Event) task).getEndTime());
        }

        return sb.toString();
    }

    private void processUserCommands() {
        boolean isRunning = true;
        while (isRunning) {
            String userInput = getUserInput();
            isRunning = processCommand(userInput);
        }
    }

    private boolean processCommand(String userInput) {
        System.out.println(DIVIDER);

        try {
            boolean shouldContinue = true;

            if (userInput.isEmpty()) {
                throw new AlanException("Please enter a command. Type 'help' to see available commands.");
            }

            String command = userInput.split(" ")[0].trim().toLowerCase();
            shouldContinue = executeCommand(command, userInput);

            return shouldContinue;
        } catch (Exception e) {
            handleCommandException(e);
        } finally {
            System.out.println(DIVIDER);
            System.out.println();
        }
        return true;
    }

    private boolean executeCommand(String command, String userInput) throws AlanException {
        switch (command) {
            case COMMAND_BYE:
                handleExit();
                return false;
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

        validateTaskNumber(numberStr);
        int taskNumber = Integer.parseInt(numberStr) - 1;

        Task task = tasks.get(taskNumber);
        if (markAsDone) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            task.markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
        saveTasks();
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

        validateTaskParameters(description, COMMAND_DEADLINE, by);
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

        validateTaskParameters(description, COMMAND_EVENT, startTime, endTime);
        addTask(new Event(description, startTime, endTime));
    }

    private void handleDelete(String userInput) throws AlanException {
        String numberStr = userInput.substring(COMMAND_DELETE.length()).trim();
        validateTaskNumber(numberStr);

        int taskNumber = Integer.parseInt(numberStr) - 1;
        Task deletedTask = tasks.remove(taskNumber);

        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        saveTasks();
    }

    private String getUserInput() {
        return scanner.nextLine().trim();
    }

    private void printWelcomeMessage() {
        System.out.println(DIVIDER);
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
        System.out.println();
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

    private void addTask(Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        saveTasks();
    }

    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    private void validateTaskNumber(String numberStr) throws AlanException {
        if (numberStr.isEmpty()) {
            throw new InvalidTaskNumberException(tasks.size());
        }

        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            if (!isValidTaskIndex(taskNumber)) {
                throw new InvalidTaskNumberException(tasks.size());
            }
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException(tasks.size());
        }
    }

    private void validateTaskParameters(String description, String command, String... timeParams)
            throws AlanException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException(command);
        }
        for (String timeParam : timeParams) {
            if (timeParam.isEmpty()) {
                throw new InvalidTimeFormatException(command, "YYYY-MM-DD HH:mm");
            }
        }
    }

    private void handleCommandException(Exception e) {
        if (e instanceof EmptyDescriptionException) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Hint: Add a description after the command.");
        } else if (e instanceof InvalidCommandException) {
            System.out.println("Error: " + e.getMessage());
            printAvailableCommands();
        } else if (e instanceof InvalidTaskNumberException) {
            System.out.println("Error: " + e.getMessage());
            handleList();
        } else if (e instanceof InvalidTimeFormatException
                || e instanceof InvalidTaskFormatException
                || e instanceof AlanException) {
            System.out.println("Error: " + e.getMessage());
        } else {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    public static void main(String[] args) {
        new Alan().start();
    }
}