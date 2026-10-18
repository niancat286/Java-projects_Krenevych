package hw03package;

public class PalindromeFinder {

    public static boolean isPalindrome(int num){
        if (num < 0){
            return false;
        }
        int origin = num;
        int reversed = 0;

        int tmp = num;
        while (tmp > 0){
            int lstDigit = tmp % 10;
            reversed = reversed * 10 + lstDigit;
            tmp = tmp / 10;
        }
        return origin == reversed;
    }

    public static void findPrintPalindrome(int[] nums){
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty");
            return;
        }

        int count = 0;
        int firstPalindrome = 0;
        int secondPalindrome = 0;

        for (int i = 0; i < nums.length; i++) {
            if (isPalindrome(nums[i])) {
                count++;
                if (count == 1) {
                    firstPalindrome = nums[i];
                } else {
                    secondPalindrome = nums[i];
                    break;
                }
            }
        }

        if (count == 0){
            System.out.println("0 palindromes");
        } else if (count == 1){
            System.out.println("Found 1 palindrome " + firstPalindrome);
        } else {
            System.out.println("Found more than 1 palindrome, 2nd is: " + secondPalindrome);
        }

    }

}
