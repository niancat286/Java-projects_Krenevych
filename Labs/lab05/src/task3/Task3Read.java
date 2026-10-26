package task3;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Task3Read {

    public static double sumNumbersFromFile(String fileName) {
        // Implementation for reading numbers from numbers.bin will go here
        try {
            Path inputPath = Paths.get(fileName);
            if (Files.exists(inputPath)) {
                try (DataInputStream dis = new DataInputStream(Files.newInputStream(inputPath))) {
                    double sum = 0;
                    while (dis.available() > 0) {
                        double number = dis.readDouble();
                        System.out.println("Read number: " + number);
                        sum += number;
                    }
                    return sum;
                }
            } else {
                System.out.println("File " + fileName + " does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static void main(String[] args) {
        System.out.println("Sum of numbers: " + sumNumbersFromFile("numbers.bin"));
    }
}
