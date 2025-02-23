// คลาสแม่ของบัตรผ่าน
abstract class AccessCard {
    protected String cardID;

    public AccessCard(String cardID) {
        this.cardID = cardID;
    }

    public abstract boolean hasAccess(String zone);

    public String getCardID() {
        return cardID;
    }
}

// บัตรแขก
class GuestCard extends AccessCard {
    public GuestCard(String cardID) {
        super(cardID);
    }

    @Override
    public boolean hasAccess(String zone) {
        return zone.equals("Guest Room") || zone.equals("Public Zone");
    }
}

// บัตรพนักงาน
class StaffCard extends AccessCard {
    public StaffCard(String cardID) {
        super(cardID);
    }

    @Override
    public boolean hasAccess(String zone) {
        return zone.equals("Staff Area") || zone.equals("Public Zone");
    }
}

// บัตรผู้ดูแลระบบ (เข้าถึงทุกพื้นที่)
class AdminCard extends AccessCard {
    public AdminCard(String cardID) {
        super(cardID);
    }

    @Override
    public boolean hasAccess(String zone) {
        return true; // Admin เข้าถึงทุกพื้นที่
    }
}
