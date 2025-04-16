package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.LoaiNhanVien;
import jakarta.persistence.EntityManager;

public class LoaiNhanVienDAO extends GeneralDAO {
    private EntityManager em = null;

    public LoaiNhanVienDAO() {
        super();
    }

    public LoaiNhanVien getLoaiNhanVienByMaNV(String maNV) {
        return findOb(LoaiNhanVien.class, maNV);
    }
}
