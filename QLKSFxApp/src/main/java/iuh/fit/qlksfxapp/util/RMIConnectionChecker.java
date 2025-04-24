package iuh.fit.qlksfxapp.util;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Công cụ kiểm tra kết nối RMI
 */
public class RMIConnectionChecker {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Kiểm tra kết nối RMI ===");
            
            // Thiết lập chính sách bảo mật
            String projectDir = System.getProperty("user.dir");
            System.setProperty("java.security.policy", projectDir + "/rmi.policy");
            
            // Kết nối đến registry
            System.out.println("Đang kết nối đến RMI registry trên localhost:9090...");
            Registry registry = LocateRegistry.getRegistry("localhost", 9090);
            
            // Liệt kê tất cả các đối tượng đã đăng ký
            System.out.println("Danh sách các đối tượng đã đăng ký trong registry:");
            String[] names = registry.list();
            
            if (names.length == 0) {
                System.out.println("Không tìm thấy đối tượng nào trong registry.");
                System.out.println("Vui lòng kiểm tra xem RMI Server đã được khởi động chưa.");
            } else {
                for (String name : names) {
                    System.out.println("  - " + name);
                }
                System.out.println("Kết nối RMI thành công! Tìm thấy " + names.length + " đối tượng.");
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi kết nối RMI: " + e.getMessage());
            System.err.println("\nNguyên nhân có thể là:");
            System.err.println("1. RMI Server chưa được khởi động");
            System.err.println("2. Cổng 9090 bị chặn bởi tường lửa");
            System.err.println("3. Địa chỉ IP không đúng");
            e.printStackTrace();
        }
    }
}
