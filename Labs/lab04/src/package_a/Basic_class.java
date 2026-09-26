package package_a;


import java.util.Queue;
import java.util.Scanner;
import package_figures.Quad;
import package_figures.Rectangle;

public class Basic_class {
    public void publicMethod(){
        System.out.println("Basic_class public");
    }

    protected void protectedMethod(){
        System.out.println("Basic_class protected");
    }
    public static void main(String[] args) {
        Basic_class BC;
        BC = new Basic_class();

        BC.publicMethod();
        BC.protectedMethod();

        System.out.println("input n:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();


        Quad[] arrQuad = new Quad[n];

        for (int i = 0; i < n; i ++){
            arrQuad[i] = new Rectangle();
            arrQuad[i].input(sc);
        }

        double s = 0;

        for (Quad val : arrQuad){
            s += val.calc_area();
        }

        System.out.println(s);


    }
}