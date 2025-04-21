package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.ChiTietDichVuDAO;
import iuh.fit.qlksfxapp.Entity.ChiTietDichVu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChiTietDichVuDAOImpl extends GeneralDAOImpl implements ChiTietDichVuDAO {
    private EntityManager em = null;

    public ChiTietDichVuDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<ChiTietDichVu> getAllChiTietDichVu() {
        return findAll(ChiTietDichVu.class);
    }

    @Override
    public ChiTietDichVu findByMaChiTietDichVu(String maChiTietDichVu) {
        return findOb(ChiTietDichVu.class, maChiTietDichVu);
    }

    @Override
    public List<ChiTietDichVu> findByMaDonDatPhong(String maDonDatPhong) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT c FROM ChiTietDichVu c WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong",
                    ChiTietDichVu.class)
                    .setParameter("maDonDatPhong", maDonDatPhong)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<ChiTietDichVu> findByMaDichVu(String maDichVu) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT c FROM ChiTietDichVu c WHERE c.dichVu.maDichVu = :maDichVu",
                    ChiTietDichVu.class)
                    .setParameter("maDichVu", maDichVu)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
