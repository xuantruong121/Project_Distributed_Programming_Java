package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.CloseEntityManager;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.rmi.RemoteException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
public class ChiTietDonDatPhongDAOImpl extends GeneralDAOImpl implements ChiTietDonDatPhongDAO, CloseEntityManager {
    private EntityManager em = null;

    public ChiTietDonDatPhongDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<ChiTietDonDatPhong> getAllChiTietDonDatPhong() throws RemoteException {
        return findAll(ChiTietDonDatPhong.class);
    }

    @Override
    public ChiTietDonDatPhong findByMaChiTietDonDatPhong(String maChiTietDonDatPhong) throws RemoteException {
        return findOb(ChiTietDonDatPhong.class, maChiTietDonDatPhong);
    }

    @Override
    public List<ChiTietDonDatPhong> findByMaDonDatPhong(String maDonDatPhong) throws RemoteException {
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
    public List<ChiTietDonDatPhong> findByMaPhong(String maPhong) throws RemoteException {
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
    @Override
    public ChiTietDonDatPhong findChiTietWithKhachHang(String maChiTiet) throws RemoteException {
        return em.createQuery(
                        "SELECT c FROM ChiTietDonDatPhong c LEFT JOIN FETCH c.khachHang WHERE c.maChiTietDonDatPhong = :ma",
                        ChiTietDonDatPhong.class)
                .setParameter("ma", maChiTiet)
                .getSingleResult();
    }

    @Override
    public ChiTietDonDatPhong findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(String maDonDatPhong, String maPhong) throws RemoteException {
        return em.createQuery("SELECT c FROM ChiTietDonDatPhong c WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong AND c.phong.maPhong = :maPhong", ChiTietDonDatPhong.class)
                .setParameter("maDonDatPhong", maDonDatPhong)
                .setParameter("maPhong", maPhong)
                .getSingleResult();

    }
    @Override
    public List<ChiTietDonDatPhong> findChiTietDonDatPhongTheoMaDonDatPhong(String maDonDatPhong) throws RemoteException {
        return em.createQuery("SELECT c FROM ChiTietDonDatPhong c WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong", ChiTietDonDatPhong.class)
                .setParameter("maDonDatPhong", maDonDatPhong)
                .getResultList();
    }
    @Override
    public Double getTongTienDichVuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) throws RemoteException {
        Double result=  em.createQuery("SELECT SUM(c.dichVu.giaDichVu * c.soLuong) FROM ChiTietDichVu c WHERE c.chiTietDonDatPhong.maChiTietDonDatPhong = :maChiTietDonDatPhong AND c.trangThai = false", Double.class)
                .setParameter("maChiTietDonDatPhong", maChiTietDonDatPhong)
                .getSingleResult();
        if (result == null) {
            return 0.0;
        }
        return result;
    }
    @Override
    public Double getTienPhongTheoMaChiTietDonDatPhong(String ma) throws RemoteException {
        Object[] result = em.createQuery(
                        "SELECT c.phong.loaiPhong.gia, d.ngayNhan, d.ngayTra " +
                                "FROM ChiTietDonDatPhong c JOIN c.donDatPhong d " +
                                "WHERE c.maChiTietDonDatPhong = :ma", Object[].class)
                .setParameter("ma", ma)
                .getSingleResult();
        Double gia = (Double) result[0];
        LocalDateTime ngayNhan = (LocalDateTime) result[1];
        LocalDateTime ngayTra = (LocalDateTime) result[2];
        long days = Duration.between(ngayNhan, ngayTra).toDays() + 1; // +1 nếu tính ngày ở lại đầu và cuối
        return gia * days;
    }
    @Override
    public Double getTongTienPhuThuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) throws RemoteException {
        Double result= em.createQuery("SELECT SUM(p.giaPhuThu*c.phong.loaiPhong.gia) FROM ChiTietDonDatPhong c JOIN c.phuThu p WHERE c.maChiTietDonDatPhong = :maChiTietDonDatPhong", Double.class)
                .setParameter("maChiTietDonDatPhong", maChiTietDonDatPhong)
                .getSingleResult();
        if (result == null) {
            return 0.0;
        }
        return result;
    }

