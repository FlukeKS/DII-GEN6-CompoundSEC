import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n1. Add");
            System.out.println("2. Modify");
            System.out.println("3. Revoke");
            System.out.println("4. Show All Card");
//            System.out.println("5. Access Check");
            System.out.println("5. Exit");
            System.out.print("Select (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addNewCard(scanner);
                    break;
                case 2:
                    modifyCard(scanner);
                    break;
                case 3:
                    revokeCard(scanner);
                    break;
                case 4:
                    showAllCards();
                    break;
                case 5:
                    verifyAccess(scanner);
                    break;
                case 6:
                    running = false;
                    System.out.println("🚪 Exiting...");
                    break;
                default:
                    System.out.println("❌ Please Select (1-6)");
            }
        }
        scanner.close();
    }

    private static void addNewCard(Scanner scanner) {
        System.out.print("🔹 Enter card ID: ");
        String cardId = scanner.nextLine();

        System.out.print("🔹 Set expiry date  (e.g., for 30 days, enter 30): ");
        int days = scanner.nextInt();
        scanner.nextLine();

        System.out.print("🔹 Access levels (Split with , e.g., LOW,ROOM101): ");
        String accessInput = scanner.nextLine();
        List<String> accessLevels = Arrays.asList(accessInput.split(","));

        CardManagement.addCard(cardId, LocalDateTime.now().plusDays(days), accessLevels);
        System.out.println("✅ Card added successfully!");
    }

    private static void modifyCard(Scanner scanner) {
        System.out.print("🔹 Enter card ID: ");
        String cardId = scanner.nextLine();

        System.out.print("🔹 Set new expiry date (e.g., for 60 days, enter 60): ");
        int days = scanner.nextInt();
        scanner.nextLine();

        System.out.print("🔹 New access levels (Split with , e.g., HIGH,ROOM202): ");
        String accessInput = scanner.nextLine();
        List<String> newAccessLevels = Arrays.asList(accessInput.split(","));

        CardManagement.modifyCard(cardId, LocalDateTime.now().plusDays(days), newAccessLevels);
        System.out.println("✅ Card modified successfully!");
    }

    private static void revokeCard(Scanner scanner) {
        System.out.print("🔹 Enter card ID: ");
        String cardId = scanner.nextLine();

        CardManagement.revokeCard(cardId);
        System.out.println("✅ Card revoked successfully!");
    }

    private static void verifyAccess(Scanner scanner) {
        System.out.print("🔹 Enter card ID: ");
        String cardId = scanner.nextLine();
        AccessCard card = CardManagement.getCard(cardId);

        if (card == null) {
            System.out.println("❌ Not Found!");
            return;
        }

        System.out.print("🔹 Select access levels (e.g. LOW or ROOM101): ");
        String location = scanner.nextLine();

        System.out.print("🔹 Is it room? (true = room, false = floor): ");
        boolean isRoom = scanner.nextBoolean();
        scanner.nextLine();

        AccessControlSystem.verifyAccess(card, location, isRoom);
    }

    private static void showAllCards() {
        System.out.println("\n📋 All Card:");
        CardManagement.showAllCards();
    }
}