import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Transfer {
    public static void process(String userId, Scanner scanner) {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.next();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Deduct from sender
            String deductQuery = "UPDATE accounts SET balance = balance - ? WHERE user_id = ? AND balance >= ?";
            try (PreparedStatement deductStmt = connection.prepareStatement(deductQuery)) {
                deductStmt.setDouble(1, amount);
                deductStmt.setString(2, userId);
                deductStmt.setDouble(3, amount);
                if (deductStmt.executeUpdate() == 0) {
                    System.out.println("Insufficient funds or invalid transfer.");
                    connection.rollback();
                    return;
                }
            }

            // Add to recipient
            String addQuery = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
            try (PreparedStatement addStmt = connection.prepareStatement(addQuery)) {
                addStmt.setDouble(1, amount);
                addStmt.setString(2, recipientId);
                addStmt.executeUpdate();
            }

            connection.commit();
            System.out.println("Transfer successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = DatabaseConnection.getConnection()) {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }
}
