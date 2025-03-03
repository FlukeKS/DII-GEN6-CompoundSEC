import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        deleteLogs();

        while (running) {
            System.out.println("\n=== Access Control System ===");
            System.out.println("1. Add New Card");
            System.out.println("2. Modify Card");
            System.out.println("3. Revoke Card");
            System.out.println("4. Verify Access");
            System.out.println("5. View Logs");
            System.out.println("6. Show All Cards");
            System.out.println("7. Exit");
            System.out.print("Select (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    viewLogs();
                    break;
                case 6:
                    showAllCards();
                    break;
                    case 7:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please select (1-7).");
            }
        }
        scanner.close();
    }

    private static void addNewCard(Scanner scanner) {
        System.out.print("Enter card ID: ");
        String cardId = scanner.nextLine();

        System.out.print("Set expiry date (in days): ");
        int days = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter access locations (comma-separated): ");
        String[] locations = scanner.nextLine().split(",");
        List<String> accessLocations = Arrays.asList(locations);

        System.out.print("Enter access level (1 = LOW, 2 = MEDIUM, 3 = HIGH): ");
        int level = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter valid from time (HH:mm): ");
        String validFromStr = scanner.nextLine();
        LocalTime validFrom = LocalTime.parse(validFromStr, DateTimeFormatter.ofPattern("HH:mm"));

        System.out.print("Enter valid to time (HH:mm): ");
        String validToStr = scanner.nextLine();
        LocalTime validTo = LocalTime.parse(validToStr, DateTimeFormatter.ofPattern("HH:mm"));

        AccessLevel accessLevel = AccessLevel.values()[level - 1];

        CardManagement.addCard(cardId, LocalDateTime.now().plusDays(days), accessLocations, accessLevel, validFrom, validTo);
        System.out.println("Card added successfully!");
    }

    private static void modifyCard(Scanner scanner) {
        System.out.print("Enter card ID to modify: ");
        String cardId = scanner.nextLine();

        AccessCard card = CardManagement.getCard(cardId);
        if (card == null) {
            System.out.println("Card not found!");
            return;
        }

        // รับวันหมดอายุใหม่
        System.out.print("Set new expiry date (in days): ");
        int days = scanner.nextInt();
        scanner.nextLine(); 

        // รับพื้นที่เข้าถึงใหม่
        System.out.print("Enter new access locations (comma-separated): ");
        String[] locations = scanner.nextLine().split(",");
        List<String> newAccessLocations = Arrays.asList(locations);

        // รับระดับการเข้าถึงใหม่
        System.out.print("Enter new access level (1 = LOW, 2 = MEDIUM, 3 = HIGH): ");
        int level = scanner.nextInt();
        scanner.nextLine(); 
        AccessLevel newLevel = AccessLevel.values()[level - 1];

        // รับเวลาเริ่มต้นใหม่
        System.out.print("Enter new valid from time (HH:mm): ");
        String validFromStr = scanner.nextLine();
        LocalTime newValidFrom = LocalTime.parse(validFromStr, DateTimeFormatter.ofPattern("HH:mm"));

        // รับเวลาสิ้นสุดใหม่
        System.out.print("Enter new valid to time (HH:mm): ");
        String validToStr = scanner.nextLine();
        LocalTime newValidTo = LocalTime.parse(validToStr, DateTimeFormatter.ofPattern("HH:mm"));

        // แก้ไขบัตร
        CardManagement.modifyCard(cardId, LocalDateTime.now().plusDays(days), newAccessLocations, newLevel, newValidFrom, newValidTo);
        System.out.println("Card modified successfully!");
    }

    private static void revokeCard(Scanner scanner) {
        System.out.print("Enter card ID to revoke: ");
        String cardId = scanner.nextLine();

        CardManagement.revokeCard(cardId);
        System.out.println("Card revoked successfully!");
    }

    private static void verifyAccess(Scanner scanner) {
        System.out.print("Enter card ID: ");
        String cardId = scanner.nextLine();

        AccessCard card = CardManagement.getCard(cardId);
        if (card == null) {
            System.out.println("Card not found!");
            return;
        }

        System.out.print("Enter location to access: ");
        String location = scanner.nextLine();

        AccessControlSystem.verifyAccess(card, location);
    }

    // ฟังก์ชัน View Logs
    private static void viewLogs() {
        System.out.println("\n=== Audit Logs ===");
        try {
            List<String> logs = Files.readAllLines(Paths.get("audit_logs.txt"));
            if (logs.isEmpty()) {
                System.out.println("No logs found.");
            } else {
                for (String log : logs) {
                    System.out.println(log);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading logs: " + e.getMessage());
        }
    }

    private static void showAllCards() {
        System.out.println("\n📋 All Access Cards:");
        System.out.println("+------------+---------------------+-------------------+-----------------+----------------+----------------+");
        System.out.println("| Card ID    | Expiry Date         | Access Levels     | Access Level    | Valid From     | Valid To       |");
        System.out.println("+------------+---------------------+-------------------+-----------------+----------------+----------------+");

        // เรียกใช้ getCardRegistry เพื่อดึงข้อมูลบัตรทั้งหมด
        Map<String, AccessCard> cardRegistry = CardManagement.getCardRegistry();
        if (cardRegistry.isEmpty()) {
            System.out.println("| No cards found in the system!                                                              |");
        } else {
            for (AccessCard card : cardRegistry.values()) {
                System.out.printf(
                        "| %-10s | %-19s | %-17s | %-15s | %-14s | %-14s |\n",
                        card.getCardId(),
                        card.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        String.join(", ", card.getAccessLocations()),
                        card.getLevel(),
                        card.getValidFrom(),
                        card.getValidTo()
                );
            }
        }
        System.out.println("+------------+---------------------+-------------------+-----------------+----------------+----------------+");
    }

    private static void deleteLogs() {
        try {
            Files.deleteIfExists(Paths.get("audit_logs.txt"));
            System.out.println("Previous logs deleted.");
        } catch (IOException e) {
            System.out.println("Error deleting logs: " + e.getMessage());
        }
    }
}