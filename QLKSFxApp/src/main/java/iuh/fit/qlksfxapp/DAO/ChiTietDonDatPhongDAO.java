package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for ChiTietDonDatPhong DAO operations
 */
public interface ChiTietDonDatPhongDAO extends GeneralDAO {
    
    /**
     * Get all booking details
     * @return A list of all booking details
     */
    List<ChiTietDonDatPhong> getAllChiTietDonDatPhong() throws RemoteException;
    
    /**
     * Find booking detail by ID
     * @param maChiTietDonDatPhong The ID of the booking detail
     * @return The found booking detail or null if not found
     */
    ChiTietDonDatPhong findByMaChiTietDonDatPhong(String maChiTietDonDatPhong) throws RemoteException;
    
    /**
     * Find booking details by booking ID
     * @param maDonDatPhong The booking ID
     * @return A list of booking details for the specified booking
     */
    List<ChiTietDonDatPhong> findByMaDonDatPhong(String maDonDatPhong) throws RemoteException;
    
    /**
     * Find booking details by room ID
     * @param maPhong The room ID
     * @return A list of booking details for the specified room
     */
    List<ChiTietDonDatPhong> findByMaPhong(String maPhong) throws RemoteException;
}

