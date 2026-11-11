package task6;

import java.util.Scanner;

public class StringRegexTasks {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть рядок для обробки: ");
        // Програма зупиниться тут і чекатиме, поки ви введете текст і натиснете Enter
        String text = scanner.nextLine();


        String res1 = text.replaceAll("\\d", "")
                .replaceAll("\\+", "++")
                .replaceAll("-", "--");
        System.out.println("Пункт 1 : " + res1);


        String res2 = text.replaceAll("\\+(\\d)", "$1");
        System.out.println("Пункт 2 : " + res2);


        String res3 = text.replaceAll("св", "с");
        System.out.println("Пункт 3 : " + res3);


        String res4 = text.replaceAll("ph", "f");
        System.out.println("Пункт 4 : " + res4);


        String res5 = text.replaceAll("\\s+", " ");
        System.out.println("Пункт 5 : " + res5);
    }
}
