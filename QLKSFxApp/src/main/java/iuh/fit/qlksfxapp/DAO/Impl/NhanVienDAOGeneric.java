package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.Entity.NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * NhanVien DAO implementation using GenericDAOImpl
 */
public class NhanVienDAOGeneric extends GenericDAOImpl<NhanVien, String> {
    private static final EntityManagerFactory emf = EntityManagerUtilImpl.getEntityManagerFactory();
    
    public NhanVienDAOGeneric() {
        super(NhanVien.class);
    }
    
    /**
     * Find employees by name (partial match)
     * @param tenNhanVien The name to search for
     * @return A list of employees with matching names
     */
    public List<NhanVien> findByTenNhanVien(String tenNhanVien) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.createQuery(
                            "SELECT nv FROM NhanVien nv WHERE nv.tenNhanVien LIKE :tenNhanVien",
                            NhanVien.class)
                    .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
