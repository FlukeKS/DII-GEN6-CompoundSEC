import java.time.LocalDateTime;

public class AccessControlSystem {

    // ตรวจสอบการเข้าถึง
    public static void verifyAccess(AccessCard card, String location, boolean isRoom) {
        if (card.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("❌ The Card Expired!");
            return;
        }

        // ตรวจสอบการเข้าถึง
        if (card.getAccessLevels().contains(location)) {
            if (isRoom && location.startsWith("ROOM")) {
                System.out.println("✅ Access to room " + location + " granted");
            } else if (!isRoom && location.startsWith("LOW")) {
                System.out.println("✅ Access to floor " + location + " granted");
            } else {
                System.out.println("❌ Access to this location is not allowed");
            }
        } else {
            System.out.println("❌ This card cannot access " + location);
        }
    }
}