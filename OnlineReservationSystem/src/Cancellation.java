import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Cancellation {
    public void cancelTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter PNR Number for Cancellation:");
        String pnrNumber = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String fetchQuery = "SELECT * FROM Reservations WHERE pnr_number = ?";
            PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery);
            fetchStatement.setString(1, pnrNumber);
            ResultSet resultSet = fetchStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Reservation found with the following details:");
                System.out.println("Train Number: " + resultSet.getString("train_number"));
                System.out.println("Train Name: " + resultSet.getString("train_name"));
                System.out.println("Class Type: " + resultSet.getString("class_type"));
                System.out.println("Journey Date: " + resultSet.getString("journey_date"));
                System.out.println("From: " + resultSet.getString("from_place"));
                System.out.println("To: " + resultSet.getString("to_place"));

                System.out.println("Are you sure you want to cancel this reservation? (yes/no):");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    String deleteQuery = "DELETE FROM Reservations WHERE pnr_number = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setString(1, pnrNumber);

                    int rowsDeleted = deleteStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Cancellation successful!");
                    } else {
                        System.out.println("Cancellation failed.");
                    }
                } else {
                    System.out.println("Cancellation aborted.");
                }
            } else {
                System.out.println("No reservation found with the provided PNR number.");
            }
        } catch (Exception e) {
            System.out.println("Cancellation error: " + e.getMessage());
        }
    }
}
