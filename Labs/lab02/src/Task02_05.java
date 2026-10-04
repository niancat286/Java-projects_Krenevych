public class Task02_05 {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("input n: ");
        long n = scanner.nextLong();

        // Перебираємо першу пару a та b
        for (long a = 1; a * a * a <= n; a++) {
            long a3 = a * a * a;

            for (long b = a + 1; a3 + b * b * b <= n; b++) {
                long sum = a3 + b * b * b;

                // Шукаємо другу пару c та d таку, що c > a і c^3 + d^3 == sum
                for (long c = a + 1; c * c * c < sum; c++) {
                    long diff = sum - c * c * c;
                    long d = Math.round(Math.cbrt(diff));

                    // Перевіряємо, що d > c (щоб не дублювати пари) і куб точно збігається
                    if (d > c && d * d * d == diff) {
                        System.out.println(sum + " = " + a + "^3 + " + b + "^3 = " + c + "^3 + " + d + "^3");
                    }
                }
            }
        }
    }
}


/// 87539319 - можна подати у вигляді суми двох кубів трьома різними способами