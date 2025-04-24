package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.LoaiDichVu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for LoaiDichVu DAO operations
 */
public interface LoaiDichVuDAO extends GeneralDAO {

    /**
     * Get all service types
     * @return A list of all service types
     */
    List<LoaiDichVu> getAllLoaiDichVu() throws RemoteException;

    /**
     * Find service type by ID
     * @param maLoaiDichVu The ID of the service type
     * @return The found service type or null if not found
     */
    LoaiDichVu findByMaLoaiDichVu(String maLoaiDichVu) throws RemoteException;

    /**
     * Find service type by name
     * @param tenLoaiDichVu The name to search for
     * @return The found service type or null if not found
     */
    LoaiDichVu findByTenLoaiDichVu(String tenLoaiDichVu) throws RemoteException;
}


//public class LoaiDichVuDAO extends GeneralDAO {
//    private EntityManager em = null;
//
//    public LoaiDichVuDAO() {
//        super();
//        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    }
//
//    // Get all service types
//    public List<LoaiDichVu> getAllLoaiDichVu() {
//        return findAll(LoaiDichVu.class);
//    }
//
//    // Find service type by ID
//    public LoaiDichVu findByMaLoaiDichVu(String maLoaiDichVu) {
//        return findOb(LoaiDichVu.class, maLoaiDichVu);
//    }
//
//    // Find service types by name (partial match)
//    public List<LoaiDichVu> findByTenLoaiDichVu(String tenLoaiDichVu) {
//        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//        try {
//            return em.createQuery(
//                            "SELECT ldv FROM LoaiDichVu ldv WHERE ldv.tenLoaiDichVu LIKE :tenLoaiDichVu",
//                            LoaiDichVu.class)
//                    .setParameter("tenLoaiDichVu", "%" + tenLoaiDichVu + "%")
//                    .getResultList();
//        } finally {
//            em.close();
//        }
//    }
//}

