package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.DichVu;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for DichVu DAO operations
 */
public interface DichVuDAO extends GeneralDAO {

    /**
     * Get all services
     * @return A list of all services
     */
    List<DichVu> getAllDichVu() throws RemoteException;

    /**
     * Find service by ID
     * @param maDichVu The ID of the service
     * @return The found service or null if not found
     */
    DichVu findByMaDichVu(String maDichVu) throws RemoteException;

    /**
     * Find services by service type
     * @param maLoaiDichVu The service type ID
     * @return A list of services with the specified service type
     */
    List<DichVu> findByLoaiDichVu(String maLoaiDichVu) throws RemoteException;

    /**
     * Search services by name
     * @param tenDichVu The name to search for
     * @return A list of services matching the search criteria
     */
    List<DichVu> searchByName(String tenDichVu) throws RemoteException;
}
//private EntityManager em = null;
//
//public DichVuDAO() {
//    super();
//    em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//}
//
//// Get all services
//public List<DichVu> getAllDichVu() {
//    return findAll(DichVu.class);
//}
//
//// Find service by ID
//public DichVu findByMaDichVu(String maDichVu) {
//    return findOb(DichVu.class, maDichVu);
//}
//
//// Find services by name (partial match)
//public List<DichVu> findByTenDichVu(String tenDichVu) {
//    EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    try {
//        return em.createQuery(
//                        "SELECT dv FROM DichVu dv WHERE dv.tenDichVu LIKE :tenDichVu",
//                        DichVu.class)
//                .setParameter("tenDichVu", "%" + tenDichVu + "%")
//                .getResultList();
//    } finally {
//        em.close();
//    }
//}
//
//// Find services by service type
//public List<DichVu> findByLoaiDichVu(String maLoaiDichVu) {
//    EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    try {
//        return em.createQuery(
//                        "SELECT dv FROM DichVu dv WHERE dv.loaiDichVu.maLoaiDichVu = :maLoaiDichVu",
//                        DichVu.class)
//                .setParameter("maLoaiDichVu", maLoaiDichVu)
//                .getResultList();
//    } finally {
//        em.close();
//    }
//}
