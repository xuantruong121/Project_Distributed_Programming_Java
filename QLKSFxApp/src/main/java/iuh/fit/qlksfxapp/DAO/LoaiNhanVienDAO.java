package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.LoaiNhanVien;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for LoaiNhanVien DAO operations
 */
public interface LoaiNhanVienDAO extends GeneralDAO {
    
    /**
     * Get all employee types
     * @return A list of all employee types
     */
    List<LoaiNhanVien> getAllLoaiNhanVien() throws RemoteException;
    
    /**
     * Find employee type by ID
     * @param maLoaiNhanVien The ID of the employee type
     * @return The found employee type or null if not found
     */
    LoaiNhanVien findByMaLoaiNhanVien(String maLoaiNhanVien) throws RemoteException;
    
    /**
     * Find employee type by name
     * @param tenLoaiNhanVien The name to search for
     * @return The found employee type or null if not found
     */
    LoaiNhanVien findByTenLoaiNhanVien(String tenLoaiNhanVien) throws RemoteException;
}

