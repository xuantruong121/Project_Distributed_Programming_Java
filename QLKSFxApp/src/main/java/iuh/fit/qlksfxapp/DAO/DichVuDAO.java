package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.DichVu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DichVuDAO extends GeneralDAO {
    private EntityManager em = null;

    public DichVuDAO() {
        super();
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

    // Get all services
    public List<DichVu> getAllDichVu() {
        return findAll(DichVu.class);
    }

    // Find service by ID
    public DichVu findByMaDichVu(String maDichVu) {
        return findOb(DichVu.class, maDichVu);
    }

    // Find services by name (partial match)
    public List<DichVu> findByTenDichVu(String tenDichVu) {
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT dv FROM DichVu dv WHERE dv.tenDichVu LIKE :tenDichVu",
                            DichVu.class)
                    .setParameter("tenDichVu", "%" + tenDichVu + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Find services by service type
    public List<DichVu> findByLoaiDichVu(String maLoaiDichVu) {
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT dv FROM DichVu dv WHERE dv.loaiDichVu.maLoaiDichVu = :maLoaiDichVu",
                            DichVu.class)
                    .setParameter("maLoaiDichVu", maLoaiDichVu)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
