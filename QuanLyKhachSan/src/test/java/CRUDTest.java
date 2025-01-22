import DAO.EntityManagerUtil;
import DAO.GeneralDAO;
import Entity.LoaiNhanVien;
import Entity.NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

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
}