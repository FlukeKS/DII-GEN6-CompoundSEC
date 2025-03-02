import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Access Control System ===");
            System.out.println("1. Add New Card - เพิ่มบัตรใหม่");
            System.out.println("2. Modify Card - แก้ไขบัตร");
            System.out.println("3. Revoke Card - เพิกถอนบัตร");
            System.out.println("4. Show All Cards - แสดงบัตรทั้งหมด");
            System.out.println("5. Verify Access - ตรวจสอบสิทธิ์การเข้าถึง");
            System.out.println("6. Exit - ออกจากโปรแกรม");
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
                    System.out.println("❌ Invalid choice! Please select (1-6).");
            }
        }
        scanner.close();
    }

    private static void addNewCard(Scanner scanner) {
        System.out.println("\n🔹 Add New Card");
        System.out.println("Example: Card ID = CARD001, Expiry Date = 30 days, Room Number = 101, Floor = 1");

        System.out.print("Enter card ID: ");
        String cardId = scanner.nextLine();

        // ตรวจสอบว่าบัตรมีอยู่แล้วหรือไม่
        if (CardManagement.getCard(cardId) != null) {
            System.out.println("❌ Card ID already exists!");
            return;
        }

        // รับวันหมดอายุ
        System.out.print("Set expiry date (e.g., for 30 days, enter 30): ");
        int days = 0;
        try {
            days = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a number.");
            return;
        }

        // รับเลขห้อง
        System.out.print("Enter room number (e.g., 101, 202): ");
        String roomNumber = scanner.nextLine();
        List<String> accessLevels = Arrays.asList("ROOM" + roomNumber);  // เพิ่ม "ROOM" นำหน้าเลขห้อง

        // รับชั้นและแปลงเป็น AccessLevel
        System.out.print("Enter floor (1 = LOW, 2 = MEDIUM, 3 = HIGH): ");
        int floor = 0;
        try {
            floor = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a number (1, 2, or 3).");
            return;
        }

        AccessLevel level;
        switch (floor) {
            case 1:
                level = AccessLevel.LOW;
                break;
            case 2:
                level = AccessLevel.MEDIUM;
                break;
            case 3:
                level = AccessLevel.HIGH;
                break;
            default:
                System.out.println("❌ Invalid floor! Please enter 1, 2, or 3.");
                return;
        }

        // เพิ่มบัตรใหม่
        CardManagement.addCard(cardId, LocalDateTime.now().plusDays(days), accessLevels, level);
        System.out.println("✅ Card added successfully!");
        AuditLogger.log("Card Added - ID: " + cardId);
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
        AuditLogger.log("Card Modified - ID: " + cardId);
    }

    private static void revokeCard(Scanner scanner) {
        System.out.print("🔹 Enter card ID: ");
        String cardId = scanner.nextLine();

        CardManagement.revokeCard(cardId);
        System.out.println("✅ Card revoked successfully!");
        AuditLogger.log("Card Revoked - ID: " + cardId);
    }

    private static void verifyAccess(Scanner scanner) {
        System.out.print("🔹 Enter card ID: ");
        String cardId = scanner.nextLine();
        AccessCard card = CardManagement.getCard(cardId);

        if (card == null) {
            System.out.println("❌ Not Found!");
            AuditLogger.log("Access Attempt - Failed (Card Not Found) - ID: " + cardId);
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
        System.out.println("\n📋 All Access Cards:");
        System.out.println("+------------+---------------------+-------------------+-----------------+");
        System.out.println("| Card ID    | Expiry Date         | Access Levels     | Access Level    |");
        System.out.println("+------------+---------------------+-------------------+-----------------+");

        if (CardManagement.getCardRegistry().isEmpty()) {
            System.out.println("| No cards found in the system!                                      |");
        } else {
            for (AccessCard card : CardManagement.getCardRegistry().values()) {
                System.out.printf(
                        "| %-10s | %-19s | %-17s | %-15s |\n",
                        card.getCardId(),
                        card.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        String.join(", ", card.getAccessLevels()),
                        card.getLevel()
                );
            }
        }
        System.out.println("+------------+---------------------+-------------------+-----------------+");
    }
}