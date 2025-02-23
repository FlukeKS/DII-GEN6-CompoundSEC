import java.time.LocalDateTime;
import java.util.List;

public class AccessCard {
    private String cardId;
    private LocalDateTime expiryDate;
    private List<String> accessLevels;

    public AccessCard(String cardId, LocalDateTime expiryDate, List<String> accessLevels) {
        this.cardId = cardId;
        this.expiryDate = expiryDate;
        this.accessLevels = accessLevels;
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
}