package jewelry;
import java.util.Objects;

public class SemipreciousStone extends Gem{
    private String colorShade;

    public SemipreciousStone(String name, double weightCarats, double pricePerCarat, double transparency, String colorShade) {
        super(name, weightCarats, pricePerCarat, transparency);
        this.colorShade = colorShade;
    }

    public SemipreciousStone(String name, double weightCarats, double pricePerCarat, String colorShade) {
        super(name, weightCarats, pricePerCarat, 0.6);
        this.colorShade = colorShade;
    }

    public String getColorShade() {
        return colorShade;
    }

    public void setColorShade(String colorShade) {
        this.colorShade = colorShade;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        SemipreciousStone that = (SemipreciousStone) o;
        return Objects.equals(colorShade, that.colorShade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), colorShade);
    }

    @Override
    public String toString() {
        return "[Напівдорогоцінний] " + super.toString() + ", відтінок = " + colorShade;
    }

}
