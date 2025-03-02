import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class CardManagement {
    private static Map<String, AccessCard> cardRegistry = new HashMap<>();

    public static void addCard(String cardId, LocalDateTime expiryDate, List<String> accessLocations, AccessLevel level, LocalTime validFrom, LocalTime validTo) {
        AccessCard card = new AccessCard(cardId, expiryDate, accessLocations, level, validFrom, validTo);
        cardRegistry.put(cardId, card);
        AuditLogger.log("Card Added", cardId, "Expiry: " + expiryDate + ", Access Locations: " + accessLocations + ", Level: " + level + ", Valid From: " + validFrom + ", Valid To: " + validTo);
    }

    public static void modifyCard(String cardId, LocalDateTime newExpiryDate, List<String> newAccessLocations, AccessLevel newLevel, LocalTime newValidFrom, LocalTime newValidTo) {
        AccessCard card = cardRegistry.get(cardId);
        if (card != null) {
            card.setExpiryDate(newExpiryDate);
            card.setAccessLocations(newAccessLocations);
            card.setLevel(newLevel);
            card.setValidFrom(newValidFrom);
            card.setValidTo(newValidTo);
            AuditLogger.log("Card Modified", cardId, "New Expiry: " + newExpiryDate + ", New Access Locations: " + newAccessLocations + ", New Level: " + newLevel + ", New Valid From: " + newValidFrom + ", New Valid To: " + newValidTo);
        }
    }

    public static void revokeCard(String cardId) {
        AccessCard card = cardRegistry.remove(cardId);
        if (card != null) {
            AuditLogger.log("Card Revoked", cardId, "Card removed from system");
        }
    }

    public static AccessCard getCard(String cardId) {
        return cardRegistry.get(cardId);
    }

    // เพิ่มเมธอด getCardRegistry เพื่อให้ Main สามารถดึงข้อมูล cardRegistry ได้
    public static Map<String, AccessCard> getCardRegistry() {
        return cardRegistry;
    }
}