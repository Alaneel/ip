import java.util.Scanner;

public class Alan {
    private static final String DIVIDER = "____________________________________________________________";
    private static final int MAX_TASKS = 100;
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";

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

        boolean isRunning = true;
        while (isRunning) {
            String userInput = getUserInput();
            isRunning = processCommand(userInput);
        }

        scanner.close();
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

        boolean shouldContinue = true;

        if (userInput.equals(COMMAND_BYE)) {
            handleExit();
            shouldContinue = false;
        } else if (userInput.equals(COMMAND_LIST)) {
            handleList();
        } else if (userInput.startsWith(COMMAND_MARK)) {
            handleMarkTask(userInput, true);
        } else if (userInput.startsWith(COMMAND_UNMARK)) {
            handleMarkTask(userInput, false);
        } else if (userInput.startsWith(COMMAND_TODO)) {
            handleTodo(userInput);
        } else if (userInput.startsWith(COMMAND_DEADLINE)) {
            handleDeadline(userInput);
        } else if (userInput.startsWith(COMMAND_EVENT)) {
            handleEvent(userInput);
        } else {
            printAvailableCommands();
        }

        System.out.println(DIVIDER);
        System.out.println();

        return shouldContinue;
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

    private void handleMarkTask(String userInput, boolean markAsDone) {
        String command = markAsDone ? COMMAND_MARK : COMMAND_UNMARK;
        try {
            int taskNumber = Integer.parseInt(userInput.substring(command.length())) - 1;
            if (isValidTaskIndex(taskNumber)) {
                if (markAsDone) {
                    tasks[taskNumber].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                } else {
                    tasks[taskNumber].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                }
                System.out.println("  " + tasks[taskNumber]);
            } else {
                System.out.println("Invalid task number. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid task number.");
        }
    }

    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < taskCount;
    }

    private void handleTodo(String userInput) {
        if (isTaskListFull()) {
            return;
        }

        String description = userInput.substring(COMMAND_TODO.length());
        addTask(new Todo(description));
    }

    private void handleDeadline(String userInput) {
        if (isTaskListFull()) {
            return;
        }

        String[] parts = userInput.split(" /by ", 2);
        if (parts.length == 2) {
            String description = parts[0].substring(COMMAND_DEADLINE.length());
            String by = parts[1];
            addTask(new Deadline(description, by));
        } else {
            System.out.println("Please provide a deadline in the format: deadline [description] /by [time]");
        }
    }

    private void handleEvent(String userInput) {
        if (isTaskListFull()) {
            return;
        }

        String[] parts = userInput.split(" /from | /to ", 3);
        if (parts.length == 3) {
            String description = parts[0].substring(COMMAND_EVENT.length());
            String startTime = parts[1];
            String endTime = parts[2];
            addTask(new Event(description, startTime, endTime));
        } else {
            System.out.println("Please provide an event in the format: event [description] /from [start] /to [end]");
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
        System.out.println("Invalid command. Available commands: todo, deadline, event, list, mark, unmark, bye");
    }

    public static void main(String[] args) {
        new Alan().start();
    }
}