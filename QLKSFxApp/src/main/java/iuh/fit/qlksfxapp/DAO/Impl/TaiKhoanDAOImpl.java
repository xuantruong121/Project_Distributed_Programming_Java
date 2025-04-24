package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.TaiKhoanDAO;
import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import iuh.fit.qlksfxapp.util.PasswordHasher;
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
            // Lấy tài khoản dựa trên username (mã nhân viên)
            TypedQuery<TaiKhoan> query = em.createQuery(
                    "SELECT t FROM TaiKhoan t WHERE t.nhanVien.maNhanVien = :username",
                    TaiKhoan.class);
            query.setParameter("username", username);

            TaiKhoan taiKhoan = query.getSingleResult();

            // Kiểm tra mật khẩu trực tiếp (không băm)
            if (taiKhoan != null && password.equals(taiKhoan.getMatKhau())) {
                return taiKhoan;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
