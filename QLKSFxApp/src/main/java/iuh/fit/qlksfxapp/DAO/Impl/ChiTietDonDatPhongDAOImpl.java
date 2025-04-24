package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChiTietDonDatPhongDAOImpl extends GeneralDAOImpl implements ChiTietDonDatPhongDAO {
    private EntityManager em = null;

    public ChiTietDonDatPhongDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<ChiTietDonDatPhong> getAllChiTietDonDatPhong() {
        return findAll(ChiTietDonDatPhong.class);
    }

    @Override
    public ChiTietDonDatPhong findByMaChiTietDonDatPhong(String maChiTietDonDatPhong) {
        return findOb(ChiTietDonDatPhong.class, maChiTietDonDatPhong);
    }

    @Override
    public List<ChiTietDonDatPhong> findByMaDonDatPhong(String maDonDatPhong) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT c FROM ChiTietDonDatPhong c WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong",
                    ChiTietDonDatPhong.class)
                    .setParameter("maDonDatPhong", maDonDatPhong)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<ChiTietDonDatPhong> findByMaPhong(String maPhong) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT c FROM ChiTietDonDatPhong c WHERE c.phong.maPhong = :maPhong",
                    ChiTietDonDatPhong.class)
                    .setParameter("maPhong", maPhong)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
