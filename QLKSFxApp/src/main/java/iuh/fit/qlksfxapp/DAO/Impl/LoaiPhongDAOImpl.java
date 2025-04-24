package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.LoaiPhongDAO;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LoaiPhongDAOImpl extends GeneralDAOImpl implements LoaiPhongDAO {
    private EntityManager em = null;

    public LoaiPhongDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<LoaiPhong> getAllLoaiPhong() {
        return findAll(LoaiPhong.class);
    }

    @Override
    public LoaiPhong findByMaLoaiPhong(String maLoaiPhong) {
        return findOb(LoaiPhong.class, maLoaiPhong);
    }

    @Override
    public LoaiPhong findByTenLoaiPhong(String tenLoaiPhong) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<LoaiPhong> query = em.createQuery(
                    "SELECT l FROM LoaiPhong l WHERE l.tenLoaiPhong = :tenLoaiPhong",
                    LoaiPhong.class);
            query.setParameter("tenLoaiPhong", tenLoaiPhong);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
