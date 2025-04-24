package iuh.fit.qlksfxappclient.service;

import iuh.fit.qlksfxapp.DAO.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.prefs.Preferences;

/**
 * Singleton service class for managing RMI connections to the server.
 * Provides access to all DAO interfaces through a single point.
 */
public class RMIService {
    private static RMIService instance;
    private final Registry registry;

    // Default RMI server settings
    private static final String DEFAULT_SERVER_IP = "192.168.99.223";
    private static final int DEFAULT_SERVER_PORT = 9090;

    // User preferences for storing server settings
    private static final Preferences prefs = Preferences.userNodeForPackage(RMIService.class);

    // DAO interfaces - these are kept for future use but currently have no getters
    // Add getter methods as needed when implementing features
    private final TaiKhoanDAO taiKhoanDAO;
    private final CaLamViecDAO caLamViecDAO;
    private final HoaDonDAO hoaDonDAO;
    private final KhachHangDAO khachHangDAO;
    private final PhongDAO phongDAO;
    private final DichVuDAO dichVuDAO;
    private final NhanVienDAO nhanVienDAO;
    private final DonDatPhongDAO donDatPhongDAO;
    private final ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    private final ChiTietDichVuDAO chiTietDichVuDAO;
    private final BangPhanCongCaLamDAO bangPhanCongCaLamDAO;
    private final LoaiPhongDAO loaiPhongDAO;
    private final LoaiDichVuDAO loaiDichVuDAO;
    private final LoaiNhanVienDAO loaiNhanVienDAO;

    /**
     * Private constructor that initializes the RMI connection and looks up all DAO interfaces.
     * @throws RuntimeException if connection to the RMI server fails
     */
    private RMIService() {
        try {
            // Set security policy
            String projectDir = System.getProperty("user.dir");
            System.setProperty("java.security.policy", projectDir + "/rmi.policy");

            // Get server settings from preferences or use defaults
            String serverIP = prefs.get("server.ip", DEFAULT_SERVER_IP);
            int serverPort = prefs.getInt("server.port", DEFAULT_SERVER_PORT);

            System.out.println("Connecting to RMI server at " + serverIP + ":" + serverPort);

            // Get the registry
            registry = LocateRegistry.getRegistry(serverIP, serverPort);

            // Look up the remote objects
            taiKhoanDAO = (TaiKhoanDAO) registry.lookup("taiKhoanDAO");
            caLamViecDAO = (CaLamViecDAO) registry.lookup("caLamViecDAO");
            hoaDonDAO = (HoaDonDAO) registry.lookup("hoaDonDAO");
            khachHangDAO = (KhachHangDAO) registry.lookup("khachHangDAO");
            phongDAO = (PhongDAO) registry.lookup("phongDAO");
            dichVuDAO = (DichVuDAO) registry.lookup("dichVuDAO");
            nhanVienDAO = (NhanVienDAO) registry.lookup("nhanVienDAO");
            donDatPhongDAO = (DonDatPhongDAO) registry.lookup("donDatPhongDAO");
            chiTietDonDatPhongDAO = (ChiTietDonDatPhongDAO) registry.lookup("chiTietDonDatPhongDAO");
            chiTietDichVuDAO = (ChiTietDichVuDAO) registry.lookup("chiTietDichVuDAO");
            loaiPhongDAO = (LoaiPhongDAO) registry.lookup("loaiPhongDAO");
            loaiDichVuDAO = (LoaiDichVuDAO) registry.lookup("loaiDichVuDAO");
            loaiNhanVienDAO = (LoaiNhanVienDAO) registry.lookup("loaiNhanVienDAO");
            bangPhanCongCaLamDAO = (BangPhanCongCaLamDAO) registry.lookup("bangPhanCongCaLamDAO");

            System.out.println("Successfully connected to RMI server");

        } catch (Exception e) {
            System.err.println("RMI Service exception: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to RMI server: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the singleton instance of the RMIService.
     * @return The RMIService instance
     * @throws RuntimeException if connection to the RMI server fails
     */
    public static synchronized RMIService getInstance() {
        if (instance == null) {
            instance = new RMIService();
        }
        return instance;
    }

    /**
     * Gets the current server IP address.
     * @return The current server IP address
     */
    public static String getServerIP() {
        return prefs.get("server.ip", DEFAULT_SERVER_IP);
    }

    /**
     * Gets the current server port.
     * @return The current server port
     */
    public static int getServerPort() {
        return prefs.getInt("server.port", DEFAULT_SERVER_PORT);
    }

    /**
     * Changes the RMI server connection settings and reconnects.
     * @param serverIP The IP address of the RMI server
     * @param serverPort The port of the RMI server
     * @return true if connection was successful, false otherwise
     */
    public static boolean changeServerSettings(String serverIP, int serverPort) {
        try {
            // Save the new settings
            prefs.put("server.ip", serverIP);
            prefs.putInt("server.port", serverPort);

            // Reset the instance to force reconnection
            instance = null;

            // Try to connect with new settings
            getInstance();
            return true;
        } catch (Exception e) {
            System.err.println("Failed to connect to new server: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Note: DAO getter methods have been removed as they were not being used
    // If you need to access any DAO interface, add the appropriate getter method here
}
