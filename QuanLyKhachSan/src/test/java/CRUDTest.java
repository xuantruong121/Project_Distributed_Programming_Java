import DAO.EntityManagerUtil;
import DAO.GeneralDAO;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import util.PasswordHasher;

import java.time.LocalDate;
import java.util.List;
import Enum.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CRUDTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setUpClass() {
        // Khởi tạo EntityManagerFactory trước khi chạy các test
        emf = EntityManagerUtil.getEntityManagerFactory();
    }

    @BeforeEach
    public void setUp() {
        // Khởi tạo EntityManager trước mỗi test
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        // Đóng EntityManager sau mỗi test
        if (em != null) {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        // Đóng EntityManagerFactory sau khi tất cả các test chạy xong
        if (emf != null) {
            emf.close();
        }
    }
    @Test
    public void testLichSuVatTuTrongKho() {
        //Test thêm một đối tượng vào database
        //Kiểm tra xem đối tượng đã được thêm vào chưa
        GeneralDAO generalDAO = new GeneralDAO();
        LichSuVatTuTrongKho lichSuVatTuTrongKho = new LichSuVatTuTrongKho();
        List<VatTuTrongKho> vatTuTrongKhoList = generalDAO.findAll(VatTuTrongKho.class);
        List<NhanVien> nhanVienList = generalDAO.findAll(NhanVien.class);
        if(vatTuTrongKhoList.isEmpty() || nhanVienList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        lichSuVatTuTrongKho.setVatTuTrongKho(vatTuTrongKhoList.get(0));
        lichSuVatTuTrongKho.setNhanVien(nhanVienList.get(0));
        lichSuVatTuTrongKho.setSoLuongThayDoi(10);
        lichSuVatTuTrongKho.setNgayThayDoi(LocalDate.now().atStartOfDay());
        boolean rsAdd= generalDAO.addOb(lichSuVatTuTrongKho);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        lichSuVatTuTrongKho.setSoLuongThayDoi(20);
        boolean rsUpdate= generalDAO.updateOb(lichSuVatTuTrongKho);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(lichSuVatTuTrongKho);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findOb(LichSuVatTuTrongKho.class, lichSuVatTuTrongKho.getMaLichSuVatTuTrongKho());
        generalDAO.findAll(LichSuVatTuTrongKho.class);

    }
    @Test
    public  void testLoaiDichVu() {
        GeneralDAO generalDAO = new GeneralDAO();
        LoaiDichVu loaiDichVu = new LoaiDichVu();
        loaiDichVu.setTenLoaiDichVu("Loai dich vu 1");
        loaiDichVu.setMoTa("Mo ta loai dich vu 1");
        boolean rsAdd= generalDAO.addOb(loaiDichVu);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(LoaiDichVu.class, loaiDichVu.getMaLoaiDichVu());
        loaiDichVu.setTenLoaiDichVu("Loai dich vu 2");
        boolean rsUpdate= generalDAO.updateOb(loaiDichVu);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(loaiDichVu);
        assert(rsDelete);
        System.out.println("Xóa thành công");

        generalDAO.findAll(LoaiDichVu.class);
    }
    @Test
    public void testLoaiNhanVien(){
        GeneralDAO generalDAO = new GeneralDAO();
        LoaiNhanVien loaiNhanVien = new LoaiNhanVien();
        loaiNhanVien.setTenLoaiNhanVien("Loai nhan vien 1");
        loaiNhanVien.setMoTa("Mo ta loai nhan vien 1");
        boolean rsAdd= generalDAO.addOb(loaiNhanVien);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(LoaiNhanVien.class, loaiNhanVien.getMaLoaiNhanVien());
        loaiNhanVien.setTenLoaiNhanVien("Loai nhan vien 2");
        boolean rsUpdate= generalDAO.updateOb(loaiNhanVien);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(loaiNhanVien);
        assert(rsDelete);
        System.out.println("Xóa thành công");

        generalDAO.findAll(LoaiNhanVien.class);
    }
    @Test
    public void testLoaiPhong(){
        GeneralDAO generalDAO = new GeneralDAO();
        LoaiPhong loaiPhong = new LoaiPhong();
        loaiPhong.setTenLoaiPhong("Loai phong 1");
        loaiPhong.setMoTa("Mo ta loai phong 1");
        boolean rsAdd= generalDAO.addOb(loaiPhong);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(LoaiPhong.class, loaiPhong.getMaLoaiPhong());
        loaiPhong.setTenLoaiPhong("Loai phong 2");
        boolean rsUpdate= generalDAO.updateOb(loaiPhong);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(loaiPhong);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(LoaiPhong.class);
    }
    @Test
    public  void testLoaiPhuThu(){
        GeneralDAO generalDAO = new GeneralDAO();
        LoaiPhuThu loaiPhuThu = new LoaiPhuThu();
        loaiPhuThu.setTenLoaiPhuThu("Loai phu thu 1");
        loaiPhuThu.setMoTa("Mo ta loai phu thu 1");
        boolean rsAdd= generalDAO.addOb(loaiPhuThu);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(LoaiPhuThu.class, loaiPhuThu.getMaLoaiPhuThu());
        loaiPhuThu.setTenLoaiPhuThu("Loai phu thu 2");
        boolean rsUpdate= generalDAO.updateOb(loaiPhuThu);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(loaiPhuThu);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(LoaiPhuThu.class);
    }
    @Test
    public void testLoaiVatTu(){
        GeneralDAO generalDAO = new GeneralDAO();
        LoaiVatTu loaiVatTu = new LoaiVatTu();
        loaiVatTu.setTenLoaiVatTu("Loai vat tu 1");
        loaiVatTu.setMoTa("Mo ta loai vat tu 1");
        boolean rsAdd= generalDAO.addOb(loaiVatTu);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(LoaiVatTu.class, loaiVatTu.getMaLoaiVatTu());
        loaiVatTu.setTenLoaiVatTu("Loai vat tu 2");
        boolean rsUpdate= generalDAO.updateOb(loaiVatTu);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(loaiVatTu);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(LoaiVatTu.class);
    }
    @Test
    public void testNhanVien() {
        //Test thêm một đối tượng vào database
        //Kiểm tra xem đối tượng đã được thêm vào chưa
        NhanVien nv = new NhanVien();
        nv.setTenNhanVien("Mai Duc Truong");

        nv.setSoDienThoai("0234567899");
        nv.setCanCuocCongDan("123456789102");
        nv.setEmail("truong@gmail.com");
        nv.setDiaChi("Ha Noi");
        nv.setGioiTinh(true);
        nv.setNgaySinh(LocalDate.of(1999, 12, 12));
        nv.setTrangThai("Đang làm việc");
        nv.setLoaiNhanVien(em.find(LoaiNhanVien.class, "LNV01"));
        GeneralDAO nhanVienDAO = new GeneralDAO();
        boolean rsAdd= nhanVienDAO.addOb(nv);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        nhanVienDAO.findOb(NhanVien.class, nv.getMaNhanVien());
        nv.setTenNhanVien("Ten dai hon");
        boolean rsUpdate= nhanVienDAO.updateOb(nv);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        nhanVienDAO.findOb(NhanVien.class, nv.getMaNhanVien());
        boolean rsDelete= nhanVienDAO.deleteOb(nv);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        nhanVienDAO.findOb(NhanVien.class, nv.getMaNhanVien());
        nhanVienDAO.findAll(NhanVien.class);
    }
    @Test
    public void testPhong(){
        GeneralDAO generalDAO = new GeneralDAO();
        Phong phong = new Phong();
        List<LoaiPhong> loaiPhongList = generalDAO.findAll(LoaiPhong.class);
        if(loaiPhongList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        phong.setLoaiPhong(loaiPhongList.get(0));
        phong.setViTri("trang 8");
        phong.setTrangThaiPhong(TrangThaiPhong.DANG_SU_DUNG);
        boolean rsAdd= generalDAO.addOb(phong);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(Phong.class, phong.getMaPhong());
        phong.setViTri("trang 9");
        boolean rsUpdate= generalDAO.updateOb(phong);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(phong);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(Phong.class);
    }
    @Test
    public  void testPhuThu(){
        GeneralDAO generalDAO = new GeneralDAO();
        PhuThu phuThu = new PhuThu();
        List<LoaiPhuThu> loaiPhuThuList = generalDAO.findAll(LoaiPhuThu.class);
        if(loaiPhuThuList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        phuThu.setLoaiPhuThu(loaiPhuThuList.get(0));
        phuThu.setTenPhuThu("Phu thu 1");
        boolean rsAdd= generalDAO.addOb(phuThu);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(PhuThu.class, phuThu.getMaPhuThu());
        phuThu.setTenPhuThu("Phu thu 2");
        boolean rsUpdate= generalDAO.updateOb(phuThu);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(phuThu);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(PhuThu.class);
    }
    @Test
    public void testTaiKhoan(){
        GeneralDAO generalDAO = new GeneralDAO();
        TaiKhoan taiKhoan = new TaiKhoan();
        List<NhanVien> nhanVienList = generalDAO.findAll(NhanVien.class);
        if(nhanVienList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        taiKhoan.setNhanVien(nhanVienList.get(0));
        taiKhoan.setMatKhau(PasswordHasher.hashPassword( PasswordHasher.generatePassword(6),PasswordHasher.generateSalt()));
        // Assert that the ConstraintViolationException is thrown
        assertThrows(ConstraintViolationException.class, () -> {
            generalDAO.addOb(taiKhoan);
        });
        List<TaiKhoan> taiKhoanList = generalDAO.findAll(TaiKhoan.class);
        if(taiKhoanList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        generalDAO.findOb(TaiKhoan.class,taiKhoanList.get(0));
        taiKhoan.setMatKhau("654321");
        boolean rsUpdate= generalDAO.updateOb(taiKhoan);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(taiKhoanList.get(0));
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(TaiKhoan.class);
    }
    @Test
    public void testTaiLieuChungCu(){
        GeneralDAO generalDAO = new GeneralDAO();
        List<ChiTietDonBaoCao> chiTietDonBaoCaoList = generalDAO.findAll(ChiTietDonBaoCao.class);
        if(chiTietDonBaoCaoList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        TaiLieuChungCu taiLieuChungCu = new TaiLieuChungCu();
        taiLieuChungCu.setChiTietDonBaoCao(chiTietDonBaoCaoList.get(0));
        taiLieuChungCu.setHinhAnh("Tai lieu 1");
        boolean rsAdd= generalDAO.addOb(taiLieuChungCu);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(TaiLieuChungCu.class, taiLieuChungCu.getMaTaiLieu());
        taiLieuChungCu.setHinhAnh("Tai lieu 2");
        boolean rsUpdate= generalDAO.updateOb(taiLieuChungCu);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(taiLieuChungCu);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(TaiLieuChungCu.class);
    }
    @Test
    public void testVatTu(){
        GeneralDAO generalDAO = new GeneralDAO();
        VatTu vatTu = new VatTu();
        List<LoaiVatTu> loaiVatTuList = generalDAO.findAll(LoaiVatTu.class);
        if(loaiVatTuList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        vatTu.setLoaiVatTu(loaiVatTuList.get(0));
        vatTu.setTenVatTu("Vat tu 1");
        vatTu.setDonViTinh("kg");;
        boolean rsAdd= generalDAO.addOb(vatTu);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(VatTu.class, vatTu.getMaVatTu());
        vatTu.setTenVatTu("Vat tu 2");
        boolean rsUpdate= generalDAO.updateOb(vatTu);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(vatTu);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(VatTu.class);
    }
    @Test
    public void testVatTuTrongKho(){
        GeneralDAO generalDAO = new GeneralDAO();
        VatTuTrongKho vatTuTrongKho = new VatTuTrongKho();
        List<VatTu> vatTuList = generalDAO.findAll(VatTu.class);
        List<Kho> khoList = generalDAO.findAll(Kho.class);
        if(vatTuList.isEmpty() || khoList.isEmpty()){
            System.out.println("Không có dữ liệu để thêm");
            return;
        }
        vatTuTrongKho.setVatTu(vatTuList.get(0));
        vatTuTrongKho.setKho(khoList.get(0));
        vatTuTrongKho.setSoLuong(10);
        boolean rsAdd= generalDAO.addOb(vatTuTrongKho);
        assert(rsAdd);
        System.out.println("Thêm thành công");
        generalDAO.findOb(VatTuTrongKho.class, vatTuTrongKho.getMaVatTuTrongKho());
        vatTuTrongKho.setSoLuong(20);
        boolean rsUpdate= generalDAO.updateOb(vatTuTrongKho);
        assert(rsUpdate);
        System.out.println("Cập nhật thành công");
        boolean rsDelete= generalDAO.deleteOb(vatTuTrongKho);
        assert(rsDelete);
        System.out.println("Xóa thành công");
        generalDAO.findAll(VatTuTrongKho.class);
    }

}