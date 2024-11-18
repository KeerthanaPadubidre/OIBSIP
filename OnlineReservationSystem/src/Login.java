import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {
    private Scanner scanner = new Scanner(System.in);

    public boolean authenticateUser() {
        System.out.println(" Do you have an account? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("no")) {
            registerNewUser();
        }

        System.out.println("=== Login ===");
        System.out.println("Enter Login ID: ");

        String loginId = scanner.nextLine();
        System.out.println(" Enter Password ");
        String password = scanner.nextLine();

        boolean isAuthenticated = false;
        try (
                Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Users WHERE login_id = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, loginId);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid login ID or password.");
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return isAuthenticated;
    }

    private void registerNewUser() {
        System.out.println("=== Register New User ===");
        System.out.print("Enter a new Login ID: ");
        String newLoginId = scanner.nextLine();
        System.out.print("Enter a new Password: ");
        String newPassword = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if login ID already exists
            String checkQuery = "SELECT * FROM Users WHERE login_id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, newLoginId);
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                System.out.println("Login ID already exists. Please try logging in.");
            } else {
                // Insert new user into Users table
                String insertQuery = "INSERT INTO Users (login_id, password) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, newLoginId);
                insertStatement.setString(2, newPassword);
                int rowsInserted = insertStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Registration successful! You can now log in.");
                } else {
                    System.out.println("Registration failed. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
}
