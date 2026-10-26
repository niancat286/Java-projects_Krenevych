package flowershop;

public abstract class Flower {

    private String name;
    private double price;
    private int fresh;
    private double len;


    public Flower(String name, double price, int fresh, double len) {
        this.name = name;
        this.price = price;
        this.fresh = fresh;
        this.len = len;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getFresh(){
        return fresh;
    }

    public double getLen(){
        return len;
    }

    @Override
    public String toString() {
        return String.format("%s [Ціна: %.2f грн, Свіжість: %d дн., Стебло: %.1f см]",
                name, price, fresh, len);
    }

}
