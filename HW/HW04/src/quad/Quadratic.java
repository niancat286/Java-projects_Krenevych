package quad;

import java.util.Objects;

public class Quadratic {
    private double a;
    private double b;
    private double c;

    public Quadratic(double a, double b, double c) {
        if (Math.abs(a) < 1e-9) {
            throw new IllegalArgumentException("Коефіцієнт 'a' не може дорівнювати 0 для квадратного рівняння");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Quadratic(double a, double c) {
        this(a, 0.0, c);
    }

    public Quadratic(double b, double c, boolean isMonic) {
        this(1.0, b, c);
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        if (Math.abs(a) < 1e-9) {
            throw new IllegalArgumentException("Коефіцієнт 'a' не може дорівнювати 0");
        }
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getDiscriminant() {
        return b * b - 4 * a * c;
    }

    public double[] findRoots() {
        double d = getDiscriminant();
        if (d < -1e-9) {
            return new double[0];
        }
        if (Math.abs(d) <= 1e-9) {
            return new double[]{-b / (2 * a)};
        }
        double sqrtD = Math.sqrt(d);
        double x1 = (-b - sqrtD) / (2 * a);
        double x2 = (-b + sqrtD) / (2 * a);
        return new double[]{Math.min(x1, x2), Math.max(x1, x2)};
    }

    public double getExtremumX() {
        return -b / (2 * a);
    }

    public double getExtremumY() {
        double xV = getExtremumX();
        return a * xV * xV + b * xV + c;
    }

    public String getExtremumDescription() {
        double xV = getExtremumX();
        double yV = getExtremumY();
        if (a > 0) {
            return String.format("Мінімум у точці (%.2f; %.2f)", xV, yV);
        } else {
            return String.format("Максимум у точці (%.2f; %.2f)", xV, yV);
        }
    }

    public String getMonotonicityIntervals() {
        double xV = getExtremumX();
        if (a > 0) {
            return String.format("Спадає на (-inf; %.2f], зростає на [%.2f; +inf)", xV, xV);
        } else {
            return String.format("Зростає на (-inf; %.2f], спадає на [%.2f; +inf)", xV, xV);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quadratic that = (Quadratic) o;
        return Double.compare(that.a, a) == 0 &&
                Double.compare(that.b, b) == 0 &&
                Double.compare(that.c, c) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    @Override
    public String toString() {
        return String.format("%.2fx^2 + (%.2f)x + (%.2f) = 0", a, b, c);
    }
}


