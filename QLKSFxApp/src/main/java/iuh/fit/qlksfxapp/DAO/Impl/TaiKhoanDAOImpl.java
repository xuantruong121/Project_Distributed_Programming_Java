package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.TaiKhoanDAO;
import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TaiKhoanDAOImpl extends GeneralDAOImpl implements TaiKhoanDAO {
    private EntityManager em = null;

    public TaiKhoanDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() {
        return findAll(TaiKhoan.class);
    }

    @Override
    public TaiKhoan findByMaTaiKhoan(String maTaiKhoan) {
        return findOb(TaiKhoan.class, maTaiKhoan);
    }

    @Override
    public TaiKhoan findByUsername(String username) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<TaiKhoan> query = em.createQuery(
                    "SELECT t FROM TaiKhoan t WHERE t.nhanVien.maNhanVien = :username",
                    TaiKhoan.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public TaiKhoan authenticate(String username, String password) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<TaiKhoan> query = em.createQuery(
                    "SELECT t FROM TaiKhoan t WHERE t.nhanVien.maNhanVien = :username AND t.matKhau = :password",
                    TaiKhoan.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
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
