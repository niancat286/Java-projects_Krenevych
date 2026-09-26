package package_figures;


import java.util.Scanner;

public class Rectangle extends Quad {

    public Rectangle(){}

    Rectangle(double a, double b){
        this.a = a;
        this.b = b;
        c = a;
        d = b;
    }


    @Override
    public double calc_area(){
        return a * b;
    }

    @Override
    public void input(Scanner sc){
        System.out.println("input a:");
        a = sc.nextDouble();
        System.out.println("input b:");
        b = sc.nextDouble();
    }



    public static void main(String[] args) {
        Rectangle rec1 = new Rectangle(3, 4);
        System.out.println(rec1.calc_perim());
        System.out.println(rec1.calc_area());
    }
}
