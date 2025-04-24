package iuh.fit.qlksfxapp.RMI;

import iuh.fit.qlksfxapp.DAO.*;
import iuh.fit.qlksfxapp.DAO.Impl.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

    public static void main(String[] args) throws Exception {
        // Set system properties for RMI
        System.setProperty("app.isServer", "true");

        // Set system properties for RMI
        String projectDir = System.getProperty("user.dir");
        System.setProperty("java.security.policy", projectDir + "/rmi.policy");
        System.setProperty("java.rmi.server.hostname", "192.168.99.223");
        // Create and initialize the registry
        Registry registry = LocateRegistry.createRegistry(9090);

        try {
            // Create DAO implementations
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAOImpl();
            CaLamViecDAO caLamViecDAO = new CaLamViecDAOImpl();
            HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
            KhachHangDAO khachHangDAO = new KhachHangDAOImpl();
            PhongDAO phongDAO = new PhongDAOImpl();
            DichVuDAO dichVuDAO = new DichVuDAOImpl();
            NhanVienDAO nhanVienDAO = new NhanVienDAOImpl();
            DonDatPhongDAO donDatPhongDAO = new DonDatPhongDAOImpl();
            ChiTietDonDatPhongDAO chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAOImpl();
            ChiTietDichVuDAO chiTietDichVuDAO = new ChiTietDichVuDAOImpl();
            LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAOImpl();
            LoaiDichVuDAO loaiDichVuDAO = new LoaiDichVuDAOImpl();
            LoaiNhanVienDAO loaiNhanVienDAO = new LoaiNhanVienDAOImpl();
            BangPhanCongCaLamDAO bangPhanCongCaLamDAO = new BangPhanCongCaLamDAOImpl();

            // Export Remote objects
            TaiKhoanDAO remoteTaiKhoanDAO = (TaiKhoanDAO) UnicastRemoteObject.exportObject(taiKhoanDAO, 0);
            CaLamViecDAO remoteCaLamViecDAO = (CaLamViecDAO) UnicastRemoteObject.exportObject(caLamViecDAO, 0);
            HoaDonDAO remoteHoaDonDAO = (HoaDonDAO) UnicastRemoteObject.exportObject(hoaDonDAO, 0);
            KhachHangDAO remoteKhachHangDAO = (KhachHangDAO) UnicastRemoteObject.exportObject(khachHangDAO, 0);
            PhongDAO remotePhongDAO = (PhongDAO) UnicastRemoteObject.exportObject(phongDAO, 0);
            DichVuDAO remoteDichVuDAO = (DichVuDAO) UnicastRemoteObject.exportObject(dichVuDAO, 0);
            NhanVienDAO remoteNhanVienDAO = (NhanVienDAO) UnicastRemoteObject.exportObject(nhanVienDAO, 0);
            DonDatPhongDAO remoteDonDatPhongDAO = (DonDatPhongDAO) UnicastRemoteObject.exportObject(donDatPhongDAO, 0);
            ChiTietDonDatPhongDAO remoteChiTietDonDatPhongDAO = (ChiTietDonDatPhongDAO) UnicastRemoteObject.exportObject(chiTietDonDatPhongDAO, 0);
            ChiTietDichVuDAO remoteChiTietDichVuDAO = (ChiTietDichVuDAO) UnicastRemoteObject.exportObject(chiTietDichVuDAO, 0);
            LoaiPhongDAO remoteLoaiPhongDAO = (LoaiPhongDAO) UnicastRemoteObject.exportObject(loaiPhongDAO, 0);
            LoaiDichVuDAO remoteLoaiDichVuDAO = (LoaiDichVuDAO) UnicastRemoteObject.exportObject(loaiDichVuDAO, 0);
            LoaiNhanVienDAO remoteLoaiNhanVienDAO = (LoaiNhanVienDAO) UnicastRemoteObject.exportObject(loaiNhanVienDAO, 0);
            BangPhanCongCaLamDAO remoteBangPhanCongCaLamDAO = (BangPhanCongCaLamDAO) UnicastRemoteObject.exportObject(bangPhanCongCaLamDAO, 0);

            // Bind the remote objects to the registry
            registry.rebind("taiKhoanDAO", remoteTaiKhoanDAO);
            registry.rebind("caLamViecDAO", remoteCaLamViecDAO);
            registry.rebind("hoaDonDAO", remoteHoaDonDAO);
            registry.rebind("khachHangDAO", remoteKhachHangDAO);
            registry.rebind("phongDAO", remotePhongDAO);
            registry.rebind("dichVuDAO", remoteDichVuDAO);
            registry.rebind("nhanVienDAO", remoteNhanVienDAO);
            registry.rebind("donDatPhongDAO", remoteDonDatPhongDAO);
            registry.rebind("chiTietDonDatPhongDAO", remoteChiTietDonDatPhongDAO);
            registry.rebind("chiTietDichVuDAO", remoteChiTietDichVuDAO);
            registry.rebind("loaiPhongDAO", remoteLoaiPhongDAO);
            registry.rebind("loaiDichVuDAO", remoteLoaiDichVuDAO);
            registry.rebind("loaiNhanVienDAO", remoteLoaiNhanVienDAO);
            registry.rebind("bangPhanCongCaLamDAO", remoteBangPhanCongCaLamDAO);

            System.out.println("RMI Server is ready!");
            System.out.println("All DAO services bound to registry");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
