package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.ChiTietPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ChiTietPhongDAO extends GeneralDAO{
//    private EntityManager em =null;
//    public ChiTietPhongDAO() {
//        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    }

     ChiTietDonDatPhong findChiTietWithKhachHang(String maChiTiet) throws RemoteException;
     ChiTietDonDatPhong findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(String maDonDatPhong, String maPhong) throws RemoteException;
     List<ChiTietDonDatPhong> findChiTietDonDatPhongTheoMaDonDatPhong(String maDonDatPhong) throws RemoteException;
     Double getTongTienDichVuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) throws RemoteException;
     Double getTienPhongTheoMaChiTietDonDatPhong(String ma) throws RemoteException;
     Double getTongTienPhuThuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) throws RemoteException;
     double getTongTienDichVuByMaDonDatPhong(String ma) throws RemoteException;
     double getTongTienPhuThuByMaDonDatPhong(String ma) throws RemoteException;
     double getTongTienPhongByMaDonDatPhong(String ma) throws RemoteException;
     double getTongTienByNgay(LocalDate startDate, LocalDate endDate) throws RemoteException;

}
