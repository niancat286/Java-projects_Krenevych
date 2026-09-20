import java.util.Scanner;

///Напишіть функцію, яка приймає у якості першого аргументу число яке представляє довжину,
/// а якості другого --- величину виміру як рядок вигляду "мм", "см", "км" і т.п.
/// Функція повинна правильно виводити довжину в метрах.

public class Task02_05_pers {
    public static void convert(double value, String unit) {
        double inMeters;

        switch (unit.toLowerCase().trim()) {
            case "мм":
                inMeters = value / 1000.0;
                break;
            case "см":
                inMeters = value / 100.0;
                break;
            case "дм":
                inMeters = value / 10.0;
                break;
            case "м":
                inMeters = value;
                break;
            case "км":
                inMeters = value * 1000.0;
                break;
            default:
                System.out.println("Невідома одиниця виміру: " + unit);
                return;
        }

        System.out.println(value + " " + unit + " = " + inMeters + " м");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть довжину та одиницю виміру (наприклад, 150 см): ");
        double length = scanner.nextDouble();
        String unit = scanner.next();

        convert(length, unit);

        scanner.close();
    }

}
