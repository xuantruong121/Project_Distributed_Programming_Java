package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.CloseEntityManager;
import iuh.fit.qlksfxapp.DAO.DonDatPhongDAO;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
@Getter
public class DonDatPhongDAOImpl extends GeneralDAOImpl implements DonDatPhongDAO, CloseEntityManager {
    private EntityManager em = null;
    public DonDatPhongDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public DonDatPhong getDonDatPhongNowByIdPhong(String idPhong) throws RemoteException {
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
    public List<DonDatPhong> getAllDonDatPhong() throws RemoteException {
        return findAll(DonDatPhong.class);
    }

    @Override
    public DonDatPhong findByMaDonDatPhong(String maDonDatPhong) throws RemoteException {
        return findOb(DonDatPhong.class, maDonDatPhong);
    }

    @Override
    public List<DonDatPhong> findByMaKhachHang(String maKhachHang) throws RemoteException {
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
    public List<DonDatPhong> getDatPhongNow() throws RemoteException {
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT d FROM DonDatPhong d WHERE d.ngayNhan <= :now AND d.ngayTra >= :now AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("now", now)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoNgayDenVaNgayDi(LocalDateTime ngayDen, LocalDateTime ngayDi) throws RemoteException {
        String query = "SELECT d FROM DonDatPhong d WHERE d.ngayNhan >= :ngayDen AND d.ngayTra <= :ngayDi AND d.trangThai = :trangThai"; // ngay den <- ngay nhan <-  ngay tra<- ngay di
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("ngayDen", ngayDen)
                .setParameter("ngayDi", ngayDi)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoSoNguoiLon(int soNguoiLon) throws RemoteException {
        String query = "SELECT d FROM DonDatPhong d WHERE d.soLuongNguoiLon = :soNguoiLon AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("soNguoiLon", soNguoiLon)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoSoTreEm(int soTreEm) throws RemoteException {
        String query = "SELECT d FROM DonDatPhong d WHERE d.soLuongTreEm = :soTreEm AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("soTreEm", soTreEm)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoSoNguoiLonVaTreEm(int soNguoiLon, int soTreEm) throws RemoteException {
        String query = "SELECT d FROM DonDatPhong d  WHERE d.soLuongNguoiLon = :soNguoiLon AND d.soLuongTreEm = :soTreEm AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("soNguoiLon", soNguoiLon)
                .setParameter("soTreEm", soTreEm)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoTenDoan(String tenDoan) throws RemoteException {
        String query = "SELECT d FROM DonDatPhong d WHERE d.tenDoan LIKE :tenDoan AND d.trangThai = :trangThai";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("tenDoan", "%" + tenDoan + "%")
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoTrangThaiPhongDAT_TRUOC() throws RemoteException {
        String query = "SELECT d FROM DonDatPhong d JOIN ChiTietDonDatPhong c ON d.maDonDatPhong = c.donDatPhong.maDonDatPhong WHERE d.trangThai=:trangThai AND c.trangThaiChiTietDonDatPhong = :trangThaiChiTiet";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .setParameter("trangThaiChiTiet", TrangThaiChiTietDonDatPhong.DAT_TRUOC)
                .getResultList();
    }

    @Override
    public List<DonDatPhong> getListDonDatPhongTheoTrangThaiPhongDANG_SU_DUNG() throws RemoteException {
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT d FROM DonDatPhong d JOIN ChiTietDonDatPhong c ON d.maDonDatPhong = c.donDatPhong.maDonDatPhong WHERE d.trangThai=:trangThai AND c.trangThaiChiTietDonDatPhong = :trangThaiChiTiet";
        return em.createQuery(query, DonDatPhong.class)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .setParameter("trangThaiChiTiet", TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG)
                .getResultList();
    }

    @Override
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    // For testing purposes
    public static void main(String[] args) {
        DonDatPhongDAOImpl donDatPhongDAOImpl = new DonDatPhongDAOImpl();
        GeneralDAOImpl generalDAOImpl = new GeneralDAOImpl();
        List<Phong> phongs = generalDAOImpl.findAll(Phong.class);
        for (Phong phong : phongs) {
            System.out.println("Phong: " + phong.getMaPhong());
            try {
                DonDatPhong donDatPhong = donDatPhongDAOImpl.getDonDatPhongNowByIdPhong(phong.getMaPhong());
                if (donDatPhong != null) {
                    System.out.println("Don Dat Phong: " + donDatPhong.getMaDonDatPhong());
                } else {
                    System.out.println("Khong co don dat phong nao");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
