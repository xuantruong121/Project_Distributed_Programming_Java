import DAO.NhanVienDAO;
import Entity.LoaiNhanVien;
import Entity.NhanVien;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class testCRUD {
    @Test
    void testAddNhanVien() {
        Entity.NhanVien nv = new Entity.NhanVien();
        nv.setMaNhanVien("NV01");
        nv.setTenNhanVien("Nguyen Van A");
        nv.setNgaySinh(LocalDate.parse("2000-01-01"));
        nv.setGioiTinh(true);
        nv.setDiaChi("Ha Noi");
        nv.setSoDienThoai("0123456789");
        LoaiNhanVien loaiNV = new LoaiNhanVien();
        loaiNV.setMaLoaiNhanVien("LNV01");
        loaiNV.setTenLoaiNhanVien("Nhân viên lễ tân");
        nv.setLoaiNhanVien(loaiNV);
        NhanVienDAO nvDAO = new DAO.NhanVienDAO();
        assert nvDAO.add(nv);
    }
    @Test
    void testUpdateNhanVien(){

    }
}
