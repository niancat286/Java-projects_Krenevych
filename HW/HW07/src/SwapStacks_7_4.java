import java.util.Stack;
import java.util.Scanner;

public class SwapStacks_7_4 {

    private static <T> void swap(Stack<T> first, Stack<T> second) {
        Stack<T> temp1 = new Stack<>();
        Stack<T> temp2 = new Stack<>();

        while (!first.isEmpty()) {
            temp1.push(first.pop());
        }
        while (!second.isEmpty()) {
            temp2.push(second.pop());
        }

        while (!temp2.isEmpty()) {
            first.push(temp2.pop());
        }
        while (!temp1.isEmpty()) {
            second.push(temp1.pop());
        }
    }

    private static void fillStackFromLine(Stack<String> stack, String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return;
        }
        String[] tokens = trimmed.split("\\s+");
        for (String token : tokens) {
            stack.push(token);
        }
    }

    public static void main(String[] args) {
        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введіть елементи першого стека через пробіл (або просто Enter для порожнього):");
            String firstLine = scanner.hasNextLine() ? scanner.nextLine() : "";
            fillStackFromLine(stack1, firstLine);

            System.out.println("Введіть елементи другого стека через пробіл (або просто Enter для порожнього):");
            String secondLine = scanner.hasNextLine() ? scanner.nextLine() : "";
            fillStackFromLine(stack2, secondLine);
        }

        System.out.println("\nДо обміну:");
        System.out.println("stack1: " + stack1);
        System.out.println("stack2: " + stack2);

        swap(stack1, stack2);

        System.out.println("\nПісля обміну:");
        System.out.println("stack1: " + stack1);
        System.out.println("stack2: " + stack2);
    }
}