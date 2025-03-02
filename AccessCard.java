import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

enum AccessLevel {
    LOW, MEDIUM, HIGH;
}

class AccessCard {
    private String cardId;
    private LocalDateTime expiryDate;
    private List<String> accessLevels;
    private AccessLevel level;

    public AccessCard(String cardId, LocalDateTime expiryDate, List<String> accessLevels, AccessLevel level) {
        this.cardId = cardId;
        this.expiryDate = expiryDate;
        this.accessLevels = accessLevels;
        this.level = level;
    }
    public void setLevel(AccessLevel level) {
        this.level = level;
    }

    public String getCardId() {
        return cardId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<String> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(List<String> accessLevels) {
        this.accessLevels = accessLevels;
    }

    public AccessLevel getLevel() {
        return level;
    }
}

class AuditLogger {
    private static final String LOG_FILE = "audit_logs.txt";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message + "\n");
        } catch (IOException e) {
            System.out.println("❌ Error writing log: " + e.getMessage());
        }
    }
}

class SecurityUtil {
    public static String generateTimeBasedToken(String cardId) {
        String data = cardId + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        return sha256(data);
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating SHA-256 hash", e);
        }
    }
}
