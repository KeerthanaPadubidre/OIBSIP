import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Reservation {
    public void reserveTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Train Number:");
        String trainNumber = scanner.nextLine();
        System.out.println("Enter Train Name:");
        String trainName = scanner.nextLine();
        System.out.println("Enter Class Type (e.g., First Class, Sleeper):");
        String classType = scanner.nextLine();
        System.out.println("Enter Date of Journey (YYYY-MM-DD):");
        String journeyDate = scanner.nextLine();
        System.out.println("Enter From (Place):");
        String from = scanner.nextLine();
        System.out.println("Enter To (Destination):");
        String to = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Reservations (train_number, train_name, class_type, journey_date, from_place, to_place) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, trainNumber);
            preparedStatement.setString(2, trainName);
            preparedStatement.setString(3, classType);
            preparedStatement.setString(4, journeyDate);
            preparedStatement.setString(5, from);
            preparedStatement.setString(6, to);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Reservation successful!");
            } else {
                System.out.println("Reservation failed.");
            }
        } catch (Exception e) {
            System.out.println("Reservation error: " + e.getMessage());
        }
    }
}
