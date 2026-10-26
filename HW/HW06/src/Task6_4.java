import java.io.*;
import java.util.Random;

public class Task6_4 {

    private static int getTask2Value(int num) {
        if (num < 0) {
            return Math.abs(num);
        } else if (num > 0) {
            return 0;
        }
        return 0;
    }

    private static int getTask3Value(int num) {
        if (num % 2 == 0) {
            return num / 2;
        } else {
            return num * 2;
        }
    }


    public static void genRandFile(String fileName, int count) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            Random random = new Random();
            System.out.print("Початкові числа: ");
            for (int i = 0; i < count; i++) {
                int num = random.nextInt(41) - 20;
                dos.writeInt(num);
                System.out.print(num + " ");
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("Помилка створення файлу: " + e.getMessage());
        }
    }

    public static void calcEvenProduct(String inFile, String outFile) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inFile));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile))) {

            long product = 1;
            boolean hasEvens = false;

            while (true) {
                try {
                    int num = dis.readInt();
                    if (num % 2 == 0 && num != 0) {
                        product *= num;
                        hasEvens = true;
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            dos.writeLong(hasEvens ? product : 0);
            System.out.println("Завдання 1 (Добуток парних): " + (hasEvens ? product : 0));

        } catch (IOException e) {
            System.err.println("Помилка Завдання 1: " + e.getMessage());
        }
    }

    public static void processTask2(String inFile, String outFile) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inFile));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile))) {

            System.out.print("Завдання 2 (Від'ємні->модуль, Додатні->0): ");
            while (true) {
                try {
                    int originalNum = dis.readInt();

                    int processedNum = getTask2Value(originalNum);

                    dos.writeInt(processedNum);
                    System.out.print(processedNum + " ");
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println();

        } catch (IOException e) {
            System.err.println("Помилка Завдання 2: " + e.getMessage());
        }
    }

    public static void processTask3(String inFile, String outFile) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inFile));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile))) {

            System.out.print("Завдання 3 (Парні/2, Непарні*2): ");
            while (true) {
                try {
                    int originalNum = dis.readInt();

                    int processedNum = getTask3Value(originalNum);

                    dos.writeInt(processedNum);
                    System.out.print(processedNum + " ");
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println();

        } catch (IOException e) {
            System.err.println("Помилка Завдання 3: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String inputFile = "random_numbers.bin";

        genRandFile(inputFile, 10);
        calcEvenProduct(inputFile, "product_evens.bin");
        processTask2(inputFile, "replaced_values.bin");
        processTask3(inputFile, "modified_parity.bin");

        System.out.println("\n Перевірте бінарні файли");
    }
}