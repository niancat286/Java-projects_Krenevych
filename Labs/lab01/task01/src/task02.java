public class task02 {
    public static void main(String[] args) {

        if (args.length == 3) {
            double num1 = Double.parseDouble(args[0]); // parse the first argument
            double num2 = Double.parseDouble(args[1]);
            double num3 = Double.parseDouble(args[2]);

            double sum = num1 + num2 + num3;
            double average = sum / 3;

            System.out.println("Sum: " + sum);
            System.out.println("Average: " + average);
        } else {
            System.out.println("Please provide exactly 3 numbers as arguments.");
        }

    }
}
