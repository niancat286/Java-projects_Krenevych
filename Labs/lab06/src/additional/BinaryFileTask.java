package additional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BinaryFileTask {

    public static void main(String[] args) {
        String binaryFileName = "numbers.bin";
        String textFileName = "output.txt";

        // Початкові дані для запису в бінарний файл
        int[] numbers = {15, 42, 8, 99, -5, 23};

        createBinaryFile(binaryFileName, numbers);
        processBinaryFile(binaryFileName, textFileName);
    }

    //Створення бінарного файлу з цілих чисел
    public static void createBinaryFile(String fileName, int[] numbers) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            for (int num : numbers) {
                dos.writeInt(num); // Записуємо число у двійковому вигляді
            }
            System.out.println("Бінарний файл успішно створено.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Читання бінарного файлу, запис у текстовий та обчислення середнього
    public static void processBinaryFile(String inputFileName, String outputTextFileName) {
        StringBuilder numbersString = new StringBuilder();
        long sum = 0;
        int count = 0;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFileName))) {
            // Зчитуємо дані доки не досягнемо кінця файлу (EOFException)
            while (true) {
                int num = dis.readInt();
                numbersString.append(num).append(" ");
                sum += num;
                count++;
            }
        } catch (EOFException e) {
            //  Досягнуто кінець бінарного файлу.
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Обчислення середнього арифметичного
        double average = count > 0 ? (double) sum / count : 0;

        // Формування фінального тексту
        String resultText = "Числа: " + numbersString.toString().trim() +
                System.lineSeparator() +
                "Середнє арифметичне: " + average;

        // Запис у текстовий файл
        Path outputPath = Paths.get(outputTextFileName);
        try {
            Files.writeString(outputPath, resultText);
            System.out.println("Дані успішно записані у текстовий файл.");
            System.out.println(resultText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}