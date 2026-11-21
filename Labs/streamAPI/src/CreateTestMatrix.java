import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateTestMatrix {
    public static void main(String[] args) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("matrix.bin"))) {
            out.writeInt(2); // rows
            out.writeInt(3); // cols
            double[] values = {1.5, -3.0, 0.0, 4.0, 2.0, -5.0};
            for (double v : values) {
                out.writeDouble(v);
            }
        }
        System.out.println("Created matrix.bin successfully.");
    }
}