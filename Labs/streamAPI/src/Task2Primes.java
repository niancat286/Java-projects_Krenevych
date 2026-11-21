import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Task2Primes {
    private static final int MAX_INTERVAL_SIZE = 10_000_000;

    private record TwinPair(int first, int second) {
        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    private record ReversedPrimePair(int original, long reversed) {
        @Override
        public String toString() {
            return "(" + original + ", " + reversed + ")";
        }
    }

    public static void main(String[] args) {
        int a;
        int b;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a (a >= 2): ");
            a = parseInt(scanner.nextLine().trim(), "a");

            System.out.print("Enter b (b >= a): ");
            b = parseInt(scanner.nextLine().trim(), "b");
        }

        if (a < 2 || a > b) {
            throw new IllegalArgumentException("Bounds must satisfy 2 <= a <= b.");
        }

        long intervalSize = (long) b - a + 1;
        if (intervalSize > MAX_INTERVAL_SIZE) {
            throw new IllegalArgumentException(
                    "The interval contains " + intervalSize + " numbers; the maximum supported size is "
                            + MAX_INTERVAL_SIZE + ".");
        }

        List<Integer> primes = IntStream.rangeClosed(a, b)
                .filter(Task2Primes::isPrime)
                .boxed()
                .toList();

        List<TwinPair> twinPairs = IntStream.range(0, primes.size() - 1)
                .filter(i -> primes.get(i + 1) - primes.get(i) == 2)
                .mapToObj(i -> new TwinPair(primes.get(i), primes.get(i + 1)))
                .toList();

        List<ReversedPrimePair> reversiblePrimes = primes.stream()
                .map(p -> new ReversedPrimePair(p, reverseDigits(p)))
                .filter(pair -> pair.reversed() != pair.original() && isPrime(pair.reversed()))
                .toList();

        System.out.println("Prime numbers: " + primes);
        System.out.println("Count: " + primes.size());
        System.out.println("Twin-prime pairs: " + twinPairs);
        System.out.println("Primes whose reversed digits also form a different prime: " + reversiblePrimes);
    }

    private static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            return number == 2;
        }
        for (long divisor = 3; divisor <= number / divisor; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    private static long reverseDigits(int number) {
        long remaining = number;
        long reversed = 0;
        while (remaining > 0) {
            reversed = reversed * 10 + remaining % 10;
            remaining /= 10;
        }
        return reversed;
    }

    private static int parseInt(String text, String name) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(name + " must be an integer: " + text, exception);
        }
    }
}