package jewelry;
import java.util.Objects;

public class PreciousStone extends Gem{
    private String clarityGrade;

    public PreciousStone(String name, double weightCarats, double pricePerCarat, double transparency, String clarityGrade) {
        super(name, weightCarats, pricePerCarat, transparency);
        this.clarityGrade = clarityGrade;
    }

    public PreciousStone(String name, double weightCarats, double pricePerCarat, String clarityGrade) {
        super(name, weightCarats, pricePerCarat, 0.95);
        this.clarityGrade = clarityGrade;
    }

    public String getClarityGrade() {
        return clarityGrade;
    }

    public void setClarityGrade(String clarityGrade) {
        this.clarityGrade = clarityGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        PreciousStone that = (PreciousStone) o;
        return Objects.equals(clarityGrade, that.clarityGrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clarityGrade);
    }

    @Override
    public String toString() {
        return "[Дорогоцінний] " + super.toString() + ", чистота = " + clarityGrade;
    }


}
