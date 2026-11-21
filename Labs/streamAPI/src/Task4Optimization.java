import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task4Optimization {
    private record Solution(double x, double y, double objective) {}

    private static final Comparator<Solution> ORDER =
            Comparator.comparingDouble(Solution::objective)
                    .thenComparingDouble(Solution::x)
                    .thenComparingDouble(Solution::y);

    public static void main(String[] args) throws IOException {
        Path file;
        int limit;
        double threshold;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter path to CSV file: ");
            String fileInput = scanner.nextLine().trim();
            if (fileInput.isEmpty()) {
                throw new IllegalArgumentException("File path must not be empty.");
            }
            file = Path.of(fileInput);

            System.out.print("Enter positive integer K: ");
            limit = parseInt(scanner.nextLine().trim(), "K");

            System.out.print("Enter threshold L: ");
            threshold = parseFiniteDouble(scanner.nextLine().trim(), "L");
        }

        if (limit <= 0) {
            throw new IllegalArgumentException("K must be a positive integer.");
        }

        List<Solution> solutions = readSolutions(file);
        List<Solution> feasible = solutions.stream()
                .filter(solution -> solution.x() >= 0
                        && solution.y() >= 0
                        && solution.x() + solution.y() <= 1)
                .toList();

        Map<String, Long> counts = new LinkedHashMap<>();
        counts.put("objective < 0", 0L);
        counts.put("0 <= objective < 10", 0L);
        counts.put("10 <= objective < 100", 0L);
        counts.put("objective >= 100", 0L);

        Map<String, Long> groupedCounts = feasible.stream().collect(Collectors.groupingBy(
                (Solution solution) -> objectiveGroup(solution.objective()),
                LinkedHashMap::new,
                Collectors.counting()));
        counts.putAll(groupedCounts);

        System.out.println("Number of feasible candidates: " + feasible.size());
        feasible.stream().min(ORDER)
                .ifPresentOrElse(
                        solution -> System.out.println("Best candidate: " + solution),
                        () -> System.out.println("There are no feasible candidates."));
        System.out.println("Up to " + limit + " best candidates: "
                + feasible.stream().sorted(ORDER).limit(limit).toList());
        System.out.println("Number with objective <= " + threshold + ": "
                + feasible.stream().filter(solution -> solution.objective() <= threshold).count());
        System.out.println("Candidate counts by interval:");
        counts.forEach((group, count) -> System.out.println("  " + group + ": " + count));
    }

    private static List<Solution> readSolutions(Path file) throws IOException {
        AtomicInteger lineNumber = new AtomicInteger(0);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines
                    .map(line -> {
                        int currentLine = lineNumber.incrementAndGet();
                        if (line.isBlank()) {
                            return null;
                        }
                        String[] fields = line.split(",", -1);
                        if (currentLine == 1 && isHeader(fields)) {
                            return null;
                        }
                        if (fields.length != 3) {
                            throw new IllegalArgumentException(file + ":" + currentLine
                                    + ": expected exactly three CSV columns: x,y,objective.");
                        }
                        try {
                            return new Solution(
                                    parseFiniteDouble(fields[0].trim(), "x"),
                                    parseFiniteDouble(fields[1].trim(), "y"),
                                    parseFiniteDouble(fields[2].trim(), "objective"));
                        } catch (IllegalArgumentException exception) {
                            throw new IllegalArgumentException(
                                    file + ":" + currentLine + ": " + exception.getMessage(), exception);
                        }
                    })
                    .filter(solution -> solution != null)
                    .toList();
        } catch (UncheckedIOException exception) {
            throw new IOException("Failed to read CSV file: " + file, exception.getCause());
        }
    }

    private static boolean isHeader(String[] fields) {
        return fields.length == 3
                && fields[0].trim().equalsIgnoreCase("x")
                && fields[1].trim().equalsIgnoreCase("y")
                && fields[2].trim().equalsIgnoreCase("objective");
    }

    private static String objectiveGroup(double objective) {
        if (objective < 0) {
            return "objective < 0";
        }
        if (objective < 10) {
            return "0 <= objective < 10";
        }
        if (objective < 100) {
            return "10 <= objective < 100";
        }
        return "objective >= 100";
    }

    private static int parseInt(String text, String name) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(name + " must be an integer: " + text, exception);
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