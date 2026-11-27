import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WordFrequency {
    public static void main(String[] args) {
        String text = "Java — це мова програмування. Багато студентів вивчають Java, "
                + "адже мова Java допомагає зрозуміти ООП та колекції.";

        List<String> targetWords = new ArrayList<>();
        if (args.length > 0) {
            for (String arg : args) {
                targetWords.add(arg.toLowerCase(Locale.ROOT));
            }
        } else {
            targetWords.add("java");
            targetWords.add("мова");
            targetWords.add("python");
        }

        // Розбиваємо текст на слова без розділових
        String[] rawWords = text.toLowerCase(Locale.ROOT).split("[^\\p{L}\\p{Nd}]+");
        List<String> words = new ArrayList<>();
        for (String w : rawWords) {
            if (!w.isEmpty()) {
                words.add(w);
            }
        }

        if (words.isEmpty()) {
            System.out.println("У тексті немає слів.");
            return;
        }

        // Підраховуємо кількість кожного слова в тексті
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        int totalWords = words.size();
        System.out.println("Загальна кількість слів у тексті: " + totalWords);

        // Виводимо відносну частоту для кожного слова зі списку
        for (String target : targetWords) {
            int count = counts.getOrDefault(target, 0);
            double relativeFrequency = (double) count / totalWords;
            System.out.printf(Locale.ROOT,
                    "Слово '%s': зустрічається %d раз(и), відносна частота = %.4f%n",
                    target, count, relativeFrequency);
        }
    }
}
