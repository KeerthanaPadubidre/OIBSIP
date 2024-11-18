import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Deposit {
    public static void process(String userId, Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, amount);
            statement.setString(2, userId);
            statement.executeUpdate();

            System.out.println("Deposit successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
