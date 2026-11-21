
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Імітує роботу ресторану з кількома касами: відвідувачі стають
 * у найкоротшу чергу, а потім можуть перейти до ще коротшої.
 * Спільні черги та стани відвідувачів захищені одним монітором.
 */
public class Shop {

    // Кількість касирів і відвідувачів у цій симуляції.
    private static final int CASHIER_COUNT = 3;
    private static final int VISITOR_COUNT = 15;

    // Мінімальна та максимальна пауза між появами відвідувачів (мс).
    private static final int ARRIVAL_MIN = 300;
    private static final int ARRIVAL_MAX = 1000;

    // Мінімальна та максимальна тривалість обслуговування (мс).
    private static final int SERVICE_MIN = 1000;
    private static final int SERVICE_MAX = 4000;

    // Єдиний монітор для узгодженого доступу до черг і станів відвідувачів.
    private static final Object lock = new Object();

    // Окрема FIFO-черга кожної каси; індекс у списку відповідає індексу касира.
    private static final List<Deque<Visitor>> queues =
            new ArrayList<>();

    // Стає false після появи останнього відвідувача, щоб касири завершили роботу.
    private static volatile boolean running = true;

    // Етап обробки відвідувача: очікує, обслуговується або вже обслужений.
    enum State {
        WAITING,
        SERVING,
        DONE
    }

    /**
     * Потік окремого відвідувача. Він приєднується до найкоротшої черги
     * та, поки очікує, періодично перевіряє, чи не з'явилася коротша.
     */
    static class Visitor extends Thread {

        // Номер відвідувача для повідомлень у консолі.
        private final int id;

        // Індекс каси, у черзі якої наразі стоїть відвідувач.
        private int cashierIndex;

        // Поточний етап обробки; зміни виконуються під блокуванням lock.
        private State state = State.WAITING;

        // Створює потік відвідувача з заданим номером.
        Visitor(int id) {
            this.id = id;
        }

        // Додає відвідувача до черги та чекає, доки касир не почне обслуговування.
        @Override
        public void run() {
            try {
                synchronized (lock) {

                    // Приєднання до найкоротшої черги відбувається атомарно під lock.
                    cashierIndex = findShortestQueue();

                    queues.get(cashierIndex).addLast(this);

                    System.out.println(
                            "Відвідувач " + id
                            + " став у чергу до каси "
                            + (cashierIndex + 1)
                    );

                    // Поки касир не забрав відвідувача, можна перейти в коротшу чергу.
                    while (state == State.WAITING) {

                        // Якщо інша черга стала строго коротшою, переходимо в її кінець.
                        int bestQueue = findShortestQueue();

                        if (queues.get(bestQueue).size()
                                < queues.get(cashierIndex).size()) {

                            queues.get(cashierIndex).remove(this);

                            cashierIndex = bestQueue;

                            queues.get(cashierIndex).addLast(this);

                            System.out.println(
                                    "Відвідувач " + id
                                    + " перейшов до каси "
                                    + (cashierIndex + 1)
                            );

                            lock.notifyAll();
                        }

                        // wait тимчасово відпускає lock; тайм-аут дає змогу повторити перевірку.
                        if (state == State.WAITING) {
                            lock.wait(200);
                        }
                    }
                }

            } catch (InterruptedException e) {
                // Зберігаємо ознаку переривання, щоб її міг обробити код, який викликав потік.
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Потік касира обслуговує відвідувачів своєї черги по одному.
     * Очікування та зміни спільного стану виконуються під lock,
     * а затримка обслуговування — поза ним.
     */
    static class Cashier extends Thread {

        // Індекс касира, який також визначає його чергу у списку queues.
        private final int id;

        // Створює касира з індексом його черги.
        Cashier(int id) {
            this.id = id;
        }

        // Послідовно забирає відвідувачів із черги та обслуговує їх до завершення.
        @Override
        public void run() {

            while (true) {

                Visitor visitor;

                synchronized (lock) {

                    // Якщо черга порожня, засинаємо до появи відвідувача або завершення симуляції.
                    while (queues.get(id).isEmpty() && running) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            // На переривання потік касира завершується, зберігши прапорець.
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    // Після завершення надходження відвідувачів порожня черга означає кінець роботи.
                    if (queues.get(id).isEmpty() && !running) {
                        return;
                    }

                    // Забираємо першого у FIFO-черзі та позначаємо початок його обслуговування.
                    visitor = queues.get(id).removeFirst();

                    visitor.state = State.SERVING;

                    System.out.println(
                            "Каса " + (id + 1)
                            + " почала обслуговувати відвідувача "
                            + visitor.id
                    );

                    lock.notifyAll();
                }

                // Не утримуємо спільний lock під час затримки, щоб інші потоки могли працювати.
                int serviceTime = ThreadLocalRandom.current()
                        .nextInt(SERVICE_MIN, SERVICE_MAX + 1);

                try {
                    Thread.sleep(serviceTime);
                } catch (InterruptedException e) {
                    // Перерване обслуговування припиняє роботу цього касира.
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (lock) {

                    // Фіксуємо завершення обслуговування та сповіщаємо потоки, що чекають на зміни.
                    visitor.state = State.DONE;

                    System.out.println(
                            "Каса " + (id + 1)
                            + " завершила обслуговування відвідувача "
                            + visitor.id
                    );

                    lock.notifyAll();
                }
            }
        }
    }

    /**
     * Повертає індекс каси з найкоротшою чергою.
     * Якщо довжини однакові, обирається каса з найменшим індексом.
     */
    private static int findShortestQueue() {

        // Починаємо з першої каси й замінюємо вибір лише на строго коротшу чергу.
        int best = 0;

        for (int i = 1; i < CASHIER_COUNT; i++) {

            if (queues.get(i).size() < queues.get(best).size()) {
                best = i;
            }
        }

        return best;
    }

    // Ініціалізує черги та потоки, моделює прихід відвідувачів і завершує касирів.
    public static void main(String[] args)
            throws InterruptedException {

        // Створюємо по одній порожній черзі на кожну касу.
        for (int i = 0; i < CASHIER_COUNT; i++) {
            queues.add(new LinkedList<>());
        }

        // Запускаємо касирів до появи відвідувачів, щоб вони могли чекати на свою чергу.
        Cashier[] cashiers = new Cashier[CASHIER_COUNT];

        for (int i = 0; i < CASHIER_COUNT; i++) {
            cashiers[i] = new Cashier(i);
            cashiers[i].start();
        }

        // Створюємо відвідувачів із випадковими інтервалами між їхніми приходами.
        Visitor[] visitors = new Visitor[VISITOR_COUNT];

        for (int i = 0; i < VISITOR_COUNT; i++) {

            int arrivalTime = ThreadLocalRandom.current()
                    .nextInt(ARRIVAL_MIN, ARRIVAL_MAX + 1);

            Thread.sleep(arrivalTime);

            visitors[i] = new Visitor(i + 1);
            visitors[i].start();
        }

        // Дочікуємося, поки кожен відвідувач потрапить на обслуговування.
        for (Visitor visitor : visitors) {
            visitor.join();
        }

        // Сигналізуємо касирам, що нових відвідувачів не буде; вони дороблять наявні черги.
        synchronized (lock) {
            running = false;
            lock.notifyAll();
        }

        // Дочікуємося, доки кожна каса завершить обслуговування своєї черги.
        for (Cashier cashier : cashiers) {
            cashier.join();
        }

        // Повідомляємо про повне завершення симуляції.
        System.out.println("Роботу ресторану завершено.");
    }
}
