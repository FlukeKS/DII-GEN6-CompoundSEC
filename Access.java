import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

// Observer Interface
interface SecurityObserver {
    void update(String log);
}

// บันทึก Log
class AuditLogger implements SecurityObserver {
    private List<String> logs = new ArrayList<>();

    @Override
    public void update(String log) {
        logs.add(log);
        System.out.println("Audit Log: " + log);
    }
}

// ระบบแจ้งเตือนความปลอดภัย
class SecurityAlert implements SecurityObserver {
    @Override
    public void update(String log) {
        System.out.println("🚨 SECURITY ALERT: " + log);
    }
}

// ระบบควบคุมการเข้าถึง
class AccessControl {
    private List<SecurityObserver> observers = new ArrayList<>();

    public void addObserver(SecurityObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SecurityObserver observer) {
        observers.remove(observer);
    }

    public void accessAttempt(AccessCard card, String zone) {
        boolean success = card.hasAccess(zone);
        String log = LocalDateTime.now() + " | Card: " + card.getCardID() + " | Zone: " + zone + " | Access: " + (success ? "GRANTED" : "DENIED");

        // แจ้ง Observer ทุกตัว
        for (SecurityObserver observer : observers) {
            observer.update(log);
        }
    }
}
