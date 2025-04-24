package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.KhachHang;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for KhachHang DAO operations
 */
public interface KhachHangDAO extends GeneralDAO {

    /**
     * Get all customers
     * @return A list of all customers
     */
    List<KhachHang> getAllKhachHang() throws RemoteException;

    /**
     * Find customer by ID
     * @param maKhachHang The ID of the customer
     * @return The found customer or null if not found
     */
    KhachHang findByMaKhachHang(String maKhachHang) throws RemoteException;

    /**
     * Find customer by phone number
     * @param soDienThoai The phone number to search for
     * @return The found customer or null if not found
     */
    KhachHang findBySoDienThoai(String soDienThoai) throws RemoteException;

    /**
     * Find customer by CCCD (Citizen ID)
     * @param cccd The CCCD to search for
     * @return The found customer or null if not found
     */
    KhachHang findByCCCD(String cccd) throws RemoteException;

    /**
     * Search customers by name
     * @param tenKhachHang The name to search for
     * @return A list of customers matching the search criteria
     */
    List<KhachHang> searchByName(String tenKhachHang) throws RemoteException;
}
//package iuh.fit.qlksfxapp.DAO;

//import iuh.fit.qlksfxapp.Entity.KhachHang;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.NoResultException;
//import lombok.Getter;
//
//import java.util.Set;
//
//@Getter
//public class KhachHangDAO implements CloseEntityManager {
//    private EntityManager em ;
//    public KhachHangDAO() {
//        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
//        em = emf.createEntityManager();
//    }
//    public KhachHang findKhachHangByCccd(String cccd) {
//        String query = "SELECT k FROM KhachHang k WHERE k.canCuocCongDan = :cccd";
//        try {
//            return em.createQuery(query, KhachHang.class)
//                    .setParameter("cccd", cccd)
//                    .getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
//    public Set<KhachHang> findKhachHangByMaChiTietDonDatPhong(String maChiTiet) {
//        String query = "SELECT  k FROM ChiTietDonDatPhong c " +
//                "JOIN c.khachHang k " +
//                "WHERE c.maChiTietDonDatPhong = :ma";
//        return Set.copyOf(em.createQuery(query, KhachHang.class)
//                .setParameter("ma", maChiTiet)
//                .getResultList());
//    }
//    @Override
//    public void closeEntityManager() {
//        if (em != null && em.isOpen()) {
//            em.close();
//        }
//    }
//}
