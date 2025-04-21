package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.HoaDon;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for HoaDon DAO operations
 */
public interface HoaDonDAO extends GeneralDAO {

    /**
     * Get all invoices
     * @return A list of all invoices
     */
    List<HoaDon> getAllHoaDon() throws RemoteException;

    /**
     * Find invoice by ID
     * @param maHoaDon The ID of the invoice
     * @return The found invoice or null if not found
     */
    HoaDon findByMaHoaDon(String maHoaDon) throws RemoteException;

    /**
     * Find invoices by date range
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of invoices within the date range
     */
    List<HoaDon> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException;

    /**
     * Find invoices by customer ID
     * @param maKhachHang The customer ID
     * @return A list of invoices for the specified customer
     */
    List<HoaDon> findByMaKhachHang(String maKhachHang) throws RemoteException;
}
