package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.NhanVien;

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

