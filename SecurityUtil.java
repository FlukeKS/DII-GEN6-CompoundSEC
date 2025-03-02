import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class SecurityUtil {
    public static String generateTimeBasedToken(String cardId) {
        String data = cardId + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        return sha256(data);
    }

    public static boolean verifyToken(String cardId, String token) {
        // สร้าง Token ใหม่เพื่อเปรียบเทียบ
        String newToken = generateTimeBasedToken(cardId);
        return newToken.equals(token);
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