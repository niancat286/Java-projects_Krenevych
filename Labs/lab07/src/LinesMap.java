
import java.math.BigInteger;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class LinesMap{

    class Line {
      BigInteger a, b, c;

        Line(BigInteger a, BigInteger b, BigInteger c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return a + "x + " + b + "y + " + c + " = 0";
        }

        @Override 
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Line line = (Line) obj;
            return a.equals(line.a) && b.equals(line.b) && c.equals(line.c);
        }
        
        @Override
        public int hashCode() {
            return a.hashCode() * 31 * 31 + b.hashCode() * 31 + c.hashCode();
        }   
    }

    public static void main(String[] args) {
        // // Example usage of Line class
        // BigInteger a = BigInteger.valueOf(1);
        // BigInteger b = BigInteger.valueOf(2);
        // BigInteger c = BigInteger.valueOf(3);
        // Line line = new LinesMap().new Line(a, b, c);
        // System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        Integer n = scanner.nextInt();

        
        record IntegerPair(Integer x, Integer y) {}
        ArrayList<IntegerPair> points= new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new IntegerPair(x, y));
        }
        scanner.close();

        HashMap<Line, HashSet<IntegerPair>> linesMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            IntegerPair point = points.get(i);
            for (int j=i+1; j < n; j++) {
                IntegerPair otherPoint = points.get(j);
                
                BigInteger x1 = BigInteger.valueOf(point.x);
                BigInteger y1 = BigInteger.valueOf(point.y);
                BigInteger x2 = BigInteger.valueOf(otherPoint.x);
                BigInteger y2 = BigInteger.valueOf(otherPoint.y);
                BigInteger a = y2.subtract(y1);
                BigInteger b = x1.subtract(x2);
                BigInteger c = x2.multiply(y1).subtract(x1.multiply(y2));
                Line line = new LinesMap().new Line(a, b, c);
                System.out.println(line);
                linesMap.computeIfAbsent(line, k -> new HashSet<>()).add(point);
                linesMap.get(line).add(otherPoint);
                
            }
        }

        for (Line line : linesMap.keySet()) {
            System.out.println(line + " : " + linesMap.get(line).size());
        }


    }

    
}
