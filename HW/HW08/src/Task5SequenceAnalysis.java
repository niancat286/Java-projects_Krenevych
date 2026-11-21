import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Task5SequenceAnalysis {

    private record Extremum(int index, double value) {
        @Override
        public String toString() {
            return String.format(Locale.ROOT, "index %d (value = %s)", index, value);
        }
    }

    private record IncreasingSegment(int startIndex, int endIndex, List<Double> elements) {
        int length() {
            return elements.size();
        }
    }

    public static void main(String[] args) {
        double[] sequence;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter non-empty sequence of real numbers separated by spaces:");
            String line = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            if (line.isEmpty()) {
                throw new IllegalArgumentException("Sequence must not be empty.");
            }
            sequence = Arrays.stream(line.split("\\s+"))
                    .mapToDouble(token -> parseFiniteDouble(token, "element"))
                    .toArray();
        }

        if (sequence.length == 0) {
            throw new IllegalArgumentException("Sequence must not be empty.");
        }

        List<Extremum> localMaxima = IntStream.range(1, sequence.length - 1)
                .filter(i -> sequence[i] > sequence[i - 1] && sequence[i] > sequence[i + 1])
                .mapToObj(i -> new Extremum(i, sequence[i]))
                .toList();

        List<Extremum> localMinima = IntStream.range(1, sequence.length - 1)
                .filter(i -> sequence[i] < sequence[i - 1] && sequence[i] < sequence[i + 1])
                .mapToObj(i -> new Extremum(i, sequence[i]))
                .toList();

        long upToDownTransitions = localMaxima.size();
        long downToUpTransitions = localMinima.size();

        IncreasingSegment longestIncreasing = findLongestStrictlyIncreasingSegment(sequence);

        System.out.println("Sequence length: " + sequence.length);
        System.out.println("Local maxima: " + localMaxima);
        System.out.println("Local minima: " + localMinima);
        System.out.println("Transitions (strictly increasing -> strictly decreasing): " + upToDownTransitions);
        System.out.println("Transitions (strictly decreasing -> strictly increasing): " + downToUpTransitions);
        System.out.println("Longest strictly increasing segment:");
        System.out.println("  Start index: " + longestIncreasing.startIndex());
        System.out.println("  End index: " + longestIncreasing.endIndex());
        System.out.println("  Length: " + longestIncreasing.length());
        System.out.println("  Elements: " + longestIncreasing.elements());
    }

    private static IncreasingSegment findLongestStrictlyIncreasingSegment(double[] sequence) {
        int bestStart = 0;
        int bestEnd = 0;
        int bestLength = 1;

        int currentStart = 0;
        int currentLength = 1;

        for (int i = 1; i < sequence.length; i++) {
            if (sequence[i] > sequence[i - 1]) {
                currentLength++;
            } else {
                if (currentLength > bestLength) {
                    bestLength = currentLength;
                    bestStart = currentStart;
                    bestEnd = i - 1;
                }
                currentStart = i;
                currentLength = 1;
            }
        }

        if (currentLength > bestLength) {
            bestLength = currentLength;
            bestStart = currentStart;
            bestEnd = sequence.length - 1;
        }

        List<Double> elements = new ArrayList<>(bestLength);
        for (int i = bestStart; i <= bestEnd; i++) {
            elements.add(sequence[i]);
        }

        return new IncreasingSegment(bestStart, bestEnd, elements);
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