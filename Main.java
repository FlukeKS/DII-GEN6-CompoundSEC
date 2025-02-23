import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Add");
            System.out.println("2. Modify");
            System.out.println("3. Revoke");
            System.out.println("4. Access Check");
            System.out.println("5. Show All Card");
            System.out.println("6. Exit");
            System.out.print("Select (1-6): ");

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
                    verifyAccess(scanner);
                    break;
                case 5:
                    showAllCards();
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
        System.out.print("🔹 Input ID Card: ");
        String cardId = scanner.nextLine();

        System.out.print("🔹 The Day of Expired  (e.g. 30 Day input 30): ");
        int days = scanner.nextInt();
        scanner.nextLine();

        System.out.print("🔹 Select Access (Split with , e.g. LOW,ROOM101): ");
        String accessInput = scanner.nextLine();
        List<String> accessLevels = Arrays.asList(accessInput.split(","));

        CardManagement.addCard(cardId, LocalDateTime.now().plusDays(days), accessLevels);
        System.out.println("✅ Add Card Success!");
    }

    private static void modifyCard(Scanner scanner) {
        System.out.print("🔹 Input ID Card : ");
        String cardId = scanner.nextLine();

        System.out.print("🔹 New Day of Expire (e.g. 60 Days Input 60): ");
        int days = scanner.nextInt();
        scanner.nextLine();

        System.out.print("🔹 New Access (Split with , e.g. HIGH,ROOM202): ");
        String accessInput = scanner.nextLine();
        List<String> newAccessLevels = Arrays.asList(accessInput.split(","));

        CardManagement.modifyCard(cardId, LocalDateTime.now().plusDays(days), newAccessLevels);
        System.out.println("✅ Modify Success!");
    }

    private static void revokeCard(Scanner scanner) {
        System.out.print("🔹 Input ID Card: ");
        String cardId = scanner.nextLine();

        CardManagement.revokeCard(cardId);
        System.out.println("✅ Revoke Success!");
    }

    private static void verifyAccess(Scanner scanner) {
        System.out.print("🔹 Input ID Card: ");
        String cardId = scanner.nextLine();
        AccessCard card = CardManagement.getCard(cardId);

        if (card == null) {
            System.out.println("❌ Not Found!");
            return;
        }

        System.out.print("🔹 Select Access (e.g. LOW or ROOM101): ");
        String location = scanner.nextLine();

        System.out.print("🔹 Is it room? (true = Room, false = Floor): ");
        boolean isRoom = scanner.nextBoolean();
        scanner.nextLine();

        AccessControlSystem.verifyAccess(card, location, isRoom);
    }

    private static void showAllCards() {
        System.out.println("\n📋 All Card:");
        CardManagement.showAllCards();
    }
}