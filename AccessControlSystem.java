import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class AccessControlSystem {
    public static void verifyAccess(AccessCard card, String location) {
        LocalTime currentTime = LocalTime.now();

        // ตรวจสอบวันหมดอายุ
        if (card.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("Access denied: Card expired");
            AuditLogger.log("Access Denied", card.getCardId(), "Expired card tried to access " + location);
            return;
        }

        // ตรวจสอบเวลาที่บัตรสามารถใช้งานได้
        if (currentTime.isBefore(card.getValidFrom()) || currentTime.isAfter(card.getValidTo())) {
            System.out.println("Access denied: Card is not valid at this time");
            AuditLogger.log("Access Denied", card.getCardId(), "Card tried to access " + location + " outside valid time");
            return;
        }

        // ตรวจสอบสิทธิ์การเข้าถึง
        if (!card.getAccessLocations().contains(location)) {
            System.out.println("Access denied: Insufficient access level");
            AuditLogger.log("Access Denied", card.getCardId(), "Tried to access " + location);
            return;
        }

        System.out.println("Access granted to " + location);
        AuditLogger.log("Access Granted", card.getCardId(), "Accessed " + location);
    }
}