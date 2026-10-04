import java.util.Scanner;

public class Task04 {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        System.out.println("Input x:");

        double x = scan.nextDouble();
        double y = x * x;
        y *= y;
        y *= y;

        System.out.printf("y = %20.4f", y);
    }
}
