package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
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
     * Get current booking for a specific room
     * @param maPhong The room ID
     * @return The current booking for the specified room or null if not found
     */
    DonDatPhong getDonDatPhongNowByIdPhong(String maPhong) throws RemoteException;
    
    /**
     * Get all current bookings
     * @return A list of all current bookings
     */
    List<DonDatPhong> getDatPhongNow() throws RemoteException;
    
    List<DonDatPhong> getListDonDatPhongTheoNgayDenVaNgayDi(LocalDateTime ngayDen, LocalDateTime ngayDi) throws RemoteException;
    List<DonDatPhong> getListDonDatPhongTheoSoNguoiLon(int soNguoiLon) throws RemoteException;
    List<DonDatPhong> getListDonDatPhongTheoSoTreEm(int soTreEm) throws RemoteException;
    List<DonDatPhong> getListDonDatPhongTheoSoNguoiLonVaTreEm(int soNguoiLon, int soTreEm) throws RemoteException;
    List<DonDatPhong> getListDonDatPhongTheoTenDoan(String tenDoan) throws RemoteException;
    List<DonDatPhong> getListDonDatPhongTheoTrangThaiPhongDAT_TRUOC() throws RemoteException;
    List<DonDatPhong> getListDonDatPhongTheoTrangThaiPhongDANG_SU_DUNG() throws RemoteException;
}
