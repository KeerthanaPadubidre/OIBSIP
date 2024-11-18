import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATM {
    private static String userId;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");

        // User Login
        System.out.print("Enter User ID: ");
        String inputUserId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String inputPin = scanner.nextLine();

        if (authenticate(inputUserId, inputPin)) {
            userId = inputUserId;
            System.out.println("Login successful!");
            showMenu(scanner);
        } else {
            System.out.println("Authentication failed. Exiting program.");
        }

        scanner.close();
    }

    private static boolean authenticate(String inputUserId, String inputPin) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM accounts WHERE user_id = ? AND pin = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputUserId);
            statement.setString(2, inputPin);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> Account.viewBalance(userId);
                case 2 -> Withdraw.process(userId, scanner);
                case 3 -> Deposit.process(userId, scanner);
                case 4 -> Transfer.process(userId, scanner);
                case 5 -> {
                    System.out.println("Thank you for using the ATM!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
