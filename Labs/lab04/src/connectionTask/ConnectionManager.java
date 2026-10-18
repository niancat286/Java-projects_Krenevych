package connectionTask;

public class ConnectionManager {

    private static Connection[] cons = {
            new Connection(1),
            new Connection(2),
            new Connection(3)
    };

    private static int count = 0;

    public static Connection getConnection(){
        if (count < cons.length) {
            Connection c = cons[count];
            count++;
            return c;
        } else {
            return null;
        }
    }



}
