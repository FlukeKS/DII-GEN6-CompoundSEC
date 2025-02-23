//// Factory สำหรับสร้างบัตรผ่านตามประเภทของผู้ใช้
//class CardFactory {
//    public static AccessCard createCard(String type, String cardID) {
//        switch (type) {
//            case "Guest": return new GuestCard(cardID);
//            case "Staff": return new StaffCard(cardID);
//            case "Admin": return new AdminCard(cardID);
//            default: throw new IllegalArgumentException("Invalid card type!");
//        }
//    }
//}