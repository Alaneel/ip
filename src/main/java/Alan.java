import java.util.Scanner;

public class Alan {
    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        System.out.println(divider);
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        System.out.println(divider);
        System.out.println();

        while (true) {
            String userInput = scanner.nextLine();

            System.out.println(divider);

            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            System.out.println(userInput);
            System.out.println(divider);
            System.out.println();
        }

        System.out.println(divider);
        scanner.close();
    }
}