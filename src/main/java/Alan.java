import java.util.Scanner;

public class Alan {
    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                }
            } else {
                if (taskCount < 100) {
                    tasks[taskCount] = userInput;
                    taskCount++;
                    System.out.println("added: " + userInput);
                } else {
                    System.out.println("Task list is full. Cannot add more tasks.");
                }
            }

            System.out.println(divider);
            System.out.println();
        }

        System.out.println(divider);
        scanner.close();
    }
}