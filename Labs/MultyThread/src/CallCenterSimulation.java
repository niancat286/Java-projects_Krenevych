import java.util.Random;

public class CallCenterSimulation {

    // Клас, що моделює роботу кол-центру та управляє зайнятістю операторів
    static class CallCenter {
        private final int totalOperators;
        private int busyOperators = 0;

        public CallCenter(int totalOperators) {
            this.totalOperators = totalOperators;
        }

        // Спроба зайняти оператора. Повертає true, якщо є вільний, і false, якщо всі зайняті.
        public synchronized boolean occupyOperator() {
            if (busyOperators < totalOperators) {
                busyOperators++;
                return true;
            }
            return false;
        }

        // Звільнення оператора після завершення обслуговування
        public synchronized void releaseOperator() {
            busyOperators--;
        }
    }

    // Клас клієнта (1 клієнт = 1 потік)
    static class ClientThread extends Thread {
        private final int clientId;
        private final CallCenter callCenter;
        private final Random random = new Random();

        public ClientThread(int clientId, CallCenter callCenter) {
            this.clientId = clientId;
            this.callCenter = callCenter;
        }

        @Override
        public void run() {
            try {
                // Час появи клієнта (випадковий від t1 до t2, наприклад, 200..800 мс)
                int t1 = 200;
                int t2 = 800;
                int arrivalDelay = t1 + random.nextInt(t2 - t1 + 1);
                Thread.sleep(arrivalDelay);

                System.out.println("Клієнт " + clientId + " набирає номер кол-центру...");

                while (true) {
                    boolean served = false;

                    // Намагаємося отримати вільнного оператора в синхронізованому блоці
                    synchronized (callCenter) {
                        if (callCenter.occupyOperator()) {
                            served = true;
                        }
                    }

                    if (served) {
                        // Якщо оператор вільний — починається розмова (від t3 до t4)
                        int t3 = 1000;
                        int t4 = 2500;
                        int serviceTime = t3 + random.nextInt(t4 - t3 + 1);

                        System.out.println("--> Оператор узяв дзвінок Клієнта " + clientId + " (розмова триватиме " + serviceTime + " мс)");
                        Thread.sleep(serviceTime);

                        // Звільняємо оператора після закінчення розмови
                        callCenter.releaseOperator();
                        System.out.println("<-- Клієнт " + clientId + " завершив розмову і поклав трубку.");
                        break; // Клієнт успішно обслуговуваний, потік завершує роботу
                    } else {
                        // Якщо всі оператори зайняті — клієнт кладе трубку і передзвонює пізніше (від t5 до t6)
                        System.out.println("--- Усі оператори зайняті! Клієнт " + clientId + " кладе трубку.");

                        int t5 = 1500;
                        int t6 = 3000;
                        int retryDelay = t5 + random.nextInt(t6 - t5 + 1);

                        System.out.println("... Клієнт " + clientId + " передзвонить через " + retryDelay + " мс.");
                        Thread.sleep(retryDelay);

                        System.out.println("Клієнт " + clientId + " намагається передзвонити знову...");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Потік клієнта " + clientId + " було перервано.");
            }
        }
    }

    public static void main(String[] args) {
        int N = 2; // Кількість операторів у кол-центрі
        int totalClients = 5; // Загальна кількість клієнтів (потоків)

        CallCenter callCenter = new CallCenter(N);

        System.out.println("=== Старт симуляції кол-центру (Операторів: " + N + ") ===\n");

        // Створюємо та запускаємо кожного клієнта в окремому потоці
        Thread[] clientThreads = new Thread[totalClients];
        for (int i = 1; i <= totalClients; i++) {
            clientThreads[i - 1] = new ClientThread(i, callCenter);
            clientThreads[i - 1].start();
        }

        // Головний потік чекає завершення всіх клієнтів
        for (int i = 0; i < totalClients; i++) {
            try {
                clientThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n=== Симуляцію кол-центру успішно завершено ===");
    }
}