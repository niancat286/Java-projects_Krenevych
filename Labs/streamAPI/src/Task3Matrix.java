import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class Task3Matrix {
    private record Cell(int row, int column, double value) {}

    public static void main(String[] args) throws IOException {
        Path file;
        double scalar;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter path to .bin matrix file: ");
            String fileInput = scanner.nextLine().trim();
            if (fileInput.isEmpty()) {
                throw new IllegalArgumentException("File path must not be empty.");
            }
            file = Path.of(fileInput);

            System.out.print("Enter scalar k: ");
            String kInput = scanner.nextLine().trim();
            scalar = parseFiniteDouble(kInput, "k");
        }

        double[][] matrix = readMatrix(file);

        double[] values = Arrays.stream(matrix)
                .flatMapToDouble(Arrays::stream)
                .toArray();

        DoubleSummaryStatistics statistics = Arrays.stream(values)
                .summaryStatistics();

        long positive = Arrays.stream(values)
                .filter(v -> v > 0.0)
                .count();

        long negative = Arrays.stream(values)
                .filter(v -> v < 0.0)
                .count();

        long zero = Arrays.stream(values)
                .filter(v -> v == 0.0)
                .count();

        double[] rowSums = Arrays.stream(matrix)
                .mapToDouble(row -> Arrays.stream(row).sum())
                .toArray();

        Cell largest = findMaxAbsCell(matrix);

        double[][] scaled = Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .map(value -> value * scalar)
                        .toArray())
                .toArray(double[][]::new);

        System.out.println("Sum: " + statistics.getSum());
        System.out.println("Mean: " + statistics.getAverage());
        System.out.println("Positive values: " + positive + ", negative values: " + negative
                + ", zero values: " + zero);
        System.out.println("Row sums: " + Arrays.toString(rowSums));
        System.out.println("Element with the greatest absolute value: " + largest.value()
                + " at position (" + largest.row() + ", " + largest.column() + ")");
        System.out.println("Matrix multiplied by " + scalar + ":");
        Arrays.stream(scaled).forEach(row -> System.out.println(Arrays.toString(row)));
        System.out.println("Elements in ascending order: "
                + Arrays.toString(Arrays.stream(values).sorted().toArray()));
    }

    private static Cell findMaxAbsCell(double[][] matrix) {
        Cell best = new Cell(0, 0, matrix[0][0]);
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                double current = matrix[row][column];
                if (Math.abs(current) > Math.abs(best.value())) {
                    best = new Cell(row, column, current);
                }
            }
        }
        return best;
    }

    private static double[][] readMatrix(Path file) throws IOException {
        try (DataInputStream input = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(file)))) {
            int rows;
            int columns;
            try {
                rows = input.readInt();
                columns = input.readInt();
            } catch (EOFException exception) {
                throw new IOException("Invalid matrix file: dimensions are missing: " + file, exception);
            }
            if (rows <= 0 || columns <= 0) {
                throw new IOException("The row and column counts must be positive: " + file);
            }
            long expectedSize;
            try {
                long elementCount = Math.multiplyExact((long) rows, columns);
                long dataSize = Math.multiplyExact(elementCount, Double.BYTES);
                expectedSize = Math.addExact(2L * Integer.BYTES, dataSize);
            } catch (ArithmeticException exception) {
                throw new IOException("Matrix dimensions are too large: " + file, exception);
            }
            if (Files.size(file) != expectedSize) {
                throw new IOException("File size does not match the declared matrix dimensions: " + file);
            }

            double[][] matrix = new double[rows][columns];
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    double value = input.readDouble();
                    if (!Double.isFinite(value)) {
                        throw new IOException("Non-finite value at position ("
                                + row + ", " + column + ") in file: " + file);
                    }
                    matrix[row][column] = value;
                }
            }
            return matrix;
        } catch (EOFException exception) {
            throw new IOException("Unexpected end of matrix file: " + file, exception);
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