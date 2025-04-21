package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.LoaiDichVu;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for LoaiDichVu DAO operations
 */
public interface LoaiDichVuDAO extends GeneralDAO {
    
    /**
     * Get all service types
     * @return A list of all service types
     */
    List<LoaiDichVu> getAllLoaiDichVu() throws RemoteException;
    
    /**
     * Find service type by ID
     * @param maLoaiDichVu The ID of the service type
     * @return The found service type or null if not found
     */
    LoaiDichVu findByMaLoaiDichVu(String maLoaiDichVu) throws RemoteException;
    
    /**
     * Find service type by name
     * @param tenLoaiDichVu The name to search for
     * @return The found service type or null if not found
     */
    LoaiDichVu findByTenLoaiDichVu(String tenLoaiDichVu) throws RemoteException;
}

