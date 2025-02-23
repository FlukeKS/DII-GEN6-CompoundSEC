import java.time.LocalDateTime;

public class AccessControlSystem {

    // ตรวจสอบการเข้าถึง
    public static void verifyAccess(AccessCard card, String location, boolean isRoom) {
        if (card.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("❌ บัตรหมดอายุแล้ว!");
            return;
        }

        // ตรวจสอบการเข้าถึง
        if (card.getAccessLevels().contains(location)) {
            if (isRoom && location.startsWith("ROOM")) {
                System.out.println("✅ เข้าถึงห้อง " + location + " ได้");
            } else if (!isRoom && location.startsWith("LOW")) {
                System.out.println("✅ เข้าถึงชั้น " + location + " ได้");
            } else {
                System.out.println("❌ ไม่สามารถเข้าถึงที่นี้ได้");
            }
        } else {
            System.out.println("❌ บัตรนี้ไม่สามารถเข้าถึง " + location + " ได้");
        }
    }
}