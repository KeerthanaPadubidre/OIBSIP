
import java.util.Scanner;

public class OnlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        Reservation reservation = new Reservation();
        Cancellation cancellation = new Cancellation();

        boolean isAuthenticated = false;

        while (true) {
            System.out.println("=== Online Reservation System ===");
            System.out.println("1. Login");
            System.out.println("2. Reserve Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("=== Login ===");
                    isAuthenticated = login.authenticateUser();
                    break;

                case 2:
                    if (isAuthenticated) {
                        System.out.println("=== Reserve Ticket ===");
                        reservation.reserveTicket();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;

                case 3:
                    if (isAuthenticated) {
                        System.out.println("=== Cancel Ticket ===");
                        cancellation.cancelTicket();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
        }
    }
}
