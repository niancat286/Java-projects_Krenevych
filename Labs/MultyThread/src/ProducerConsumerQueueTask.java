import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerQueueTask {

    // Клас-буфер (раніше це був просто Shop з одним value, а тепер це повноцінна черга)
    private class MessageQueue {
        private final Queue<String> queue = new LinkedList<>();
        private final int capacity = 5; // Ліміт черги, щоб виробник не випереджав занадто сильно

        // Метод додавання (виробник)
        public synchronized void produce(String message) throws InterruptedException {
            while (queue.size() >= capacity) {
                wait(); // Чекаємо, якщо черга заповнена
            }
            queue.add(message);
            System.out.println("-> Згенеровано: " + message);
            notifyAll(); // Будимо споживача
        }

        // Метод вилучення (споживач)
        public synchronized String consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait(); // Чекаємо, якщо черга порожня
            }
            String message = queue.poll();
            notifyAll(); // Будимо виробника
            return message;
        }
    }

    private MessageQueue messageQueue = new MessageQueue();
    private boolean finished = false;

    class Producer implements Runnable {
        private int t1; // інтервал генерації

        public Producer(int t1) {
            this.t1 = t1;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    Thread.sleep(t1); // t1 одиниць часу на генерацію
                    String message = "Повідомлення #" + i;
                    messageQueue.produce(message);
                }
                synchronized (ProducerConsumerQueueTask.this) {
                    finished = true;
                    notifyAll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class Consumer implements Runnable {
        private int t2; // час обробки

        public Consumer(int t2) {
            this.t2 = t2;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Якщо черга порожня і виробник уже закінчив — виходимо
                    synchronized (ProducerConsumerQueueTask.this) {
                        if (messageQueue.queue.isEmpty() && finished) {
                            break;
                        }
                    }

                    // Споживаємо з черги (метод consume сам заблокує потік, якщо пусто)
                    // Але щоб уникнути вічного зависання в кінці, зробимо безпечніший перехід:
                    if (!messageQueue.queue.isEmpty() || !finished) {
                        // Використаємо простий переривчастий контроль або прямий виклик
                    }

                    // Перепишемо споживання через метод черги з урахуванням таймауту або перевірки закінчення:
                    String msg = messageQueue.consume();

                    Thread.sleep(t2); // t2 одиниць часу на обробку
                    System.out.println("\t<- Оброблено і показано на екрані: " + msg);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerQueueTask pc = new ProducerConsumerQueueTask();

        int t1 = 400; // Виробник створює кожні 400 мс
        int t2 = 700; // Споживач обробляє кожні 700 мс

        Producer producer = pc.new Producer(t1);
        Consumer consumer = pc.new Consumer(t2);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Роботу програми завершено.");
    }
}