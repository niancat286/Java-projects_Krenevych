package jewelry;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Формуємо набір каменів для намиста
        Gem[] stones = {
                new PreciousStone("Діамант", 1.5, 60000.0, 0.98, "VVS1"),
                new PreciousStone("Смарагд", 2.2, 35000.0, 0.85, "VS1"),
                new SemipreciousStone("Аметист", 4.0, 1200.0, 0.70, "Фіолетовий глибокий"),
                new SemipreciousStone("Топаз", 3.5, 2500.0, 0.80, "Блакитний димчастий"),
                new SemipreciousStone("Бурштин", 6.0, 800.0, 0.35, "Медовий")
        };

        Necklace necklace = new Necklace(stones);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Ювелірна майстерня: Намисто зібрано ===");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути склад намиста, загальну вагу та вартість");
            System.out.println("2. Відсортувати камені за спаданням цінності");
            System.out.println("3. Знайти камені за діапазоном прозорості");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    necklace.printNecklace();
                    break;
                case "2":
                    necklace.sortByValueDescending();
                    System.out.println("Камені відсортовано за спаданням вартості:");
                    necklace.printNecklace();
                    break;
                case "3":
                    System.out.print("Введіть мінімальну прозорість (0.0 - 1.0): ");
                    double min = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Введіть максимальну прозорість (0.0 - 1.0): ");
                    double max = Double.parseDouble(scanner.nextLine().trim());
                    System.out.println("Камені, що відповідають критерію:");
                    necklace.printStonesByTransparency(min, max);
                    break;
                case "0":
                    System.out.println("Роботу завершено.");
                    running = false;
                    break;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
    }
}
