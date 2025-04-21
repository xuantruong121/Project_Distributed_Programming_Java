package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.DichVuDAO;
import iuh.fit.qlksfxapp.Entity.DichVu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DichVuDAOImpl extends GeneralDAOImpl implements DichVuDAO {
    private EntityManager em = null;

    public DichVuDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    // Get all services
    @Override
    public List<DichVu> getAllDichVu() {
        return findAll(DichVu.class);
    }

    // Find service by ID
    @Override
    public DichVu findByMaDichVu(String maDichVu) {
        return findOb(DichVu.class, maDichVu);
    }

    // Find services by name (partial match)
    @Override
    public List<DichVu> searchByName(String tenDichVu) {
        EntityManager em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
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
    @Override
    public List<DichVu> findByLoaiDichVu(String maLoaiDichVu) {
        EntityManager em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
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
