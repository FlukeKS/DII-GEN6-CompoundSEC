//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class AuditLogger {
//    private static final String LOG_FILE = "audit_logs.txt";
//
//    public static void log(String message) {
//        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
//            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message + "\n");
//        } catch (IOException e) {
//            System.out.println("❌ Error writing log: " + e.getMessage());
//        }
//    }
//}