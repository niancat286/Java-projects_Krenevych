package task3;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Task3Write {

    public static void writeNumbersToFile(String fileName) {
        try {
            Path outputPath = Paths.get(fileName); 
            if (!Files.exists(outputPath)) {
                Files.createFile(outputPath);
            }
            try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(outputPath))) {
                int number = 0;
                Scanner scanner = new Scanner(System.in);
                double d;
                for (int i = 0; i < 5; i++) {
                    System.out.println("Enter a number to write to " + fileName + ": ");
                    d = scanner.nextDouble();
                    dos.writeDouble(d);
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args) {
        // Implementation for writing sample numbers to numbers.bin using java.nio
        writeNumbersToFile("numbers.bin");
    }
}
