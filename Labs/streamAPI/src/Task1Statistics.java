import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Task1Statistics {

    public static void main(String[] args) throws IOException {
        Path root;
        double delta;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter directory path: ");
            String dirInput = scanner.nextLine().trim();
            if (dirInput.isEmpty()) {
                throw new IllegalArgumentException("Directory path must not be empty.");
            }
            root = Path.of(dirInput);

            System.out.print("Enter non-negative delta: ");
            String deltaInput = scanner.nextLine().trim();
            delta = parseFiniteDouble(deltaInput, "delta");
        }

        if (delta < 0) {
            throw new IllegalArgumentException("delta must be non-negative.");
        }
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("Directory does not exist or is not a directory: " + root);
        }

        List<Path> logFiles = findMeasurementFiles(root);
        List<Double> measurements = new ArrayList<>();

        for (Path file : logFiles) {
            readMeasurements(file, measurements);
        }

        if (measurements.isEmpty()) {
            throw new IOException("No valid measurements found in matching log*.txt files under: " + root);
        }

        DoubleSummaryStatistics stats = measurements.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        long count = stats.getCount();
        double mean = stats.getAverage();
        double min = stats.getMin();
        double max = stats.getMax();

        List<Double> sortedMeasurements = measurements.stream()
                .sorted()
                .toList();

        List<Double> outliers = measurements.stream()
                .filter(x -> Math.abs(x - mean) > delta)
                .toList();

        System.out.println("Found files (" + logFiles.size() + "):");
        logFiles.forEach(file -> System.out.println("  " + file));

        System.out.println("Count: " + count);
        System.out.println("Mean: " + mean);
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
        System.out.println("Sorted measurements: " + sortedMeasurements);
        System.out.println("Measurements with |x_i - mean| > " + delta + ": " + outliers);

        if (Double.compare(min, max) == 0) {
            System.out.println("Trimmed mean (without one min and one max): " + min);
        } else if (count <= 2) {
            System.out.println("Trimmed mean (without one min and one max): "
                    + "cannot be computed because no measurements remain after removing min and max.");
        } else {
            double trimmedMean = (stats.getSum() - min - max) / (count - 2);
            System.out.println("Trimmed mean (without one min and one max): " + trimmedMean);
        }
    }

    private static List<Path> findMeasurementFiles(Path root) throws IOException {
        try (Stream<Path> paths = Files.walk(root)) {
            return paths
                    .filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
                    .filter(Task1Statistics::isMeasurementFile)
                    .sorted(Comparator.comparing(Path::toString))
                    .toList();
        } catch (UncheckedIOException exception) {
            throw new IOException("Failed while traversing directory: " + root, exception.getCause());
        }
    }

    private static boolean isMeasurementFile(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return name.startsWith("log") && name.endsWith(".txt");
    }

    private static void readMeasurements(Path file, List<Double> destination) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            var iterator = lines.iterator();
            int lineNumber = 0;
            while (iterator.hasNext()) {
                String line = iterator.next();
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    destination.add(parseFiniteDouble(line.trim(), "value"));
                } catch (IllegalArgumentException exception) {
                    throw new IOException(file + ":" + lineNumber + ": " + exception.getMessage(), exception);
                }
            }
        } catch (UncheckedIOException exception) {
            throw new IOException("Failed to read file: " + file, exception.getCause());
        }
    }

    private static double parseFiniteDouble(String text, String name) {
        try {
            double value = Double.parseDouble(text);
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(name + " must be a finite number: " + text);
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(name + " must be a number: " + text, exception);
        }
    }
}