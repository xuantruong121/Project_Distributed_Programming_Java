package iuh.fit.qlksfxapp.service;

import iuh.fit.qlksfxapp.DAO.*;
import iuh.fit.qlksfxapp.DAO.Impl.LocalTaiKhoanDAOImpl;
import lombok.Getter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Getter
public class RMIService {
    private static RMIService instance;

    // Getter methods for DAO interfaces
    //
    private TaiKhoanDAO taiKhoanDAO;
    private CaLamViecDAO caLamViecDAO;
    private HoaDonDAO hoaDonDAO;
    private KhachHangDAO khachHangDAO;
    private PhongDAO phongDAO;
    private DichVuDAO dichVuDAO;
    private NhanVienDAO nhanVienDAO;
    private DonDatPhongDAO donDatPhongDAO;
    private ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    private ChiTietDichVuDAO chiTietDichVuDAO;
    private BangPhanCongCaLamDAO bangPhanCongCaLamDAO;
    private LoaiPhongDAO loaiPhongDAO;
    private LoaiDichVuDAO loaiDichVuDAO;
    private LoaiNhanVienDAO loaiNhanVienDAO;

    private RMIService() {
        try {
            // Set security policy
            String projectDir = System.getProperty("user.dir");
            System.setProperty("java.security.policy", projectDir + "/rmi.policy");

            // Set timeout for RMI connections
            System.setProperty("sun.rmi.transport.tcp.responseTimeout", "30000"); // 30 seconds
            System.setProperty("sun.rmi.transport.tcp.readTimeout", "30000"); // 30 seconds

            System.out.println("Attempting to connect to RMI registry on 192.168.99.223:9090...");

            // Get the registry
            Registry registry = LocateRegistry.getRegistry("192.168.99.105", 9090);

            // Check if registry is available by listing entries
            String[] services = registry.list();
            System.out.println("Found " + services.length + " services in RMI registry");

            if (services.length == 0) {
                throw new Exception("No services found in RMI registry. Make sure RMI Server is running.");
            }

            // Look up the remote objects
            System.out.println("Looking up remote objects...");

            System.out.println("Looking up taiKhoanDAO...");
            taiKhoanDAO = (TaiKhoanDAO) registry.lookup("taiKhoanDAO");
            System.out.println("taiKhoanDAO found successfully");

            System.out.println("Looking up caLamViecDAO...");
            caLamViecDAO = (CaLamViecDAO) registry.lookup("caLamViecDAO");
            System.out.println("caLamViecDAO found successfully");

            System.out.println("Looking up hoaDonDAO...");
            hoaDonDAO = (HoaDonDAO) registry.lookup("hoaDonDAO");
            System.out.println("hoaDonDAO found successfully");

            System.out.println("Looking up khachHangDAO...");
            khachHangDAO = (KhachHangDAO) registry.lookup("khachHangDAO");
            System.out.println("khachHangDAO found successfully");

            System.out.println("Looking up phongDAO...");
            phongDAO = (PhongDAO) registry.lookup("phongDAO");
            System.out.println("phongDAO found successfully");

            System.out.println("Looking up dichVuDAO...");
            dichVuDAO = (DichVuDAO) registry.lookup("dichVuDAO");
            System.out.println("dichVuDAO found successfully");

            System.out.println("Looking up nhanVienDAO...");
            nhanVienDAO = (NhanVienDAO) registry.lookup("nhanVienDAO");
            System.out.println("nhanVienDAO found successfully");

            System.out.println("Looking up donDatPhongDAO...");
            donDatPhongDAO = (DonDatPhongDAO) registry.lookup("donDatPhongDAO");
            System.out.println("donDatPhongDAO found successfully");

            System.out.println("Looking up chiTietDonDatPhongDAO...");
            chiTietDonDatPhongDAO = (ChiTietDonDatPhongDAO) registry.lookup("chiTietDonDatPhongDAO");
            System.out.println("chiTietDonDatPhongDAO found successfully");

            System.out.println("Looking up chiTietDichVuDAO...");
            chiTietDichVuDAO = (ChiTietDichVuDAO) registry.lookup("chiTietDichVuDAO");
            System.out.println("chiTietDichVuDAO found successfully");

            System.out.println("Looking up loaiPhongDAO...");
            loaiPhongDAO = (LoaiPhongDAO) registry.lookup("loaiPhongDAO");
            System.out.println("loaiPhongDAO found successfully");

            System.out.println("Looking up loaiDichVuDAO...");
            loaiDichVuDAO = (LoaiDichVuDAO) registry.lookup("loaiDichVuDAO");
            System.out.println("loaiDichVuDAO found successfully");

            System.out.println("Looking up loaiNhanVienDAO...");
            loaiNhanVienDAO = (LoaiNhanVienDAO) registry.lookup("loaiNhanVienDAO");
            System.out.println("loaiNhanVienDAO found successfully");

            System.out.println("Looking up bangPhanCongCaLamDAO...");
            bangPhanCongCaLamDAO = (BangPhanCongCaLamDAO) registry.lookup("bangPhanCongCaLamDAO");
            System.out.println("bangPhanCongCaLamDAO found successfully");

            System.out.println("RMI Service initialized successfully!");

        } catch (Exception e) {
            System.err.println("RMI Service exception: " + e.toString());
            e.printStackTrace();

            // Hiển thị thông báo lỗi chi tiết
            System.err.println("\nLỗi kết nối RMI. Vui lòng kiểm tra:");
            System.err.println("1. RMI Server đã được khởi động chưa?");
            System.err.println("2. Cổng 9090 có bị chặn bởi tường lửa không?");
            System.err.println("3. File rmi.policy đã được đặt đúng vị trí chưa?");

            // Sử dụng các triển khai cục bộ cho các DAO quan trọng
            System.out.println("Sử dụng triển khai cục bộ cho TaiKhoanDAO để cho phép đăng nhập offline");
            taiKhoanDAO = new LocalTaiKhoanDAOImpl();

            // Các DAO khác vẫn sẽ trả về null
        }
    }

    public static synchronized RMIService getInstance() {
        if (instance == null) {
            instance = new RMIService();
        }
        return instance;
    }

}
