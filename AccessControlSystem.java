import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class AccessControlSystem {
    public static void verifyAccess(AccessCard card, String location, boolean isRoom) {
        if (card.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("❌ The Card Expired!");
            AuditLogger.log("Access Denied - Expired Card: " + card.getCardId());
            return;
        }

        AccessLevel requiredLevel = getLocationLevel(location);
        if (card.getLevel().ordinal() < requiredLevel.ordinal()) {
            System.out.println("❌ Access denied - Insufficient Level");
            AuditLogger.log("Access Denied - Card: " + card.getCardId() + " (" + card.getLevel() + ") tried to access " + location + " (" + requiredLevel + ")");
            return;
        }

        if (card.getAccessLevels().contains(location)) {
            String token = SecurityUtil.generateTimeBasedToken(card.getCardId());
            System.out.println("🔑 Time-Based Token: " + token);
            System.out.println("✅ Access granted to " + location);
            AuditLogger.log("Access Granted - Card: " + card.getCardId() + " to " + location);
        } else {
            System.out.println("❌ Access denied to " + location);
            AuditLogger.log("Access Denied - Card: " + card.getCardId() + " tried " + location);
        }
    }

    private static AccessLevel getLocationLevel(String location) {
        if (location.startsWith("HIGH")) return AccessLevel.HIGH;
        if (location.startsWith("MEDIUM")) return AccessLevel.MEDIUM;
        return AccessLevel.LOW;
    }
}