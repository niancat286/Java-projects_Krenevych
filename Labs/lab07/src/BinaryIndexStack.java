import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class BinaryIndexStack {
    public static void main(String[] args) {
        String fileName = "records.bin";
        String[] records = {
                "Перший запис",
                "Другий запис у файлі",
                "Третій",
                "Четвертий — трохи довший запис"
        };

        //  зберігає зміщення кожного запису у бінарному файлі
        Stack<Long> indexStack = new Stack<>();

        //Записуємо дані і запам'ятовуємо позицію
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.setLength(0); // очищаємо

            for (String record : records) {
                long offset = raf.getFilePointer(); // поточна позиція
                indexStack.push(offset);

                byte[] bytes = record.getBytes(StandardCharsets.UTF_8);
                raf.writeInt(bytes.length); // довжина запису
                raf.write(bytes);           // байти рядка
            }

            // Швидкий доступ до записів за індексом через стек позицій
            int targetIndex = 2; // хочемо прочитати 3-й запис (індекс 2)
            if (targetIndex >= 0 && targetIndex < indexStack.size()) {
                long targetOffset = indexStack.get(targetIndex);
                raf.seek(targetOffset); // стрибаємо одразу до потрібного місця

                int length = raf.readInt();
                byte[] buffer = new byte[length];
                raf.readFully(buffer);
                String foundRecord = new String(buffer, StandardCharsets.UTF_8);

                System.out.println("Запис за індексом " + targetIndex + ": " + foundRecord);
            }

            // Приклад роботи зі стеком
            System.out.println("\nЧитання зі стеку у зворотному порядку:");
            while (!indexStack.isEmpty()) {
                long offset = indexStack.pop();
                raf.seek(offset);
                int length = raf.readInt();
                byte[] buffer = new byte[length];
                raf.readFully(buffer);
                System.out.println("Позиція " + offset + " -> " + new String(buffer, StandardCharsets.UTF_8));
            }

        } catch (IOException e) {
            System.out.println("Помилка роботи з бінарним файлом: " + e.getMessage());
        }
    }
}