    @Override
    public double getTongTienDichVuByMaDonDatPhong(String ma) throws RemoteException {
        List<ChiTietDonDatPhong> chiTietDonDatPhongs= findChiTietDonDatPhongTheoMaDonDatPhong(ma);
        double tongTien =0;

        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
            if(!c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY) && !c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)){
                tongTien+= getTongTienDichVuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
            }
        }
        return tongTien;
    }
    @Override
    public double getTongTienPhuThuByMaDonDatPhong(String ma) throws RemoteException {
        List<ChiTietDonDatPhong> chiTietDonDatPhongs = findChiTietDonDatPhongTheoMaDonDatPhong(ma);
        double tongTien =0;
        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
            if(!c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY) && !c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)){
                tongTien+= getTongTienPhuThuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
            }
        }
        return tongTien;
    }
    @Override
    public double getTongTienPhongByMaDonDatPhong(String ma) throws RemoteException {
        List<ChiTietDonDatPhong> chiTietDonDatPhongs = findChiTietDonDatPhongTheoMaDonDatPhong(ma);
        double tongTien =0;
        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
            if(!c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY) && !c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)){
                tongTien+= getTienPhongTheoMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
            }
        }
        return tongTien;
    }
    @Override
    public double getTongTienByNgay(LocalDate startDate, LocalDate endDate) throws RemoteException {
        List<ChiTietDonDatPhong> chiTietDonDatPhongs = em.createQuery("SELECT c FROM ChiTietDonDatPhong c WHERE c.ngayTra BETWEEN :startDate AND :endDate AND c.trangThaiChiTietDonDatPhong = :trangThai"  , ChiTietDonDatPhong.class)
                .setParameter("startDate", startDate.atStartOfDay())
                .setParameter("endDate", endDate.atTime(23, 59, 59))
                .setParameter("trangThai", TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)
                .getResultList();
        double tongTien =0;
        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
            tongTien+= getTongTienDichVuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
            tongTien+= getTongTienPhuThuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
            tongTien+= getTienPhongTheoMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
        }
        return tongTien;
    }
    @Override
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

