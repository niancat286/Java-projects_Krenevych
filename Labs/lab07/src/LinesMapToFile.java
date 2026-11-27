import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class LinesMapToFile {

    record Point(int x, int y) {
    }

    static class Line {
        final BigInteger a;
        final BigInteger b;
        final BigInteger c;

        Line(BigInteger a, BigInteger b, BigInteger c) {
            // Скорочуємо на нсд
            BigInteger g = a.gcd(b).gcd(c);
            if (!g.equals(BigInteger.ZERO)) {
                a = a.divide(g);
                b = b.divide(g);
                c = c.divide(g);
            }

            // Фіксуємо знак першого ненульового коефіцієнта
            if (a.compareTo(BigInteger.ZERO) < 0
                    || (a.equals(BigInteger.ZERO) && b.compareTo(BigInteger.ZERO) < 0)
                    || (a.equals(BigInteger.ZERO) && b.equals(BigInteger.ZERO) && c.compareTo(BigInteger.ZERO) < 0)) {
                a = a.negate();
                b = b.negate();
                c = c.negate();
            }

            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return a + "x + " + b + "y + " + c + " = 0";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Line line = (Line) obj;
            return a.equals(line.a) && b.equals(line.b) && c.equals(line.c);
        }

        @Override
        public int hashCode() {
            int result = a.hashCode();
            result = 31 * result + b.hashCode();
            result = 31 * result + c.hashCode();
            return result;
        }
    }

    public static void main(String[] args) {
        String outputFile = "lines_output.txt";
        List<Point> points = new ArrayList<>();

        System.out.println("Введіть кількість точок N, а потім координати x та y для кожної точки:");
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Некоректне введення кількості точок.");
                return;
            }
            int n = scanner.nextInt();
            if (n <= 0) {
                System.out.println("Кількість точок має бути додатною.");
                return;
            }

            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                points.add(new Point(x, y));
            }
        }

        // ключ - пряма Line, значення - множина точок, що лежать на цій прямій
        Map<Line, Set<Point>> linesMap = new HashMap<>();

        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);

                if (p1.equals(p2)) {
                    continue;
                }

                BigInteger x1 = BigInteger.valueOf(p1.x());
                BigInteger y1 = BigInteger.valueOf(p1.y());
                BigInteger x2 = BigInteger.valueOf(p2.x());
                BigInteger y2 = BigInteger.valueOf(p2.y());

                // Рівняння прямої через дві точки: (y1 - y2)x + (x2 - x1)y + (x1*y2 - x2*y1) = 0
                BigInteger a = y1.subtract(y2);
                BigInteger b = x2.subtract(x1);
                BigInteger c = x1.multiply(y2).subtract(x2.multiply(y1));

                Line line = new Line(a, b, c);
                Set<Point> linePoints = linesMap.computeIfAbsent(line, k -> new HashSet<>());
                linePoints.add(p1);
                linePoints.add(p2);
            }
        }

        // Записуємо у файл усі прямі, які проходять більш ніж через одну точку
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(outputFile, StandardCharsets.UTF_8))) {

            for (Map.Entry<Line, Set<Point>> entry : linesMap.entrySet()) {
                int pointCount = entry.getValue().size();
                if (pointCount > 1) {
                    writer.write("Пряма: " + entry.getKey() + " | Кількість точок: " + pointCount);
                    writer.newLine();
                }
            }

            System.out.println("Результат збережено у файл: " + outputFile);
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }
}