// Interface สำหรับการตรวจสอบสิทธิ์การเข้าถึง
interface ZoneAccessControl {
    boolean grantAccess(String cardID, String zone);
}
