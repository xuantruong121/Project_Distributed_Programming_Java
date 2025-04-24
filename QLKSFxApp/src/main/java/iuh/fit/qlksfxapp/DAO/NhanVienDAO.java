package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for NhanVien DAO operations
 */
public interface NhanVienDAO extends GeneralDAO {

    /**
     * Get all employees
     * @return A list of all employees
     */
    List<NhanVien> getAllNhanVien() throws RemoteException;

    /**
     * Find employee by ID
     * @param maNhanVien The ID of the employee
     * @return The found employee or null if not found
     */
    NhanVien findByMaNhanVien(String maNhanVien) throws RemoteException;

    /**
     * Find employees by name (partial match)
     * @param tenNhanVien The name to search for
     * @return A list of employees with matching names
     */
    List<NhanVien> searchByName(String tenNhanVien) throws RemoteException;

    /**
     * Find employees by employee type
     * @param maLoaiNhanVien The employee type ID
     * @return A list of employees with the specified employee type
     */
    List<NhanVien> findByLoaiNhanVien(String maLoaiNhanVien) throws RemoteException;

    /**
     * Find employee by phone number
     * @param soDienThoai The phone number to search for
     * @return The found employee or null if not found
     */
    NhanVien findBySoDienThoai(String soDienThoai) throws RemoteException;

    /**
     * Find employee by CCCD (Citizen ID)
     * @param cccd The CCCD to search for
     * @return The found employee or null if not found
     */
    NhanVien findByCCCD(String cccd) throws RemoteException;
}


//package iuh.fit.qlksfxapp.DAO;
//
//import iuh.fit.qlksfxapp.Entity.NhanVien;
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//
//public class NhanVienDAO extends GeneralDAO {
//
//    public NhanVienDAO() {
//        super();
//    }
//
//    // Get all employees
//    public List<NhanVien> getAllNhanVien() {
//        return findAll(NhanVien.class);
//    }
//
//    // Find employee by ID
//    public NhanVien findByMaNhanVien(String maNhanVien) {
//        return findOb(NhanVien.class, maNhanVien);
//    }
//
//    // Find employees by name (partial match)
//    public List<NhanVien> findByTenNhanVien(String tenNhanVien) {
//        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
//        try {
//            return em.createQuery(
//                            "SELECT nv FROM NhanVien nv WHERE nv.tenNhanVien LIKE :tenNhanVien",
//                            NhanVien.class)
//                    .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
//                    .getResultList();
//        } finally {
//            em.close();
//        }
//    }
//}
