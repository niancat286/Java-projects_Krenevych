package jewelry;

public class Necklace {
    private Gem[] stones;

    public Necklace(Gem[] stones) {
        this.stones = stones;
    }

    public Necklace(int capacity) {
        this.stones = new Gem[capacity];
    }

    public Gem[] getStones() {
        return stones;
    }

    public void setStones(Gem[] stones) {
        this.stones = stones;
    }

    public double calculateTotalWeight() {
        double totalWeight = 0.0;
        if (stones != null) {
            for (Gem stone : stones) {
                if (stone != null) {
                    totalWeight += stone.getWeightCarats();
                }
            }
        }
        return totalWeight;
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        if (stones != null) {
            for (Gem stone : stones) {
                if (stone != null) {
                    totalPrice += stone.getTotalPrice();
                }
            }
        }
        return totalPrice;
    }

    public void sortByValueDescending() {
        if (stones == null || stones.length <= 1) return;

        for (int i = 0; i < stones.length - 1; i++) {
            for (int j = 0; j < stones.length - i - 1; j++) {
                if (stones[j] != null && stones[j + 1] != null) {
                    if (stones[j].getTotalPrice() < stones[j + 1].getTotalPrice()) {
                        Gem temp = stones[j];
                        stones[j] = stones[j + 1];
                        stones[j + 1] = temp;
                    }
                }
            }
        }
    }

    public void printStonesByTransparency(double minTrans, double maxTrans) {
        boolean found = false;
        if (stones != null) {
            for (Gem stone : stones) {
                if (stone != null) {
                    double t = stone.getTransparency();
                    if (t >= minTrans && t <= maxTrans) {
                        System.out.println("  -> " + stone);
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            System.out.printf("  Каменів із прозорістю в межах [%.2f; %.2f] не знайдено.\n", minTrans, maxTrans);
        }
    }

    public void printNecklace() {
        System.out.println("Склад намиста");
        if (stones != null) {
            for (int i = 0; i < stones.length; i++) {
                System.out.println((i + 1) + ". " + stones[i]);
            }
        }
        System.out.printf("Загальна вага: %.2f карат\n", calculateTotalWeight());
        System.out.printf("Загальна вартість: %.2f грн\n", calculateTotalPrice());
        System.out.println();
    }
}
