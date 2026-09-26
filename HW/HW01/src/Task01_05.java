import java.util.Scanner;

public class Task01_05 {

    // 1. y = x^4 + 2x^2 + 1 = (x^2 + 1)^2
    public static double poly1(double x) {
        double t = x * x + 1;
        return t * t;
    }

    // 2. y = x^4 + x^3 + x^2 + x + 1
    public static double poly2(double x) {
        return (((x + 1) * x + 1) * x + 1) * x + 1;
    }

    // 3. y = x^5 + 5x^4 + 10x^3 + 10x^2 + 5x + 1 = (x + 1)^5
    public static double poly3(double x) {
        double t = x + 1;
        double t2 = t * t;
        return t2 * t2 * t;
    }

    // 4. y = x^9 + x^3 + 1 = (t^2 + 1)*t + 1, де t = x^3
    public static double poly4(double x) {
        double t = x * x * x;
        return (t * t + 1) * t + 1;
    }

    // 5. y = 16x^4 + 8x^3 + 4x^2 + 2x + 1
    public static double poly5(double x) {
        double t = 2 * x;
        return (((t + 1) * t + 1) * t + 1) * t + 1;
    }

    // 6. y = x^5 + x^3 + x = x * ((x^2 + 1) * x^2 + 1)
    public static double poly6(double x) {
        double t = x * x;
        return x * ((t + 1) * t + 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input x: ");
        double x = scanner.nextDouble();

        System.out.println("1) x^4 + 2x^2 + 1 = " + poly1(x));
        System.out.println("2) x^4 + x^3 + x^2 + x + 1 = " + poly2(x));
        System.out.println("3) (x + 1)^5 = " + poly3(x));
        System.out.println("4) x^9 + x^3 + 1 = " + poly4(x));
        System.out.println("5) 16x^4 + 8x^3 + 4x^2 + 2x + 1 = " + poly5(x));
        System.out.println("6) x^5 + x^3 + x = " + poly6(x));
    }
}