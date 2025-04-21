package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.HoaDonDAO;
import iuh.fit.qlksfxapp.Entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class HoaDonDAOImpl extends GeneralDAOImpl implements HoaDonDAO {
    private EntityManager em = null;

    public HoaDonDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<HoaDon> getAllHoaDon() {
        return findAll(HoaDon.class);
    }

    @Override
    public HoaDon findByMaHoaDon(String maHoaDon) {
        return findOb(HoaDon.class, maHoaDon);
    }

    @Override
    public List<HoaDon> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<HoaDon> query = em.createQuery(
                    "SELECT h FROM HoaDon h WHERE h.ngayLap BETWEEN :startDate AND :endDate",
                    HoaDon.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<HoaDon> findByMaKhachHang(String maKhachHang) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<HoaDon> query = em.createQuery(
                    "SELECT h FROM HoaDon h WHERE h.khachHang.maKhachHang = :maKhachHang",
                    HoaDon.class);
            query.setParameter("maKhachHang", maKhachHang);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
