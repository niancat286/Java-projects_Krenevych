import hw03package.PalindromeFinder;
import hw03package.UpcCalc;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Task 3.5 from personal:\n");

        System.out.print("Input size of array: ");
        int size_3_5 = scanner.nextInt();

        if (size_3_5 <= 0) {
            System.out.println("Size cannot be less than 0");
            scanner.close();
            return;
        }

        int[] numbers = new int[size_3_5];
        System.out.println("Input " + size_3_5 + " integers split with space or Enter:");
        for (int i = 0; i < size_3_5; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.println("\nResult:");
        PalindromeFinder.findPrintPalindrome(numbers);


        System.out.println("Task 3.15 from personal:\n");

        /// WARNING: use "Edit configuration" and input number there or use such commands in terminal:
        /// javac -cp . Main.java hw03package/*.java
        /// java -cp . Main 04850000102


        if (args.length == 0) {
            System.out.println("Error: input 11-digit number");
            return;
        }

        long inputNumber = Long.parseLong(args[0]);

        int checkDigit = UpcCalc.calcCheckDigit(inputNumber);


        System.out.println("Remainder d1: " + checkDigit);
        System.out.printf("Full UPC: %011d%d\n", inputNumber, checkDigit);

    }
}