import java.util.ArrayList;



public class ProducerConsumerOneItem {
    private class Shop {
        private int value;
    }
    private Shop shop = new Shop();
    private boolean available = false;
    private boolean finished = false;

    class Producer implements Runnable {
        private int value;
        public Producer(int value) {
            this.value = value;
        }
        public void produce(int value) throws InterruptedException {
            synchronized (ProducerConsumerOneItem.this) {
                while (available) {
                    ProducerConsumerOneItem.this.wait();
                }
                shop.value = value;
                available = true;
                ProducerConsumerOneItem.this.notifyAll(); //будимо всіх споживачів, коли з'явився новий товар
            }
        }
        @Override
        public void run() {
            try {
                for(int i=0; i<value; i++) {
                    Thread.sleep(100); // simulate some delay between productions
                    produce(i);
                }
                System.out.println("Produced: " + value);
                synchronized (ProducerConsumerOneItem.this) {
                    finished = true;
                    ProducerConsumerOneItem.this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); // restore interrupted status
            }
        }
    }

    class Consumer implements Runnable {
        ArrayList<Integer> consumedValues = new ArrayList<>();
        public int consume() throws InterruptedException {
            synchronized (ProducerConsumerOneItem.this) {
                if(available){
                    consumedValues.add(shop.value);
                    available = false;
                    ProducerConsumerOneItem.this.notifyAll();
                    return shop.value;
                }
                if(finished) {
                    return -2; // indicate that production is finished
                }
            }
            return -1; // or throw an exception if no value is available
        }
        public ArrayList<Integer> getConsumedValues() {
            return consumedValues;
        }
        @Override
        public void run() {
            try {
                while (true) {
                    int result = consume();
                    if (result == -1) {
                        Thread.sleep(100); // wait before trying again
                    } else if (result == -2) {
                        break; // exit loop if production is finished
                    } else {
                        System.out.println(Thread.currentThread().getName() + " Consumed: " + result);
                    }
                    System.out.println("Waiting to consume...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); // restore interrupted status
            }
        }
    }


    public static void main(String[] args) {
        ProducerConsumerOneItem pc = new ProducerConsumerOneItem();
        Producer producer = pc.new Producer(10);
        //Consumer consumer = pc.new Consumer();

        int N = 3; //задаємо кількість споживачів (N)



        //Thread producerThread = new Thread(producer);
        //Thread consumerThread = new Thread(consumer);


        //масиви для зберігання споживачів та їхніх потоків замість єдиного consumer
        Consumer[] consumers = new Consumer[N];
        Thread[] consumerThreads = new Thread[N];

        Thread producerThread = new Thread(producer);

        //цикл для створення N споживачів та призначення їм імен
        for (int i = 0; i < N; i++) {
            consumers[i] = pc.new Consumer();
            consumerThreads[i] = new Thread(consumers[i], "Consumer-" + (i + 1));
        }

        producerThread.start();

        //запуск усіх потоків споживачів у циклі
        for (int i = 0; i < N; i++) {
            consumerThreads[i].start();
        }

        try {
            producerThread.join();

            // очікування завершення (join) для кожного потоку споживача в циклі
            for (int i = 0; i < N; i++) {
                consumerThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // вивід результатів роботи кожного споживача окремо
        for (int i = 0; i < N; i++) {
            System.out.println("Consumer-" + (i + 1) + " consumed values: " + consumers[i].getConsumedValues());
        }
    }
}
