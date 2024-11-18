import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public static void viewBalance(String userId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT balance FROM accounts WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Current Balance: " + resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
