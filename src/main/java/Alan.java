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
            } else {
                if (taskCount < 100) {
                    tasks[taskCount] = new Task(userInput);
                    taskCount++;
                    System.out.println("added: " + userInput);
                } else {
                    System.out.println("Task list is full. Cannot add more tasks.");
                }
            }

            System.out.println(divider);
            System.out.println();
        }

        scanner.close();
    }
}