package quad;

public class Main {
    public static void main(String[] args) {
        Quadratic[] list = {
                new Quadratic(1.0, -5.0, 6.0),    // корені: 2.0, 3.0
                new Quadratic(2.0, -8.0),         // 2x^2 - 8 = 0 -> корені: -2.0, 2.0
                new Quadratic(2.0, 1.0, true),    // x^2 + 2x + 1 = 0 -> корінь: -1.0
                new Quadratic(1.0, 1.0, 5.0)      // D < 0 (коренів немає)
        };

        EqArray repo = new EqArray(list);
        System.out.println("=== Аналіз квадратних рівнянь ===");
        repo.printAll();

        System.out.println("\n--- Результати пошуку екстремальних коренів ---");
        Double min = repo.findMinRoot();
        Double max = repo.findMaxRoot();

        System.out.println("Найменший дійсний корінь: " + (min != null ? String.format("%.3f", min) : "немає"));
        System.out.println("Найбільший дійсний корінь: " + (max != null ? String.format("%.3f", max) : "немає"));
    }
}
