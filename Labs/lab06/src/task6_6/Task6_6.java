package task6_6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Task6_6 {

    public static void processFile(String fileF, String fileG) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileF));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileG))) {

            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder swappedLine = new StringBuilder();

                // перебираємо рядок по одному символу
                for (char c : line.toCharArray()) {
                    if (c == '0') {
                        swappedLine.append('1');
                    } else if (c == '1') {
                        swappedLine.append('0');
                    } else {
                        swappedLine.append(c); // усі інші символи залишаю як є
                    }
                }

                writer.write(swappedLine.toString());
                writer.newLine(); // новий рядок у файлі G
            }
        }
    }

    public static void main(String[] args) {
        String fileF = "F.txt";
        String fileG = "G.txt";

        try {
            processFile(fileF, fileG);
            System.out.println("Файл успішно оброблено. Результат збережено у " + fileG);
        } catch (IOException e) {
            System.err.println("Помилка роботи з файлами: " + e.getMessage());
        }
    }

}
