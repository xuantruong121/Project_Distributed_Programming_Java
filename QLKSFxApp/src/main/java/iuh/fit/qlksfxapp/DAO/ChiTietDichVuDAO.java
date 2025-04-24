package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.ChiTietDichVu;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for ChiTietDichVu DAO operations
 */
public interface ChiTietDichVuDAO extends GeneralDAO {

    /**
     * Get all service details
     * @return A list of all service details
     */
    List<ChiTietDichVu> getAllChiTietDichVu() throws RemoteException;

    /**
     * Find service detail by ID
     * @param maChiTietDichVu The ID of the service detail
     * @return The found service detail or null if not found
     */
    ChiTietDichVu findByMaChiTietDichVu(String maChiTietDichVu) throws RemoteException;

    /**
     * Find service details by booking ID
     * @param maDonDatPhong The booking ID
     * @return A list of service details for the specified booking
     */
    List<ChiTietDichVu> findByMaDonDatPhong(String maDonDatPhong) throws RemoteException;

    /**
     * Find service details by service ID
     * @param maDichVu The service ID
     * @return A list of service details for the specified service
     */
    List<ChiTietDichVu> findByMaDichVu(String maDichVu) throws RemoteException;
}
//public class ChiTietDichVuDAO {
//    private EntityManager em =null;
//    public ChiTietDichVuDAO() {
//        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    }
//    public List<ChiTietDichVu> getListChiTietDichVuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) {
//        try {
//            return  em.createQuery("SELECT c FROM ChiTietDichVu c WHERE c.chiTietDonDatPhong.maChiTietDonDatPhong = :maChiTietDonDatPhong", ChiTietDichVu.class)
//                    .setParameter("maChiTietDonDatPhong", maChiTietDonDatPhong)
//                    .getResultList();
//        }finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
//
//    }
//}