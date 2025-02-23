public class Main {
    public static void main(String[] args) {

        // สร้างระบบควบคุมการเข้าถึง
        AccessControl system = new AccessControl();

        // เพิ่ม Observer (ระบบบันทึกและแจ้งเตือน)
        system.addObserver(new AuditLogger());
        system.addObserver(new SecurityAlert());

        // สร้างบัตรผ่านจาก Factory
        AccessCard guest = CardFactory.createCard("Guest", "GUEST-001");
        AccessCard staff = CardFactory.createCard("Staff", "STAFF-002");
        AccessCard admin = CardFactory.createCard("Admin", "ADMIN-003");

        // ทดสอบการใช้งานบัตร
        system.accessAttempt(guest, "Staff Room"); // ✅ เข้าได้
        system.accessAttempt(staff, "Admin Room");
        system.accessAttempt(admin, "Guest Room");
    }
}
