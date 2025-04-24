package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.LoaiDichVuDAO;
import iuh.fit.qlksfxapp.Entity.LoaiDichVu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class LoaiDichVuDAOImpl extends GeneralDAOImpl implements LoaiDichVuDAO {
    private EntityManager em = null;

    public LoaiDichVuDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    // Get all service types
    @Override
    public List<LoaiDichVu> getAllLoaiDichVu() {
        return findAll(LoaiDichVu.class);
    }

    // Find service type by ID
    @Override
    public LoaiDichVu findByMaLoaiDichVu(String maLoaiDichVu) {
        return findOb(LoaiDichVu.class, maLoaiDichVu);
    }

    // Find service type by name
    @Override
    public LoaiDichVu findByTenLoaiDichVu(String tenLoaiDichVu) {
        EntityManager em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT ldv FROM LoaiDichVu ldv WHERE ldv.tenLoaiDichVu = :tenLoaiDichVu",
                            LoaiDichVu.class)
                    .setParameter("tenLoaiDichVu", tenLoaiDichVu)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
