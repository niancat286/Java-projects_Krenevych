import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SparseCircularLists_7_13 {

    // Внутрішній клас циклічно пов'язаного списку для ненульових елементів розрідженої матриці
    static class CircularSparseList {

        static class Node {
            int row;
            int col;
            double value;
            Node next;

            Node(int row, int col, double value) {
                this.row = row;
                this.col = col;
                this.value = value;
            }
        }

        private Node tail; // tail.next завжди вказує на head
        private int size;

        public boolean isEmpty() {
            return tail == null;
        }

        public int size() {
            return size;
        }

        public Node getHead() {
            return (tail == null) ? null : tail.next;
        }

        // Додавання ненульового елемента в кінець циклічного списку
        public void add(int row, int col, double value) {
            if (Math.abs(value) < 1e-12) {
                return; // нульові елементи не додаємо
            }
            Node newNode = new Node(row, col, value);
            if (tail == null) {
                tail = newNode;
                tail.next = tail; // замикаємо сам на себе
            } else {
                newNode.next = tail.next; // новий вузол вказує на старий head
                tail.next = newNode;    // старий tail вказує на новий вузол
                tail = newNode;         // оновлюємо tail
            }
            size++;
        }

        // Повертає всі вузли списку у звичайному списку для зручного перегляду
        public List<Node> toList() {
            List<Node> result = new ArrayList<>();
            if (tail == null) {
                return result;
            }
            Node head = tail.next;
            Node current = head;
            do {
                result.add(current);
                current = current.next;
            } while (current != head);
            return result;
        }

        @Override
        public String toString() {
            if (tail == null) {
                return "[]";
            }
            StringBuilder sb = new StringBuilder("[");
            Node head = tail.next;
            Node current = head;
            do {
                sb.append(String.format(Locale.ROOT, "(%d, %d) = %.2f", current.row, current.col, current.value));
                current = current.next;
                if (current != head) {
                    sb.append(" -> ");
                }
            } while (current != head);
            sb.append(" -> (початок)]");
            return sb.toString();
        }
    }

    static class SparseMatrix {
        final int rows;
        final int cols;
        final CircularSparseList elements;

        SparseMatrix(int rows, int cols, CircularSparseList elements) {
            this.rows = rows;
            this.cols = cols;
            this.elements = elements;
        }

        // Сума двох розріджених матриць S = A + B шляхом перегляду списків
        static SparseMatrix add(SparseMatrix a, SparseMatrix b) {
            if (a.rows != b.rows || a.cols != b.cols) {
                throw new IllegalArgumentException("Розміри матриць A та B не збігаються для додавання.");
            }

            double[][] sumGrid = new double[a.rows][a.cols];

            for (CircularSparseList.Node nodeA : a.elements.toList()) {
                sumGrid[nodeA.row][nodeA.col] += nodeA.value;
            }
            for (CircularSparseList.Node nodeB : b.elements.toList()) {
                sumGrid[nodeB.row][nodeB.col] += nodeB.value;
            }

            CircularSparseList resultList = new CircularSparseList();
            for (int r = 0; r < a.rows; r++) {
                for (int c = 0; c < a.cols; c++) {
                    if (Math.abs(sumGrid[r][c]) >= 1e-12) {
                        resultList.add(r, c, sumGrid[r][c]);
                    }
                }
            }
            return new SparseMatrix(a.rows, a.cols, resultList);
        }

        // Добуток
        static SparseMatrix multiply(SparseMatrix a, SparseMatrix b) {
            if (a.cols != b.rows) {
                throw new IllegalArgumentException("Кількість стовпців A має дорівнювати кількості рядків B.");
            }

            double[][] prodGrid = new double[a.rows][b.cols];
            List<CircularSparseList.Node> listA = a.elements.toList();
            List<CircularSparseList.Node> listB = b.elements.toList();

            for (CircularSparseList.Node nodeA : listA) {
                for (CircularSparseList.Node nodeB : listB) {
                    if (nodeA.col == nodeB.row) {
                        prodGrid[nodeA.row][nodeB.col] += nodeA.value * nodeB.value;
                    }
                }
            }

            CircularSparseList resultList = new CircularSparseList();
            for (int r = 0; r < a.rows; r++) {
                for (int c = 0; c < b.cols; c++) {
                    if (Math.abs(prodGrid[r][c]) >= 1e-12) {
                        resultList.add(r, c, prodGrid[r][c]);
                    }
                }
            }
            return new SparseMatrix(a.rows, b.cols, resultList);
        }

        void printDense(String title) {
            System.out.println(title + " (" + rows + "x" + cols + "):");
            double[][] dense = new double[rows][cols];
            for (CircularSparseList.Node node : elements.toList()) {
                dense[node.row][node.col] = node.value;
            }
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    System.out.printf(Locale.ROOT, "%8.2f", dense[r][c]);
                }
                System.out.println();
            }
        }
    }

    // Створює приклад вхідного файлу, якщо його ще немає
    private static void createSampleFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("3 3\n");
            writer.write("0 2 5.0\n");
            writer.write("1 0 -3.0\n");
            writer.write("\n");
            writer.write("3 3\n");
            writer.write("0 2 2.0\n");
            writer.write("2 1 4.0\n");
        }
    }

    // Зчитує одну розріджену матрицю з потоку рядків
    private static SparseMatrix readNextMatrix(List<String> nonEmptyLines, int[] indexRef) {
        if (indexRef[0] >= nonEmptyLines.size()) {
            throw new IllegalArgumentException("Недостатньо даних у файлі для зчитування матриці.");
        }

        String[] dims = nonEmptyLines.get(indexRef[0]++).trim().split("\\s+");
        int rows = Integer.parseInt(dims[0]);
        int cols = Integer.parseInt(dims[1]);
        CircularSparseList list = new CircularSparseList();

        while (indexRef[0] < nonEmptyLines.size()) {
            String line = nonEmptyLines.get(indexRef[0]).trim();
            String[] parts = line.split("\\s+");
            // Якщо бачимо рівно 2 числа і поточний список уже не порожній — це початок наступної матриці
            if (parts.length == 2 && !list.isEmpty()) {
                break;
            }
            indexRef[0]++;
            if (parts.length == 3) {
                int r = Integer.parseInt(parts[0]);
                int c = Integer.parseInt(parts[1]);
                double val = Double.parseDouble(parts[2]);
                list.add(r, c, val);
            }
        }

        return new SparseMatrix(rows, cols, list);
    }

    public static void main(String[] args) {
        String inputFile = "matrices.txt";

        try {
            createSampleFileIfNotExists(inputFile);

            List<String> nonEmptyLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        nonEmptyLines.add(line.trim());
                    }
                }
            }

            int[] idx = {0};
            SparseMatrix matrixA = readNextMatrix(nonEmptyLines, idx);
            SparseMatrix matrixB = readNextMatrix(nonEmptyLines, idx);

            System.out.println("Циклічний список CA (матриця A): " + matrixA.elements);
            System.out.println("Циклічний список CB (матриця B): " + matrixB.elements);
            System.out.println();

            // а) Сума S = A + B
            SparseMatrix sumS = SparseMatrix.add(matrixA, matrixB);
            System.out.println("а) Циклічний список суми S = A + B: " + sumS.elements);
            sumS.printDense("Матриця S = A + B");
            System.out.println();

            // б) Добуток P = A * B
            SparseMatrix prodP = SparseMatrix.multiply(matrixA, matrixB);
            System.out.println("б) Циклічний список добутку P = A * B: " + prodP.elements);
            prodP.printDense("Матриця P = A * B");

        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Помилка обробки даних: " + e.getMessage());
        }
    }
}