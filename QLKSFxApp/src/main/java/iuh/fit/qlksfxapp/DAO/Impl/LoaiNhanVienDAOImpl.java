package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.LoaiNhanVienDAO;
import iuh.fit.qlksfxapp.Entity.LoaiNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LoaiNhanVienDAOImpl extends GeneralDAOImpl implements LoaiNhanVienDAO {
    private EntityManager em = null;

    public LoaiNhanVienDAOImpl() {
        super();
    }

    @Override
    public List<LoaiNhanVien> getAllLoaiNhanVien() {
        return findAll(LoaiNhanVien.class);
    }

    @Override
    public LoaiNhanVien findByMaLoaiNhanVien(String maLoaiNhanVien) {
        return findOb(LoaiNhanVien.class, maLoaiNhanVien);
    }

    @Override
    public LoaiNhanVien findByTenLoaiNhanVien(String tenLoaiNhanVien) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<LoaiNhanVien> query = em.createQuery(
                    "SELECT l FROM LoaiNhanVien l WHERE l.tenLoaiNhanVien = :tenLoaiNhanVien",
                    LoaiNhanVien.class);
            query.setParameter("tenLoaiNhanVien", tenLoaiNhanVien);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // Backward compatibility
    public LoaiNhanVien getLoaiNhanVienByMaNV(String maNV) {
        return findByMaLoaiNhanVien(maNV);
    }
}
