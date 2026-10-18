package flowershop;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Flower[] flowers = {
                new Rose(90.0, 3, 60.0),
                new Tulip(45.0, 1, 35.0),
                new Chamomile(30.0, 2, 25.0),
                new Rose(110.0, 1, 70.0),
                new Tulip(40.0, 4, 30.0)
        };

        Accessory[] accessories = {
                new Accessory("Папір", 25.0),
                new Accessory("Стрічка", 15.0)
        };

        Bouquet bouquet = new Bouquet(flowers, accessories);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути букет і вартість");
            System.out.println("2. Відсортувати квіти за свіжістю");
            System.out.println("3. Знайти квітку за довжиною стебла");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    bouquet.printBouquet();
                    break;
                case "2":
                    bouquet.sortFresh();
                    System.out.println("Квіти відсортовано за свіжістю (від найсвіжіших):");
                    bouquet.printBouquet();
                    break;
                case "3":
                    System.out.print("Введіть мінімальну довжину стебла (см): ");
                    double min = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Введіть максимальну довжину стебла (см): ");
                    double max = Double.parseDouble(scanner.nextLine().trim());
                    System.out.println("Результати пошуку:");
                    bouquet.findLen(min, max);
                    break;
                case "0":
                    System.out.println("Вихід");
                    running = false;
                    break;
                default:
                    System.out.println("Невідома команда, спробуйте ще раз.");
            }
        }
    }


}
