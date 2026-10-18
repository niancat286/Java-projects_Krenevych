package quad;

public class EqArray {
    private Quadratic[] equations;

    public EqArray(Quadratic[] equations) {
        this.equations = equations;
    }

    public EqArray(int size) {
        this.equations = new Quadratic[size];
    }

    public Quadratic[] getEquations() {
        return equations;
    }

    public void setEquations(Quadratic[] equations) {
        this.equations = equations;
    }

    public Double findMinRoot() {
        Double min = null;
        if (equations == null) return null;

        for (Quadratic eq : equations) {
            if (eq != null) {
                double[] roots = eq.findRoots();
                for (double r : roots) {
                    if (min == null || r < min) {
                        min = r;
                    }
                }
            }
        }
        return min;
    }

    public Double findMaxRoot() {
        Double max = null;
        if (equations == null) return null;

        for (Quadratic eq : equations) {
            if (eq != null) {
                double[] roots = eq.findRoots();
                for (double r : roots) {
                    if (max == null || r > max) {
                        max = r;
                    }
                }
            }
        }
        return max;
    }

    public void printAll() {
        if (equations == null || equations.length == 0) {
            System.out.println("Масив рівнянь порожній.");
            return;
        }

        for (int i = 0; i < equations.length; i++) {
            Quadratic eq = equations[i];
            System.out.println((i + 1) + ") " + eq);
            double[] roots = eq.findRoots();
            System.out.print("   Корені: ");
            if (roots.length == 0) {
                System.out.println("дійсних коренів немає");
            } else if (roots.length == 1) {
                System.out.printf("x = %.3f\n", roots[0]);
            } else {
                System.out.printf("x1 = %.3f, x2 = %.3f\n", roots[0], roots[1]);
            }
            System.out.println("   Екстремум: " + eq.getExtremumDescription());
            System.out.println("   Монотонність: " + eq.getMonotonicityIntervals());
        }
    }
}
