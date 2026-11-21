import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskSumFileNumbers {

    public static boolean createBinaryFile(String filename, int n) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            Random rand = new Random();
            for (int i = 0; i < n; i++) {
                dos.writeInt(rand.nextInt(100));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int[] readBinaryFile(String filename) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            List<Integer> numbers = new ArrayList<>();
            while (dis.available() > 0) {
                numbers.add(dis.readInt());
            }
            return numbers.stream().mapToInt(i -> i).toArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class SumThread extends Thread {
        int[] array;
        int start;
        int end;
        long result;

        public SumThread(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            result = 0;
            for (int i = start; i < end; i++) {
                result += array[i];
            }
        }
    }

    public static void main(String[] args) {
        String filename = "numbers.bin";
        int totalNumbers = 100;

        if (!createBinaryFile(filename, totalNumbers)) {
            System.out.println("error create file");
            return;
        }

        int[] numbers = readBinaryFile(filename);
        if (numbers == null) {
            System.out.println("error read file");
            return;
        }

        System.out.println("4 threads");

        SumThread thread1 = new SumThread(numbers, 0, 25);
        SumThread thread2 = new SumThread(numbers, 25, 50);
        SumThread thread3 = new SumThread(numbers, 50, 75);
        SumThread thread4 = new SumThread(numbers, 75, 100);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            System.out.println("error wait theads");
        }

        System.out.println("thr 1: " + thread1.result);
        System.out.println("thr 2: " + thread2.result);
        System.out.println("thr 3: " + thread3.result);
        System.out.println("thr 4: " + thread4.result);

        long totalSum = thread1.result + thread2.result + thread3.result + thread4.result;

        System.out.println("total: " + totalSum);
    }
}