//
//public class ChiTietDonDatPhongDAO  implements CloseEntityManager {
//    private EntityManager em =null;
//    public ChiTietDonDatPhongDAO() {
//        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//    }
//    public ChiTietDonDatPhong findChiTietWithKhachHang(String maChiTiet) {
//        return em.createQuery(
//                        "SELECT c FROM ChiTietDonDatPhong c LEFT JOIN FETCH c.khachHang WHERE c.maChiTietDonDatPhong = :ma",
//                        ChiTietDonDatPhong.class)
//                .setParameter("ma", maChiTiet)
//                .getSingleResult();
//    }
//
//    public ChiTietDonDatPhong findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(String maDonDatPhong, String maPhong) {
//        return em.createQuery("SELECT c FROM ChiTietDonDatPhong c WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong AND c.phong.maPhong = :maPhong", ChiTietDonDatPhong.class)
//                .setParameter("maDonDatPhong", maDonDatPhong)
//                .setParameter("maPhong", maPhong)
//                .getSingleResult();
//
//    }
//    public List<ChiTietDonDatPhong> findChiTietDonDatPhongTheoMaDonDatPhong(String maDonDatPhong){
//        return em.createQuery("SELECT c FROM ChiTietDonDatPhong c WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong", ChiTietDonDatPhong.class)
//                .setParameter("maDonDatPhong", maDonDatPhong)
//                .getResultList();
//    }
//    public  Double getTongTienDichVuByMaChiTietDonDatPhong(String maChiTietDonDatPhong){
//        Double result=  em.createQuery("SELECT SUM(c.dichVu.giaDichVu * c.soLuong) FROM ChiTietDichVu c WHERE c.chiTietDonDatPhong.maChiTietDonDatPhong = :maChiTietDonDatPhong AND c.trangThai = false", Double.class)
//                .setParameter("maChiTietDonDatPhong", maChiTietDonDatPhong)
//                .getSingleResult();
//        if (result == null) {
//            return 0.0;
//        }
//        return result;
//    }
//    public Double getTienPhongTheoMaChiTietDonDatPhong(String ma) {
//        Object[] result = em.createQuery(
//                        "SELECT c.phong.loaiPhong.gia, d.ngayNhan, d.ngayTra " +
//                                "FROM ChiTietDonDatPhong c JOIN c.donDatPhong d " +
//                                "WHERE c.maChiTietDonDatPhong = :ma", Object[].class)
//                .setParameter("ma", ma)
//                .getSingleResult();
//        Double gia = (Double) result[0];
//        LocalDateTime ngayNhan = (LocalDateTime) result[1];
//        LocalDateTime ngayTra = (LocalDateTime) result[2];
//        long days = Duration.between(ngayNhan, ngayTra).toDays() + 1; // +1 nếu tính ngày ở lại đầu và cuối
//        return gia * days;
//    }
//    public Double getTongTienPhuThuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) {
//        Double result= em.createQuery("SELECT SUM(p.giaPhuThu*c.phong.loaiPhong.gia) FROM ChiTietDonDatPhong c JOIN c.phuThu p WHERE c.maChiTietDonDatPhong = :maChiTietDonDatPhong", Double.class)
//                .setParameter("maChiTietDonDatPhong", maChiTietDonDatPhong)
//                .getSingleResult();
//        if (result == null) {
//            return 0.0;
//        }
//        return result;
//    }
//
//    public double getTongTienDichVuByMaDonDatPhong(String ma){
//        List<ChiTietDonDatPhong> chiTietDonDatPhongs= findChiTietDonDatPhongTheoMaDonDatPhong(ma);
//        double tongTien =0;
//
//        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
//            if(!c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY) && !c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)){
//                tongTien+= getTongTienDichVuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
//            }
//        }
//        return tongTien;
//    }
//    public double getTongTienPhuThuByMaDonDatPhong(String ma){
//        List<ChiTietDonDatPhong> chiTietDonDatPhongs = findChiTietDonDatPhongTheoMaDonDatPhong(ma);
//        double tongTien =0;
//        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
//            if(!c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY) && !c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)){
//                tongTien+= getTongTienPhuThuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
//            }
//        }
//        return tongTien;
//    }
//    public double getTongTienPhongByMaDonDatPhong(String ma){
//        List<ChiTietDonDatPhong> chiTietDonDatPhongs = findChiTietDonDatPhongTheoMaDonDatPhong(ma);
//        double tongTien =0;
//        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
//            if(!c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY) && !c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)){
//                tongTien+= getTienPhongTheoMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
//            }
//        }
//        return tongTien;
//    }
//    public double getTongTienByNgay(LocalDate startDate, LocalDate endDate){
//        List<ChiTietDonDatPhong> chiTietDonDatPhongs = em.createQuery("SELECT c FROM ChiTietDonDatPhong c WHERE c.ngayTra BETWEEN :startDate AND :endDate AND c.trangThaiChiTietDonDatPhong = :trangThai"  , ChiTietDonDatPhong.class)
//                .setParameter("startDate", startDate.atStartOfDay())
//                .setParameter("endDate", endDate.atTime(23, 59, 59))
//                .setParameter("trangThai", TrangThaiChiTietDonDatPhong.DA_THANH_TOAN)
//                .getResultList();
//        double tongTien =0;
//        for(ChiTietDonDatPhong c: chiTietDonDatPhongs){
//            tongTien+= getTongTienDichVuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
//            tongTien+= getTongTienPhuThuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
//            tongTien+= getTienPhongTheoMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
//        }
//        return tongTien;
//    }
//    public static void main(String[] args) {
//        GeneralDAO generalDAO = new GeneralDAO();
//        ChiTietDonDatPhongDAO chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
//        ChiTietDonDatPhong chiTietDonDatPhong = generalDAO.findOb(ChiTietDonDatPhong.class, "200425001-001");
//        System.out.println(chiTietDonDatPhongDAO.getTongTienDichVuByMaChiTietDonDatPhong(chiTietDonDatPhong.getMaChiTietDonDatPhong()));
//        System.out.println(chiTietDonDatPhongDAO.getTienPhongTheoMaChiTietDonDatPhong(chiTietDonDatPhong.getMaChiTietDonDatPhong()));
//    }
//
//    @Override
//    public void closeEntityManager() {
//        if (em != null && em.isOpen()) {
//            em.close();
//        }
//    }
//}

