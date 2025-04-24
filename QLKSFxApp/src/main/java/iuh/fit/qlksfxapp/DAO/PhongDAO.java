package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;

import java.time.LocalDateTime;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for Phong DAO operations
 */
public interface PhongDAO extends GeneralDAO {

    /**
     * Get all rooms
     * @return A list of all rooms
     */
    List<Phong> getAllPhong() throws RemoteException;

    /**
     * Find room by ID
     * @param maPhong The ID of the room
     * @return The found room or null if not found
     */
    Phong findByMaPhong(String maPhong) throws RemoteException;

    /**
     * Find rooms by status
     * @param trangThaiPhong The status to search for
     * @return A list of rooms with the specified status
     */
    List<Phong> findByTrangThai(TrangThaiPhong trangThaiPhong) throws RemoteException;

    /**
     * Find available rooms for a specific date range
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @return A list of available rooms for the specified date range
     */
    List<Phong> findAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) throws RemoteException;

    /**
     * Find rooms by room type
     * @param maLoaiPhong The room type ID
     * @return A list of rooms with the specified room type
     */
    List<Phong> findByLoaiPhong(String maLoaiPhong) throws RemoteException;
}
//
//package iuh.fit.qlksfxapp.DAO;
//
//import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
//import iuh.fit.qlksfxapp.Entity.Phong;
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//
//public class PhongDAO implements  CloseEntityManager{
//    private EntityManager em =null;
//    public PhongDAO() {
//        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    }
//    public List<Phong> getPhongTheoViTri(String viTri){
//        String query = "SELECT p FROM Phong p WHERE p.viTri = :viTri";
//        return em.createQuery(query, Phong.class)
//                .setParameter("viTri", viTri)
//                .getResultList();
//    }
//    public List<Phong> getPhongTheoMaDonDatPhong(String maDonDatPhong){
//        String query = "SELECT p FROM Phong p JOIN ChiTietDonDatPhong c ON p.maPhong = c.phong.maPhong WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong";
//        return em.createQuery(query, Phong.class)
//                .setParameter("maDonDatPhong", maDonDatPhong)
//                .getResultList();
//    }
//    public  List<Phong> getListPhongTheoTrangThaiPhongDANG_DON_DEP_DANG_SUA_CHUA_KHONG_SU_DUNG(TrangThaiPhong trangThaiPhong){
//        String query = "SELECT p FROM Phong p WHERE p.trangThaiPhong = :trangThaiPhong";
//        return em.createQuery(query, Phong.class)
//                .setParameter("trangThaiPhong", trangThaiPhong)
//                .getResultList();
//    }
//
//    @Override
//    public void closeEntityManager() {
//        if (em != null && em.isOpen()) {
//            em.close();
//        }
//    }
//}
