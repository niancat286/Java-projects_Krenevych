package connectionTask;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Connection conn = ConnectionManager.getConnection();

            if (conn != null) {
                System.out.println("Received: " + conn);
                conn.executeQuery("SELECT * FROM users");
            } else {
                System.out.println("Try " + (i + 1) + ": no objects!");
            }
        }
    }
}
