package iuh.fit.qlksfxapp.service;

import iuh.fit.qlksfxapp.DAO.*;
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

            // Get the registry
            Registry registry = LocateRegistry.getRegistry("192.168.0.223", 9090);

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

        } catch (Exception e) {
            System.err.println("RMI Service exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static synchronized RMIService getInstance() {
        if (instance == null) {
            instance = new RMIService();
        }
        return instance;
    }

}
