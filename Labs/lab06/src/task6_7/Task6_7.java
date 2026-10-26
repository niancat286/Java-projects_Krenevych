package task6_7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Task6_7 {

    public static void geneBinFile(String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            dos.writeDouble(10.5);
            dos.writeDouble(2.25);
            dos.writeDouble(7.0);
            dos.writeDouble(-1.25);

            System.out.println("Тестовий бінарний файл '" + fileName + "' успішно створено.");
        } catch (IOException e) {
            System.err.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    public static void calcSumFromBinFile(String fileName) {
        double sum = 0.0;
        int count = 0;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            while (true) {
                try {
                    double number = dis.readDouble(); // Зчитуємо 8 байтів як одне дійсне число
                    sum += number;
                    count++;
                    System.out.println("Прочитано: " + number);
                } catch (EOFException e) {
                    break;
                }
            }

            System.out.println("Кількість прочитаних чисел: " + count);
            System.out.println("Загальна сума: " + sum);

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        String fileName = "data.bin";
        geneBinFile(fileName);
        calcSumFromBinFile(fileName);
    }
}
