import java.util.Scanner;

///Дано три натуральних числа: 32-бітне число, 8-бітний номер біта та булеве значення біта.
/// Необхідно поміняти в першому числі біт з заданим номером на передане значення та вивести
/// отримане після змін біта число в десятковій, шістнадцятковій та двійковій формі.
/// Наприклад, якщо Ви вводите «12 1 1», програма має вивести «13 0xD 1101»;
/// і якщо Ви вводите «13 1 0»б програма має вивести «12 xС 1100»



public class Task02_02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("input number, number of bit and value (0 or 1): ");
        int number = scanner.nextInt();
        int bitIndex = scanner.nextInt();
        int value = scanner.nextInt();

        int mask = 1 << (bitIndex - 1);
        int result;

        if (value == 1) {
            result = number | mask;
        } else {
            result = number & ~mask;
        }

        String hex = "0x" + Integer.toHexString(result).toUpperCase();
        String bin = Integer.toBinaryString(result);

        System.out.println(result + " " + hex + " " + bin);
    }

}
