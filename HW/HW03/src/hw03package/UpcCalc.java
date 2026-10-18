package hw03package;

public class UpcCalc {
    public static int calcCheckDigit(long num11){
        long tmp = num11;
        int sumOdd = 0;
        int sumEven = 0;

        for (int pos = 2; pos <= 12; pos++){
            int digit = (int) (tmp % 10);
            tmp = tmp / 10;

            if (pos % 2 == 0){
                sumEven += digit;
            } else {
                sumOdd += digit;
            }
        }
        int res = sumOdd + 3 * sumEven;

        int rem = res % 10;
        if (rem == 0){
            return 0;
        } else {
            return 10 - rem;
        }
    }
}
