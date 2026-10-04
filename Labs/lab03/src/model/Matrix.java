package model;

import java.util.Random;
import java.util.Scanner;


public class Matrix {
    private int n;
    private int[][] a;

    public Matrix(int n){
        this.n = n;
        this.a = new int[n][n];
    }

    public void fillRand(){
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = random.nextInt(2 * n + 1) - n;
            }
        }
    }

    public void ConsoleInput(Scanner sc){
        System.out.println("Input elements of matrix (numbers from " + (-n) + " to " + (n) + ")");
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                while (true){
                    System.out.println("a[" + i + "][" + j + "] = ");
                    int val = sc.nextInt();
                    if (val >= -n && val <= n){
                        a[i][j] = val;
                        break;
                    } else {
                        System.out.println("Invalid number, try again");
                    }
                }
            }
        }
    }

    public void fillFromConsole(Scanner sc) {
        System.out.println("Input elements of matrix " + n + "x" + n + ":");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("a[" + i + "][" + j + "] = ");
                a[i][j] = sc.nextInt();
            }
        }
    }

    public void print(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%5d", a[i][j]);
            }
            System.out.println();
        }
    }


    public void rotate90left(){
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                rotated[n - 1 - j][i] = a[i][j];
            }
        }
        this.a = rotated;
    }

    public void rotate180(){
        rotate90left();
        rotate90left();
    }

    public void rotate270left(){
        rotate180();
        rotate90left();
    }


}
