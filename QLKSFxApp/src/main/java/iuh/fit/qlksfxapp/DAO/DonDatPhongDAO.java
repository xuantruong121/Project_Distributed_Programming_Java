package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;

import java.time.LocalDateTime;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for DonDatPhong DAO operations
 */
public interface DonDatPhongDAO extends GeneralDAO {
    
    /**
     * Get all bookings
     * @return A list of all bookings
     */
    List<DonDatPhong> getAllDonDatPhong() throws RemoteException;
    
    /**
     * Find booking by ID
     * @param maDonDatPhong The ID of the booking
     * @return The found booking or null if not found
     */
    DonDatPhong findByMaDonDatPhong(String maDonDatPhong) throws RemoteException;
    
    /**
     * Find bookings by customer ID
     * @param maKhachHang The customer ID
     * @return A list of bookings for the specified customer
     */
    List<DonDatPhong> findByMaKhachHang(String maKhachHang) throws RemoteException;
    
    /**
     * Find bookings by status
     * @param trangThai The status to search for
     * @return A list of bookings with the specified status
     */
    List<DonDatPhong> findByTrangThai(TrangThaiDonDatPhong trangThai) throws RemoteException;
    
    /**
     * Find bookings by date range
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of bookings within the date range
     */
    List<DonDatPhong> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException;
    
    /**
     * Get current booking for a specific room
     * @param maPhong The room ID
     * @return The current booking for the specified room or null if not found
     */
    DonDatPhong getDonDatPhongNowByIdPhong(String maPhong) throws RemoteException;
}

