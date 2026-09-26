import java.io.IOException;
import java.io.PrintStream;

class Word {

    private String value;

    public Word(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}


class Sentence {

    private Word[] words;

    public Sentence(Word[] words) {
        this.words = words;
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result += " ";
            }

            result += words[i];
        }

        return result + ".";
    }
}


class Text {

    private String title;
    private Sentence[] sentences;

    public Text(String title, Sentence[] sentences) {
        this.title = title;
        this.sentences = sentences;
    }

    // Доповнити текст новим реченням
    public void addSentence(Sentence sentence) {

        Sentence[] newSentences =
                new Sentence[sentences.length + 1];

        for (int i = 0; i < sentences.length; i++) {
            newSentences[i] = sentences[i];
        }

        newSentences[sentences.length] = sentence;

        sentences = newSentences;
    }

    // Вивести текст
    public void printText() {

        System.out.println(title);
        System.out.println();

        for (Sentence sentence : sentences) {
            System.out.println(sentence);
        }
    }

    // Вивести заголовок
    public void printTitle() {
        System.out.println(title);
    }
}


public class Task4_4 {

    public static void main(String[] args) throws IOException, InterruptedException {
        // if (System.getProperty("os.name").startsWith("Windows")) {
        //     new ProcessBuilder("cmd.exe", "/c", "chcp 65001 >nul")
        //         .inheritIO()
        //         .start()
        //         .waitFor();
        // }
        // System.setOut(new PrintStream(System.out, true, "UTF-8"));

        Word[] words1 = {
                new Word("Java"),
                new Word("є"),
                new Word("об'єктно"),
                new Word("орієнтованою"),
                new Word("мовою")
        };

        Word[] words2 = {
                new Word("Класи"),
                new Word("дозволяють"),
                new Word("описувати"),
                new Word("об'єкти")
        };

        Sentence sentence1 = new Sentence(words1);
        Sentence sentence2 = new Sentence(words2);

        Sentence[] sentences = {
                sentence1,
                sentence2
        };

        Text text = new Text(
                "Java та об'єктно-орієнтоване програмування",
                sentences
        );

        // Виведення заголовка
        text.printTitle();

        System.out.println();

        // Виведення тексту
        text.printText();

        // Створення нового речення
        Word[] words3 = {
                new Word("Об'єкти"),
                new Word("взаємодіють"),
                new Word("між"),
                new Word("собою")
        };

        Sentence sentence3 = new Sentence(words3);

        // Доповнення тексту
        text.addSentence(sentence3);

        System.out.println();
        System.out.println("Після доповнення:");

        text.printText();
    }
}

/* 

### Структура класів

Залежності між класами:

```text
Text
 │
 ├── title
 │
 └── Sentence[]
       │
       ├── Sentence
       │     └── Word[]
       │           ├── Word
       │           ├── Word
       │           └── Word
       │
       └── Sentence
             └── Word[]
```

Тобто:

```text
Текст
  ↓ складається з
Речень
  ↓ складається з
Слів
```

### Основні методи `Text`

`addSentence()` — доповнює текст новим реченням:

```java
text.addSentence(sentence3);
```

`printText()` — виводить заголовок і всі речення:

```java
text.printText();
```

`printTitle()` — виводить тільки заголовок:

```java
text.printTitle();
```

### Приклад результату

```text
Java та об'єктно-орієнтоване програмування

Java є об'єктно орієнтованою мовою.
Класи дозволяють описувати об'єкти.

Після доповнення:
Java та об'єктно-орієнтоване програмування

Java є об'єктно орієнтованою мовою.
Класи дозволяють описувати об'єкти.
Об'єкти взаємодіють між собою.
```
*/