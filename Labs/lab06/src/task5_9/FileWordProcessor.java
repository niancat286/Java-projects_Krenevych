package task5_9;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class FileWordProcessor {

    public static int countWords(String fileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            return 0;
        }
        int wordCount = 0;

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Перевіряємо, чи рядок не порожній
                if (!line.trim().isEmpty()) {
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
            }
        }
        return wordCount;
    }

    public static void cleanFile(String fileName, String outputFileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Input file name cannot be null or empty");
        }
        if (outputFileName == null || outputFileName.isEmpty()) {
            throw new IllegalArgumentException("Output file name cannot be null or empty");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Пропускаємо повністю порожні рядки
                }

                String[] words = line.trim().split("\\s+");
                StringBuilder cleanLine = new StringBuilder();

                for (String word : words) {
                    // Перевіряємо, чи складається слово з однієї літери
                    boolean isOneLetterWord = word.length() == 1 && Character.isLetter(word.charAt(0));

                    // Додаємо слово, тільки якщо це НЕ одна літера
                    if (!isOneLetterWord) {
                        cleanLine.append(word).append(" ");
                    }
                }

                // Записуємо рядок, якщо після очищення в ньому щось залишилося
                if (cleanLine.length() > 0) {
                    bufferedWriter.write(cleanLine.toString().trim());
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.flush();
        }
    }

    public static void main(String[] args) {
        try {
            int wordCount = countWords("input.txt");
            System.out.println("Word count: " + wordCount);
            cleanFile("input.txt", "output.txt");
            System.out.println("File cleaned successfully!");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }











}
