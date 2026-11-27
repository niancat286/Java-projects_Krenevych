import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CircleComparison {
    // вибування кожного k-го в списку
    private static int eliminateInCircle(List<Integer> circle, int k) {
        int index = 0;
        while (circle.size() > 1) {
            index = (index + k - 1) % circle.size();
            circle.remove(index);
        }
        return circle.get(0);
    }

    public static void main(String[] args) {
        int n = 20_000; // кількість людей
        int k = 2;      // k-та людина вибуває

        List<Integer> arrayListCircle = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            arrayListCircle.add(i);
        }
        long startArray = System.nanoTime();
        int winnerArray = eliminateInCircle(arrayListCircle, k);
        long timeArrayMs = (System.nanoTime() - startArray) / 1_000_000;



        List<Integer> linkedListCircle = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            linkedListCircle.add(i);
        }
        long startLinked = System.nanoTime();
        int winnerLinked = eliminateInCircle(linkedListCircle, k);
        long timeLinkedMs = (System.nanoTime() - startLinked) / 1_000_000;

        System.out.println("Переможець (ArrayList):  " + winnerArray + ", час: " + timeArrayMs + " мс");
        System.out.println("Переможець (LinkedList): " + winnerLinked + ", час: " + timeLinkedMs + " мс");


        ///ArrayList зазвичай працює швидше, бо його елементи лежать підряд у пам'яті
        ///та краще кешуються процесором, тоді як LinkedList повільніший через прохід
        ///по вузлах за індексом і зайві витрати пам'яті на об'єкти-вузли, хоча через часті видалення
        ///обидва варіанти мають складність близько O(N^2).
    }
}
