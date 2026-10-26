package task8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ColleaguePrank {

    public static void main(String[] args) {
        File file = new File("input.txt");
        int totalSum = 0;

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split("[^0-9]+");

                for (String part : parts) {
                    if (!part.isEmpty()) {
                        totalSum += Integer.parseInt(part);
                    }
                }
            }

            System.out.println("Сума всіх прихованих чисел: " + totalSum);
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Помилка: файл " + file.getName() + " не знайдено.");
        }
    }
}
