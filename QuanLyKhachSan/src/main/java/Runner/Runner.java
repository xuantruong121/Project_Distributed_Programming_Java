package Runner;
import DAO.EntityManagerUtil;
import Data.FakeData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Runner {
    public static void main(String[] args) {
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try{
            FakeData fakeData = new FakeData();

//            fakeData.addLoaiDichVu();
//            fakeData.addLoaiNhanVien();
//            fakeData.addLoaiPhong();
//            fakeData.addLoaiPhuThu();
//            fakeData.addLoaiVatTu();
//            fakeData.addKho();
//            fakeData.addCaLamViec();

//            fakeData.addDichVu();
//            fakeData.addChuongTrinhKhuyenMai();
//            fakeData.addDieuKienApDung();
//            fakeData.addKhachHang();
//            fakeData.addNhanVien();
//            fakeData.addVatTu();
//            fakeData.addVatTuTrongKho();
//            fakeData.addPhuThu();
//            fakeData.addPhong();
//            fakeData.addTaiKhoan();

//            fakeData.addBangPhanCongCaLam();
//            fakeData.addChiTietPhong();
//            fakeData.addLichSuVatTuTrongKho();
//            fakeData.addDoiTuongApDungKhuyenMai();
//            fakeData.addDonDatPhong();
//            fakeData.addChiTietDonDatPhong();
//            fakeData.addChiTietDichVuThanhToanNgoai();
//            fakeData.addDonBaoCao();
//            fakeData.addChiTietDonBaoCao();

//            fakeData.addHoaDon();

        }catch (Exception e) {
            em.getTransaction().rollback();
           
            System.out.println("Error: " + e.getMessage());
        }finally {
            em.close();
        }
    }
}