package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.DichVu;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for DichVu DAO operations
 */
public interface DichVuDAO extends GeneralDAO {
    
    /**
     * Get all services
     * @return A list of all services
     */
    List<DichVu> getAllDichVu() throws RemoteException;
    
    /**
     * Find service by ID
     * @param maDichVu The ID of the service
     * @return The found service or null if not found
     */
    DichVu findByMaDichVu(String maDichVu) throws RemoteException;
    
    /**
     * Find services by service type
     * @param maLoaiDichVu The service type ID
     * @return A list of services with the specified service type
     */
    List<DichVu> findByLoaiDichVu(String maLoaiDichVu) throws RemoteException;
    
    /**
     * Search services by name
     * @param tenDichVu The name to search for
     * @return A list of services matching the search criteria
     */
    List<DichVu> searchByName(String tenDichVu) throws RemoteException;
}

