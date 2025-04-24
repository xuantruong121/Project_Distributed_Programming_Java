package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.NhanVienDAO;
import iuh.fit.qlksfxapp.Entity.NhanVien;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NhanVienDAOImpl extends GeneralDAOImpl implements NhanVienDAO {
    private EntityManager em = null;

    public NhanVienDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    // Get all employees
    @Override
    public List<NhanVien> getAllNhanVien() {
        return findAll(NhanVien.class);
    }

    // Find employee by ID
    @Override
    public NhanVien findByMaNhanVien(String maNhanVien) {
        return findOb(NhanVien.class, maNhanVien);
    }

    // Find employees by name (partial match)
    @Override
    public List<NhanVien> searchByName(String tenNhanVien) {
        EntityManager em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT nv FROM NhanVien nv WHERE nv.tenNhanVien LIKE :tenNhanVien",
                            NhanVien.class)
                    .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<NhanVien> findByLoaiNhanVien(String maLoaiNhanVien) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT nv FROM NhanVien nv WHERE nv.loaiNhanVien.maLoaiNhanVien = :maLoaiNhanVien",
                    NhanVien.class)
                    .setParameter("maLoaiNhanVien", maLoaiNhanVien)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public NhanVien findBySoDienThoai(String soDienThoai) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT nv FROM NhanVien nv WHERE nv.soDienThoai = :soDienThoai",
                    NhanVien.class)
                    .setParameter("soDienThoai", soDienThoai)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public NhanVien findByCCCD(String cccd) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT nv FROM NhanVien nv WHERE nv.cccd = :cccd",
                    NhanVien.class)
                    .setParameter("cccd", cccd)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
