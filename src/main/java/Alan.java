import java.util.Scanner;

public class Alan {
    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(divider);
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        System.out.println(divider);
        System.out.println();

        while (true) {
            String userInput = scanner.nextLine().trim();
            System.out.println(divider);

            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                if (taskCount == 0) {
                    System.out.println("No tasks in the list.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                }
            } else if (userInput.startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                    if (taskNumber >= 0 && taskNumber < taskCount) {
                        tasks[taskNumber].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[taskNumber]);
                    } else {
                        System.out.println("Invalid task number. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please provide a valid task number.");
                }
            } else if (userInput.startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                    if (taskNumber >= 0 && taskNumber < taskCount) {
                        tasks[taskNumber].markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[taskNumber]);
                    } else {
                        System.out.println("Invalid task number. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please provide a valid task number.");
                }
            } else if (userInput.startsWith("todo ")) {
                if (taskCount < 100) {
                    String description = userInput.substring(5);
                    tasks[taskCount] = new Todo(description);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);
                    taskCount++;
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else {
                    System.out.println("Task list is full. Cannot add more tasks.");
                }
            } else if (userInput.startsWith("deadline ")) {
                if (taskCount < 100) {
                    String[] parts = userInput.split(" /by ", 2);
                    if (parts.length == 2) {
                        String description = parts[0].substring(9);
                        String by = parts[1];
                        tasks[taskCount] = new Deadline(description, by);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks[taskCount]);
                        taskCount++;
                        System.out.println("Now you have " + taskCount + " tasks in the list.");
                    } else {
                        System.out.println("Please provide a deadline in the format: deadline [description] /by [time]");
                    }
                } else {
                    System.out.println("Task list is full. Cannot add more tasks.");
                }
            } else if (userInput.startsWith("event ")) {
                if (taskCount < 100) {
                    String[] parts = userInput.split(" /from | /to ", 3);
                    if (parts.length == 3) {
                        String description = parts[0].substring(6);
                        String startTime = parts[1];
                        String endTime = parts[2];
                        tasks[taskCount] = new Event(description, startTime, endTime);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks[taskCount]);
                        taskCount++;
                        System.out.println("Now you have " + taskCount + " tasks in the list.");
                    } else {
                        System.out.println("Please provide an event in the format: event [description] /from [start] /to [end]");
                    }
                } else {
                    System.out.println("Task list is full. Cannot add more tasks.");
                }
            } else {
                System.out.println("Invalid command. Available commands: todo, deadline, event, list, mark, unmark, bye");
            }

            System.out.println(divider);
            System.out.println();
        }

        scanner.close();
    }
}