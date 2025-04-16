package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.NhanVien;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NhanVienDAO extends GeneralDAO {

    public NhanVienDAO() {
        super();
    }

    // Get all employees
    public List<NhanVien> getAllNhanVien() {
        return findAll(NhanVien.class);
    }

    // Find employee by ID
    public NhanVien findByMaNhanVien(String maNhanVien) {
        return findOb(NhanVien.class, maNhanVien);
    }

    // Find employees by name (partial match)
    public List<NhanVien> findByTenNhanVien(String tenNhanVien) {
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT nv FROM NhanVien nv WHERE nv.tenNhanVien LIKE :tenNhanVien",
                            NhanVien.class)
                    .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
