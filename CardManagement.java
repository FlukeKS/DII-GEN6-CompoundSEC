import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManagement {
    private static Map<String, AccessCard> cardRegistry = new HashMap<>();


    // เพิ่ม method นี้เพื่อให้คลาสอื่นสามารถเข้าถึง cardRegistry ได้
    public static Map<String, AccessCard> getCardRegistry() {
        return cardRegistry;
    }
    // เพิ่มบัตร
    public static void addCard(String cardId, LocalDateTime expiryDate, List<String> accessLevels, AccessLevel level) {
        if (cardRegistry.containsKey(cardId)) {
            System.out.println("❌ Card ID already exists!");
            return;
        }
        AccessCard card = new AccessCard(cardId, expiryDate, accessLevels, level);
        cardRegistry.put(cardId, card);
    }

    // แก้ไขบัตร
    public static void modifyCard(String cardId, LocalDateTime newExpiryDate, List<String> newAccessLevels) {
        AccessCard card = cardRegistry.get(cardId);
        if (card != null) {
            card.setExpiryDate(newExpiryDate);
            card.setAccessLevels(newAccessLevels);
        }
    }

    // เพิกถอนบัตร
    public static void revokeCard(String cardId) {
        cardRegistry.remove(cardId);
    }

    // ค้นหาบัตร
    public static AccessCard getCard(String cardId) {
        return cardRegistry.get(cardId);
    }

    // แสดงรายการบัตรทั้งหมด
    public static void showAllCards() {
        if (cardRegistry.isEmpty()) {
            System.out.println("No Card in System!");
        } else {
            for (Map.Entry<String, AccessCard> entry : cardRegistry.entrySet()) {
                AccessCard card = entry.getValue();
                System.out.println("ID: " + card.getCardId() + " | Expiry: " + card.getExpiryDate() + " | Access Levels: " + card.getAccessLevels());
            }
        }
    }
}