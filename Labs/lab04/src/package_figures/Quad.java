package package_figures;

import java.util.Scanner;

public class Quad {

    double a;
    double b;
    double c;
    double d;

    public Quad (){
        a = 0;
        b = 0;
        c = 0;
        d = 0;
    }

    public Quad(double a, double b, double c, double d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void input(Scanner sc){
        System.out.println("input a:");
        a = sc.nextDouble();
        System.out.println("input b:");
        b = sc.nextDouble();
        System.out.println("input c:");
        c = sc.nextDouble();
        System.out.println("input d:");
        d = sc.nextDouble();
    }



    public double calc_perim(){
        return a+b+c+d;
    }

    double geron(double a, double b, double c){
        double half_perim = (a+b+c)/2;

        double area = Math.sqrt(half_perim * (half_perim -a) * (half_perim - b) * (half_perim - c));

        return area;
    }

    public double calc_area(double diag){
        return geron(a, b, diag) + geron(c, d, diag);
    }

    public double calc_area(){
        return 0;
    }




    public static void main(String[] args){
        Quad q1 = new Quad(1, 2, 3, 4);
        System.out.println(q1.calc_perim());

        double diag = 1.5;

        System.out.println(q1.calc_area(diag));

        Quad q2 = new Rectangle(5, 6);

        System.out.println(q2.calc_perim());
        System.out.println(q2.calc_area());
    }

}
