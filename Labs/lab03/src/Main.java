import model.Matrix;
import java.util.Scanner;
import model.NumberFinder;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Task 3.4 from classwork:\n");

        System.out.print("Input n");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Invalid n, n is less than 0, try again");
            return;
        }

        System.out.println("\n--- Random matrix ---");
        Matrix randomMatrix = new Matrix(n);
        randomMatrix.fillRand();
        randomMatrix.print();

        // console
        System.out.println("\n--- Console matrix ---");
        Matrix manualMatrix = new Matrix(n);
        manualMatrix.ConsoleInput(scanner);

        System.out.println("\nResult:");
        manualMatrix.print();


        System.out.print("Task 3.4 from personal:\n");

        System.out.print("Input size for array: ");
        int size = scanner.nextInt();

        if (size <= 0) {
            System.out.println("Invalid number, it cannot be less than 0");
        } else {
            int[] numbers = new int[size];
            System.out.println("Input " + size + " integers split with space or Enter:");
            for (int i = 0; i < size; i++) {
                numbers[i] = scanner.nextInt();
            }

            System.out.println("\nResult:");
            NumberFinder.findMinLen(numbers);
        }


        System.out.print("Task 3.9 from personal:\n");


        System.out.print("Input m: ");
        int m = scanner.nextInt();

        if (m <= 0) {
            System.out.println("Invalid number, m cannot be less than 0 ");
            scanner.close();
            return;
        }

        Matrix matrix = new Matrix(m);
        matrix.fillFromConsole(scanner);

        System.out.println("\nManual matrix:");
        matrix.print();

        matrix.rotate90left();
        System.out.println("\nMatrix after rotation 90 degrees left:");
        matrix.print();

        matrix.rotate90left();
        System.out.println("\nMatrix after rotation 180 degrees left:");
        matrix.print();

        matrix.rotate90left();
        System.out.println("\nMatrix after rotation 270 degrees left:");
        matrix.print();

    }
}
