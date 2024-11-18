import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm_db"; // Replace with your database URL
    private static final String DB_USER = "root"; // Replace with your database username
    private static final String DB_PASSWORD = "Lagrange123@_"; // Replace with your database password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
