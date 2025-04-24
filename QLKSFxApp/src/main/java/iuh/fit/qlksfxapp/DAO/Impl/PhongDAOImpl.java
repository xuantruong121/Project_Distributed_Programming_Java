package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.PhongDAO;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class PhongDAOImpl extends GeneralDAOImpl implements PhongDAO {
    private EntityManager em = null;

    public PhongDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Phong> getAllPhong() {
        return findAll(Phong.class);
    }

    @Override
    public Phong findByMaPhong(String maPhong) {
        return findOb(Phong.class, maPhong);
    }

    @Override
    public List<Phong> findByTrangThai(TrangThaiPhong trangThaiPhong) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<Phong> query = em.createQuery(
                    "SELECT p FROM Phong p WHERE p.trangThaiPhong = :trangThai",
                    Phong.class);
            query.setParameter("trangThai", trangThaiPhong);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Phong> findAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            // Phòng có trạng thái TRONG hoặc không có trong bất kỳ đơn đặt phòng nào trong khoảng thời gian này
            TypedQuery<Phong> query = em.createQuery(
                    "SELECT p FROM Phong p WHERE p.trangThaiPhong = :trangThai OR p.maPhong NOT IN " +
                            "(SELECT ctdp.phong.maPhong FROM ChiTietDonDatPhong ctdp " +
                            "WHERE ctdp.donDatPhong.ngayNhan <= :checkOut AND ctdp.donDatPhong.ngayTra >= :checkIn)",
                    Phong.class);
            query.setParameter("trangThai", TrangThaiPhong.TRONG);
            query.setParameter("checkIn", checkIn);
            query.setParameter("checkOut", checkOut);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Phong> findByLoaiPhong(String maLoaiPhong) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            TypedQuery<Phong> query = em.createQuery(
                    "SELECT p FROM Phong p WHERE p.loaiPhong.maLoaiPhong = :maLoaiPhong",
                    Phong.class);
            query.setParameter("maLoaiPhong", maLoaiPhong);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
