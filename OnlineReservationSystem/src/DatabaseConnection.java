import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/onlinereservationsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "Lagrange123@_";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to database!");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }
}
