import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Withdraw {
    public static void process(String userId, Scanner scanner) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE accounts SET balance = balance - ? WHERE user_id = ? AND balance >= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, amount);
            statement.setString(2, userId);
            statement.setDouble(3, amount);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
