package jewelry;
import java.util.Objects;

public class Gem {

    private String name;
    private double weightCarats;
    private double pricePerCarat;
    private double transparency;


    public Gem(String name, double weightCarats, double pricePerCarat, double transparency) {
        this.name = name;
        this.weightCarats = weightCarats;
        this.pricePerCarat = pricePerCarat;
        this.transparency = transparency;
    }

    public Gem(String name, double weightCarats, double pricePerCarat) {
        this(name, weightCarats, pricePerCarat, 0.5);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeightCarats() {
        return weightCarats;
    }

    public void setWeightCarats(double weightCarats) {
        this.weightCarats = weightCarats;
    }

    public double getPricePerCarat() {
        return pricePerCarat;
    }

    public void setPricePerCarat(double pricePerCarat) {
        this.pricePerCarat = pricePerCarat;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    // Повна вартість каменю: вага * ціна за карат
    public double getTotalPrice() {
        return weightCarats * pricePerCarat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gem gemstone = (Gem) o;
        return Double.compare(gemstone.weightCarats, weightCarats) == 0 &&
                Double.compare(gemstone.pricePerCarat, pricePerCarat) == 0 &&
                Double.compare(gemstone.transparency, transparency) == 0 &&
                Objects.equals(name, gemstone.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weightCarats, pricePerCarat, transparency);
    }

    @Override
    public String toString() {
        return String.format("%s: вага = %.2f карат, вартість = %.2f грн, прозорість = %.2f",
                name, weightCarats, getTotalPrice(), transparency);
    }

}
