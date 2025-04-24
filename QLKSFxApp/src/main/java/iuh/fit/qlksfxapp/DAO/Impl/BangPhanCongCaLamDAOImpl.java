package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.BangPhanCongCaLamDAO;
import iuh.fit.qlksfxapp.Entity.BangPhanCongCaLam;
import iuh.fit.qlksfxapp.Entity.CaLamViec;
import iuh.fit.qlksfxapp.Entity.NhanVien;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiBangPhanCongCaLam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class BangPhanCongCaLamDAOImpl extends GeneralDAOImpl implements BangPhanCongCaLamDAO {
    private EntityManager em = null;

    public BangPhanCongCaLamDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public BangPhanCongCaLam findByMaPhanCong(String maPhanCong) {
        return findOb(BangPhanCongCaLam.class, maPhanCong);
    }

    @Override
    public List<BangPhanCongCaLam> getAllBangPhanCongCaLam() {
        return findAll(BangPhanCongCaLam.class);
    }

    @Override
    public List<BangPhanCongCaLam> findByMaNhanVien(String maNhanVien) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<BangPhanCongCaLam> query = em.createQuery(
                    "SELECT b FROM BangPhanCongCaLam b WHERE b.nhanVien.maNhanVien = :maNhanVien",
                    BangPhanCongCaLam.class);
            query.setParameter("maNhanVien", maNhanVien);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<BangPhanCongCaLam> findByMaCaLam(String maCaLam) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<BangPhanCongCaLam> query = em.createQuery(
                    "SELECT b FROM BangPhanCongCaLam b WHERE b.caLamViec.maCaLam = :maCaLam",
                    BangPhanCongCaLam.class);
            query.setParameter("maCaLam", maCaLam);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<BangPhanCongCaLam> findByNhanVien(NhanVien nhanVien) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<BangPhanCongCaLam> query = em.createQuery(
                    "SELECT b FROM BangPhanCongCaLam b WHERE b.nhanVien = :nhanVien",
                    BangPhanCongCaLam.class);
            query.setParameter("nhanVien", nhanVien);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<BangPhanCongCaLam> findByCaLamViec(CaLamViec caLamViec) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<BangPhanCongCaLam> query = em.createQuery(
                    "SELECT b FROM BangPhanCongCaLam b WHERE b.caLamViec = :caLamViec",
                    BangPhanCongCaLam.class);
            query.setParameter("caLamViec", caLamViec);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<BangPhanCongCaLam> findByNgayLamViec(LocalDate ngayLamViec) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<BangPhanCongCaLam> query = em.createQuery(
                    "SELECT b FROM BangPhanCongCaLam b WHERE b.ngayLamViec = :ngayLamViec",
                    BangPhanCongCaLam.class);
            query.setParameter("ngayLamViec", ngayLamViec);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<BangPhanCongCaLam> findByTrangThai(TrangThaiBangPhanCongCaLam trangThai) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<BangPhanCongCaLam> query = em.createQuery(
                    "SELECT b FROM BangPhanCongCaLam b WHERE b.trangThai = :trangThai",
                    BangPhanCongCaLam.class);
            query.setParameter("trangThai", trangThai);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
