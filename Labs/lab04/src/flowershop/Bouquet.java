package flowershop;

public class Bouquet {
    private Flower[] flowers;
    private Accessory[] acsrs;

    public Bouquet(Flower[] flowers, Accessory[] accessories) {
        this.flowers = flowers;
        this.acsrs = accessories;
    }

    public double calcPrice(){
        double res = 0.0;
        if (flowers != null) {
            for (Flower flower : flowers) {
                if (flower != null) {
                    res += flower.getPrice();
                }
            }
        }

        if (acsrs != null) {
            for (Accessory acsr : acsrs) {
                if (acsr != null) {
                    res += acsr.getPrice();
                }
            }
        }

        return res;
    }

    public void sortFresh() {
        if (flowers == null || flowers.length <= 1) {
            return;
        }

        for (int i = 0; i < flowers.length - 1; i++) {
            for (int j = 0; j < flowers.length - i - 1; j++) {
                if (flowers[j].getFresh() > flowers[j + 1].getFresh()) {
                    Flower temp = flowers[j];
                    flowers[j] = flowers[j + 1];
                    flowers[j + 1] = temp;
                }
            }
        }
    }

    public void findLen(double minLen, double maxLen) {
        boolean found = false;

        if (flowers != null) {
            for (Flower flower : flowers) {
                if (flower != null && flower.getLen() >= minLen && flower.getLen() <= maxLen) {
                    System.out.println("  -> " + flower);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Квітів у діапазоні стебла від " + minLen + " до " + maxLen + " см не знайдено.");
        }
    }

    public void printBouquet() {
        System.out.println("Букет:");
        System.out.println("Квіти:");
        if (flowers != null) {
            for (Flower flower : flowers) {
                System.out.println("  * " + flower);
            }
        }
        System.out.println("Аксесуари:");
        if (acsrs != null) {
            for (Accessory acsr: acsrs) {
                System.out.println("  * " + acsr);
            }
        }
        System.out.printf("Повна вартість: %.2f грн\n", calcPrice());
        System.out.println();
    }


}
