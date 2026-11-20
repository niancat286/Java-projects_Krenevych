import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortLinesArrayList {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(inputFile, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Не вдалося прочитати файл '" + inputFile + "': " + e.getMessage());
            return;
        }

        System.out.println("До сортування:");
        for (String line : lines) {
            System.out.println(line);
        }

        // Сортуємо список за допомогою Collections.sort()
        Collections.sort(lines);

        System.out.println("\nПісля сортування:");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
