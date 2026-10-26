import java.util.Scanner;

public class SymmetricStrings {

    public static boolean isSymmetric(String s) {
        if (s == null || s.isEmpty()) return false;

        int length = s.length();
        for (int i = 0; i < length / 2; i++) {
            if (s.charAt(i) != s.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasTwoSymmetricParts(String s) {
        for (int i = 1; i < s.length(); i++) {
            String leftPart = s.substring(0, i);   // ліва частина
            String rightPart = s.substring(i);     // права частина

            // Якщо обидві частини симетричні
            if (isSymmetric(leftPart) && isSymmetric(rightPart)) {
                System.out.println("  Знайдено розбиття: [" + leftPart + "] та [" + rightPart + "]");
                return true;
            }
        }
        return false;
    }

    public static boolean hasNSymmetricParts(String s, int n) {
        if (n == 0) {
            return s.isEmpty();
        }
        if (s.isEmpty()) {
            return false;
        }

        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);

            if (isSymmetric(prefix)) {
                // рекурсія для залишку рядка
                String remainingPart = s.substring(i);
                if (hasNSymmetricParts(remainingPart, n - 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        /*

        // Тестові рядки
        String test1 = "abbaдед";      // складається з 2-х: "abba" та "дед"
        String test2 = "казанannaпотоп"; // складається з 3-х: "казан", "anna", "потоп"
        String test3 = "hello";          // не складається з симетричних підрядків

        System.out.println("--- Перевірка на 2 підрядки ---");
        System.out.println("Рядок '" + test1 + "': " + hasTwoSymmetricParts(test1));
        System.out.println("Рядок '" + test3 + "': " + hasTwoSymmetricParts(test3));

        System.out.println("\n--- Перевірка на N підрядків ---");
        System.out.println("Рядок '" + test2 + "' (на 3 частини): " + hasNSymmetricParts(test2, 3));
        System.out.println("Рядок '" + test1 + "' (на 3 частини): " + hasNSymmetricParts(test1, 3));
         */

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть рядок для перевірки: ");
        String text = scanner.nextLine();

        System.out.print("Введіть кількість частин (n): ");
        int n = scanner.nextInt();

        System.out.println("Чи складається рядок з 2 симетричних підрядків? "
                + hasTwoSymmetricParts(text));

        System.out.println("Чи складається рядок з " + n + " симетричних підрядків? "
                + hasNSymmetricParts(text, n));
    }

}