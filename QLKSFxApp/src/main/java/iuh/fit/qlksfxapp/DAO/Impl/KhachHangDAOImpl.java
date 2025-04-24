package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.KhachHangDAO;
import iuh.fit.qlksfxapp.Entity.KhachHang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class KhachHangDAOImpl extends GeneralDAOImpl implements KhachHangDAO {
    private EntityManager em = null;

    public KhachHangDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<KhachHang> getAllKhachHang() {
        return findAll(KhachHang.class);
    }

    @Override
    public KhachHang findByMaKhachHang(String maKhachHang) {
        return findOb(KhachHang.class, maKhachHang);
    }

    @Override
    public KhachHang findBySoDienThoai(String soDienThoai) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<KhachHang> query = em.createQuery(
                    "SELECT k FROM KhachHang k WHERE k.soDienThoai = :soDienThoai",
                    KhachHang.class);
            query.setParameter("soDienThoai", soDienThoai);
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
    public KhachHang findByCCCD(String cccd) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<KhachHang> query = em.createQuery(
                    "SELECT k FROM KhachHang k WHERE k.canCuocCongDan = :cccd",
                    KhachHang.class);
            query.setParameter("cccd", cccd);
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
    public List<KhachHang> searchByName(String tenKhachHang) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<KhachHang> query = em.createQuery(
                    "SELECT k FROM KhachHang k WHERE k.tenKhachHang LIKE :tenKhachHang",
                    KhachHang.class);
            query.setParameter("tenKhachHang", "%" + tenKhachHang + "%");
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
