package laba_Arrays;

import java.util.Scanner;
import java.util.Random;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static void task1() {

        String[] masStrings = {
                "Hello",
                "World",
                "Of",
                "Java"
        };

        // 1. Вивід через foreach (з комою після кожного елемента)
        for (String s : masStrings) {
            System.out.print(s + ", ");
        }
        System.out.println();

        // 2. Вивід через for з індексами (без коми після останнього слова)
        for (int i = 0; i < masStrings.length - 1; i++) {
            System.out.print(masStrings[i] + ", ");
        }
        System.out.println(masStrings[masStrings.length - 1]);
        System.out.println();

        // 3. Вивід кожного слова з нового рядка
        for (int i = 0; i < masStrings.length; i++) {
            System.out.println(masStrings[i]);
        }

    }

    static void task2(){

        int lineount = 1;
        int wordcount = 0;
        int charcount = 0;

        while (sc.hasNext()){
            String input = sc.next();
            System.out.println(input);

            if (input.isEmpty()){
                break;
            }
            lineount++;
            wordcount += input.split("\\s+").length;
            charcount += input.length();

            System.out.println("Number of lines: " + lineount);
            System.out.println("Number of words: " + wordcount);
            System.out.println("Number of characters: " + charcount);
        }
    }


    public static void main(String[] args) {
        //task1();
        task2();
    }
}