import java.util.Scanner;


public class Task06 {
    public static double findPerimeter(double a, double b, double c) {
        return a + b + c;
    }

    public static double findArea(double a, double b, double c) {
        double p = (a + b + c) / 2.0; // півпериметр
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public static void main(String[] args) {
        double a = 3.0;

        // 2^(-111) записується через Math.pow(2.0, -111)
        double b = 3.5 + 3.0 * Math.pow(2.0, -111);
        //double b = 3.5;
        double c = b;

        // Рахуємо результат за допомогою функцій
        double perimeter = findPerimeter(a, b, c);
        double area = findArea(a, b, c);

        // Виводимо отримані значення
        System.out.println("Сторона a: " + a);
        System.out.println("Сторона b: " + b);
        System.out.println("Сторона c: " + c);
        System.out.println("Периметр трикутника: " + perimeter);
        System.out.println("Площа трикутника: " + area);
    }
}
