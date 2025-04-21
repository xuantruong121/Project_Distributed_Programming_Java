package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.DonDatPhongDAO;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

public class DonDatPhongDAOImpl extends GeneralDAOImpl implements DonDatPhongDAO {
    private EntityManager em = null;
    public DonDatPhongDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public DonDatPhong getDonDatPhongNowByIdPhong(String idPhong){
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT d FROM DonDatPhong d JOIN ChiTietDonDatPhong c ON d.maDonDatPhong = c.donDatPhong.maDonDatPhong WHERE c.phong.maPhong = :idPhong AND d.ngayNhan <= :now AND d.ngayTra >= :now AND d.trangThai = :trangThai";
        List<DonDatPhong> donDatPhongs = em.createQuery(query, DonDatPhong.class)
                .setParameter("idPhong", idPhong)
                .setParameter("now", now)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
        if(!donDatPhongs.isEmpty()){
            return donDatPhongs.getFirst();
        }
        return null;
    }

    @Override
    public List<DonDatPhong> getAllDonDatPhong() {
        return findAll(DonDatPhong.class);
    }

    @Override
    public DonDatPhong findByMaDonDatPhong(String maDonDatPhong) {
        return findOb(DonDatPhong.class, maDonDatPhong);
    }

    @Override
    public List<DonDatPhong> findByMaKhachHang(String maKhachHang) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT d FROM DonDatPhong d WHERE d.khachHang.maKhachHang = :maKhachHang",
                    DonDatPhong.class)
                    .setParameter("maKhachHang", maKhachHang)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<DonDatPhong> findByTrangThai(TrangThaiDonDatPhong trangThai) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT d FROM DonDatPhong d WHERE d.trangThai = :trangThai",
                    DonDatPhong.class)
                    .setParameter("trangThai", trangThai)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<DonDatPhong> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        EntityManager em = null;
        try {
            em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
            return em.createQuery(
                    "SELECT d FROM DonDatPhong d WHERE d.ngayDat BETWEEN :startDate AND :endDate",
                    DonDatPhong.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // For testing purposes
    public static void main(String[] args) {
        DonDatPhongDAOImpl donDatPhongDAOImpl = new DonDatPhongDAOImpl();
        GeneralDAOImpl generalDAOImpl = new GeneralDAOImpl();
        List<Phong> phongs = generalDAOImpl.findAll(Phong.class);
        for (Phong phong : phongs) {
            System.out.println("Phong: " + phong.getMaPhong());
            DonDatPhong donDatPhong = donDatPhongDAOImpl.getDonDatPhongNowByIdPhong(phong.getMaPhong());
            if (donDatPhong != null) {
                System.out.println("Don Dat Phong: " + donDatPhong.getMaDonDatPhong());
            } else {
                System.out.println("Khong co don dat phong nao");
            }
        }
    }
}
