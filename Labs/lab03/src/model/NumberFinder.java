package model;

public class NumberFinder {
    public static int countDigits(int num){
        if (num == 0){
            return 1;
        }
        int n = Math.abs(num);
        int counter = 0;
        while (n > 0){
            counter++;
            n = n / 10;
        }
        return counter;
    }

    public static void findMinLen(int[] nums){
        if (nums == null || nums.length == 0){
            System.out.println("Array is empty");
            return;
        }

        int minLen = countDigits(nums[0]);
        for (int i = 1; i < nums.length; i++){
            int curr = countDigits(nums[i]);
            if (curr < minLen){
                minLen = curr;
            }
        }

        System.out.println("Min length is " + minLen);
        System.out.println("Such numbers are: ");
        for (int i = 0; i < nums.length; i++) {
            if (countDigits(nums[i]) == minLen) {
                System.out.print(nums[i] + " ");
            }
        }
        System.out.println();
    }
}
