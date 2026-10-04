import java.util.Scanner;

public class Task05 {
    public static double evaluatePolynomial(double x) {
        double t = 2 * x;
        return (((t + 1) * t + 1) * t + 1) * t + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть значення x: ");
        double x = scanner.nextDouble();
        double y = evaluatePolynomial(x);
        System.out.println("y = " + y);
    }
}
