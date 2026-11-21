import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class HotelSimulation {

    private static final int ROOM_COUNT = 3;
    private static final int CLIENT_COUNT = 10;

    private static final int ARRIVAL_MIN_MS = 200;
    private static final int ARRIVAL_MAX_MS = 700;

    private static final int STAY_MIN_MS = 1000;
    private static final int STAY_MAX_MS = 2500;

    static class Hotel {
        private final boolean[] occupied;
        private final Object lock = new Object();
        private final List<Long> waitDurationsNanos = new ArrayList<>();

        Hotel(int roomCount) {
            if (roomCount <= 0) {
                throw new IllegalArgumentException("Кількість номерів має бути додатною.");
            }
            this.occupied = new boolean[roomCount];
        }

        // Повертає індекс зайнятого номера  і записує час очікування
        public int checkIn(int clientId) throws InterruptedException {
            synchronized (lock) {
                long waitStart = System.nanoTime();
                int freeRoom = findFreeRoom();

                while (freeRoom == -1) {
                    System.out.println("Клієнт " + clientId + " чекає на вільний номер...");
                    lock.wait();
                    freeRoom = findFreeRoom();
                }

                long waitedNanos = System.nanoTime() - waitStart;
                waitDurationsNanos.add(waitedNanos);

                occupied[freeRoom] = true;
                double waitedMs = waitedNanos / 1_000_000.0;
                System.out.printf(Locale.ROOT,
                        "--> Клієнт %d заселився у номер %d (очікував %.1f мс)%n",
                        clientId, freeRoom + 1, waitedMs);
                return freeRoom;
            }
        }

        // Звільняє номер і будить потоки, які чекають на заселення
        public void checkOut(int clientId, int roomIndex) {
            synchronized (lock) {
                occupied[roomIndex] = false;
                System.out.println("<-- Клієнт " + clientId + " виселився і звільнив номер " + (roomIndex + 1));
                lock.notifyAll();
            }
        }

        public double getAverageWaitMillis() {
            synchronized (lock) {
                if (waitDurationsNanos.isEmpty()) {
                    return 0.0;
                }
                long totalNanos = 0;
                for (long nanos : waitDurationsNanos) {
                    totalNanos += nanos;
                }
                return (totalNanos / (double) waitDurationsNanos.size()) / 1_000_000.0;
            }
        }

        private int findFreeRoom() {
            for (int i = 0; i < occupied.length; i++) {
                if (!occupied[i]) {
                    return i;
                }
            }
            return -1;
        }
    }

    static class Client extends Thread {
        private final int clientId;
        private final Hotel hotel;

        Client(int clientId, Hotel hotel) {
            this.clientId = clientId;
            this.hotel = hotel;
        }

        @Override
        public void run() {
            try {
                int roomIndex = hotel.checkIn(clientId);

                int stayTime = ThreadLocalRandom.current()
                        .nextInt(STAY_MIN_MS, STAY_MAX_MS + 1);
                System.out.println("    Клієнт " + clientId + " живе в номері "
                        + (roomIndex + 1) + " (" + stayTime + " мс)");
                Thread.sleep(stayTime);

                hotel.checkOut(clientId, roomIndex);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Потік клієнта " + clientId + " перервано.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Hotel hotel = new Hotel(ROOM_COUNT);
        List<Client> clients = new ArrayList<>();

        System.out.println("=== Готель відкрито (номерів: " + ROOM_COUNT
                + ", клієнтів: " + CLIENT_COUNT + ") ===\n");

        for (int i = 1; i <= CLIENT_COUNT; i++) {
            int arrivalDelay = ThreadLocalRandom.current()
                    .nextInt(ARRIVAL_MIN_MS, ARRIVAL_MAX_MS + 1);
            Thread.sleep(arrivalDelay);

            System.out.println("Прийшов клієнт " + i + " (інтервал появи: " + arrivalDelay + " мс)");
            Client client = new Client(i, hotel);
            clients.add(client);
            client.start();
        }

        for (Client client : clients) {
            client.join();
        }

        System.out.printf(Locale.ROOT,
                "%n=== Усі клієнти опрацьовані. Середній час очікування: %.2f мс ===%n",
                hotel.getAverageWaitMillis());
    }
}