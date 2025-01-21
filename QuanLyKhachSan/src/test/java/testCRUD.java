import DAO.NhanVienDAO;
import Entity.NhanVien;
import org.junit.jupiter.api.Test;

public class testCRUD {
    @Test
    void testAddNhanVien() {
        Entity.NhanVien nv = new Entity.NhanVien();
        nv.setMa("NV01");
        nv.setTen("Nguyen Van A");
        nv.setNgaySinh("01/01/2000");
        nv.setGioiTinh("Nam");
        nv.setDiaChi("Ha Noi");
        nv.setSoDienThoai("0123456789");
        nv.setChucVu("Nhan vien");
        NhanVienDAO nvDAO = new DAO.NhanVienDAO();
        assert nvDAO.add(nv);
    }
    @Test
    void testUpdateNhanVien(){

    }
}
