package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.ChiTietDichVu;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
public class DonDatPhongDAO implements CloseEntityManager {
    private EntityManager em;
    public DonDatPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
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
    public List<DonDatPhong> getDatPhongNow(){
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT d FROM DonDatPhong d WHERE d.ngayNhan <= :now AND d.ngayTra >= :now AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("now", now)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoNgayDenVaNgayDi(LocalDateTime ngayDen, LocalDateTime ngayDi) {
        String query = "SELECT d FROM DonDatPhong d WHERE d.ngayNhan >= :ngayDen AND d.ngayTra <= :ngayDi AND d.trangThai = :trangThai"; // ngay den <- ngay nhan <-  ngay tra<- ngay di
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("ngayDen", ngayDen)
                .setParameter("ngayDi", ngayDi)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoSoNguoiLon(int soNguoiLon) {
        String query = "SELECT d FROM DonDatPhong d WHERE d.soLuongNguoiLon = :soNguoiLon AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("soNguoiLon", soNguoiLon)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoSoTreEm(int soTreEm) {
        String query = "SELECT d FROM DonDatPhong d WHERE d.soLuongTreEm = :soTreEm AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("soTreEm", soTreEm)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoSoNguoiLonVaTreEm(int soNguoiLon, int soTreEm) {
        String query = "SELECT d FROM DonDatPhong d  WHERE d.soLuongNguoiLon = :soNguoiLon AND d.soLuongTreEm = :soTreEm AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("soNguoiLon", soNguoiLon)
                .setParameter("soTreEm", soTreEm)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoTenDoan(String tenDoan) {
        String query = "SELECT d FROM DonDatPhong d WHERE d.tenDoan LIKE :tenDoan AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("tenDoan", "%" + tenDoan + "%")
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoTrangThaiPhongDAT_TRUOC() {
        String query = "SELECT d FROM DonDatPhong d JOIN ChiTietDonDatPhong c ON d.maDonDatPhong = c.donDatPhong.maDonDatPhong WHERE d.trangThai=:trangThai AND c.trangThaiChiTietDonDatPhong = :trangThaiChiTiet";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .setParameter("trangThaiChiTiet", TrangThaiChiTietDonDatPhong.DAT_TRUOC)
                .getResultList();
    }
    public List<DonDatPhong> getListDonDatPhongTheoTrangThaiPhongDANG_SU_DUNG() {
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT d FROM DonDatPhong d JOIN ChiTietDonDatPhong c ON d.maDonDatPhong = c.donDatPhong.maDonDatPhong WHERE d.trangThai=:trangThai AND c.trangThaiChiTietDonDatPhong = :trangThaiChiTiet";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .setParameter("trangThaiChiTiet", TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG)
                .getResultList();
    }


 public static void main(String[] args) {
    DonDatPhongDAO donDatPhongDAO = new DonDatPhongDAO();
    GeneralDAO generalDAO = new GeneralDAO();
    ChiTietDonDatPhongDAO chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
    List<DonDatPhong> donDatPhongs = donDatPhongDAO.getListDonDatPhongTheoTrangThaiPhongDAT_TRUOC();
    for (DonDatPhong d : donDatPhongs) {
        System.out.println(d.getMaDonDatPhong());
        List<ChiTietDonDatPhong> chiTietDonDatPhongs = chiTietDonDatPhongDAO.findChiTietDonDatPhongTheoMaDonDatPhong (d.getMaDonDatPhong());
        for (ChiTietDonDatPhong c : chiTietDonDatPhongs) {
            System.out.println(c.getMaChiTietDonDatPhong());
        }
    }
    donDatPhongDAO.closeEntityManager();
    chiTietDonDatPhongDAO.closeEntityManager();
}
    @Override
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
