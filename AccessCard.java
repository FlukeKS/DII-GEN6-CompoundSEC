import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private List<String> accessLocations;
    private AccessLevel level;
    private LocalTime validFrom;  // เวลาเริ่มต้นที่บัตรสามารถใช้งานได้
    private LocalTime validTo;    // เวลาสิ้นสุดที่บัตรสามารถใช้งานได้

    public AccessCard(String cardId, LocalDateTime expiryDate, List<String> accessLocations, AccessLevel level, LocalTime validFrom, LocalTime validTo) {
        this.cardId = cardId;
        this.expiryDate = expiryDate;
        this.accessLocations = accessLocations;
        this.level = level;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    // Getters
    public String getCardId() {
        return cardId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public List<String> getAccessLocations() {
        return accessLocations;
    }

    public AccessLevel getLevel() {
        return level;
    }

    public LocalTime getValidFrom() {
        return validFrom;
    }

    public LocalTime getValidTo() {
        return validTo;
    }

    // Setters
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setAccessLocations(List<String> accessLocations) {
        this.accessLocations = accessLocations;
    }

    public void setLevel(AccessLevel level) {
        this.level = level;
    }

    public void setValidFrom(LocalTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalTime validTo) {
        this.validTo = validTo;
    }
}

class AuditLogger {
    private static final String LOG_FILE = "audit_logs.txt";

    public static void log(String action, String cardId, String details) {
        String logMessage = String.format(
                "%s - Action: %s, Card ID: %s, Details: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                action,
                cardId,
                details
        );

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            System.out.println("Error writing log: " + e.getMessage());
        }
    }
}