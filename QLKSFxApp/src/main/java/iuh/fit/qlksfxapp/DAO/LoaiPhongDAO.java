package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.LoaiPhong;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for LoaiPhong DAO operations
 */
public interface LoaiPhongDAO extends GeneralDAO {
    
    /**
     * Get all room types
     * @return A list of all room types
     */
    List<LoaiPhong> getAllLoaiPhong() throws RemoteException;
    
    /**
     * Find room type by ID
     * @param maLoaiPhong The ID of the room type
     * @return The found room type or null if not found
     */
    LoaiPhong findByMaLoaiPhong(String maLoaiPhong) throws RemoteException;
    
    /**
     * Find room type by name
     * @param tenLoaiPhong The name to search for
     * @return The found room type or null if not found
     */
    LoaiPhong findByTenLoaiPhong(String tenLoaiPhong) throws RemoteException;
}

