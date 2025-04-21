package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;

import java.time.LocalDateTime;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for Phong DAO operations
 */
public interface PhongDAO extends GeneralDAO {
    
    /**
     * Get all rooms
     * @return A list of all rooms
     */
    List<Phong> getAllPhong() throws RemoteException;
    
    /**
     * Find room by ID
     * @param maPhong The ID of the room
     * @return The found room or null if not found
     */
    Phong findByMaPhong(String maPhong) throws RemoteException;
    
    /**
     * Find rooms by status
     * @param trangThaiPhong The status to search for
     * @return A list of rooms with the specified status
     */
    List<Phong> findByTrangThai(TrangThaiPhong trangThaiPhong) throws RemoteException;
    
    /**
     * Find available rooms for a specific date range
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @return A list of available rooms for the specified date range
     */
    List<Phong> findAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) throws RemoteException;
    
    /**
     * Find rooms by room type
     * @param maLoaiPhong The room type ID
     * @return A list of rooms with the specified room type
     */
    List<Phong> findByLoaiPhong(String maLoaiPhong) throws RemoteException;
}

