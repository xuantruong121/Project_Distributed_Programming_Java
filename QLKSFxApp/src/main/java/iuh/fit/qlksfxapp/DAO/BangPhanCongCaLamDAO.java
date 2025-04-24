package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.BangPhanCongCaLam;
import iuh.fit.qlksfxapp.Entity.CaLamViec;
import iuh.fit.qlksfxapp.Entity.NhanVien;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiBangPhanCongCaLam;

import java.time.LocalDate;
import java.rmi.RemoteException;
import java.util.List;

public interface BangPhanCongCaLamDAO extends GeneralDAO {
    /**
     * Find a BangPhanCongCaLam by its ID
     * @param maPhanCong The ID of the BangPhanCongCaLam
     * @return The found BangPhanCongCaLam or null if not found
     */
    BangPhanCongCaLam findByMaPhanCong(String maPhanCong) throws RemoteException;

    /**
     * Get all BangPhanCongCaLam entities
     * @return A list of all BangPhanCongCaLam entities
     */
    List<BangPhanCongCaLam> getAllBangPhanCongCaLam() throws RemoteException;

    /**
     * Find BangPhanCongCaLam by employee ID
     * @param maNhanVien The employee ID
     * @return A list of BangPhanCongCaLam entities for the specified employee
     */
    List<BangPhanCongCaLam> findByMaNhanVien(String maNhanVien) throws RemoteException;

    /**
     * Find BangPhanCongCaLam by work shift ID
     * @param maCaLam The work shift ID
     * @return A list of BangPhanCongCaLam entities for the specified work shift
     */
    List<BangPhanCongCaLam> findByMaCaLam(String maCaLam) throws RemoteException;

    /**
     * Find BangPhanCongCaLam by NhanVien entity
     * @param nhanVien The NhanVien entity
     * @return A list of BangPhanCongCaLam entities for the specified employee
     */
    List<BangPhanCongCaLam> findByNhanVien(NhanVien nhanVien) throws RemoteException;

    /**
     * Find BangPhanCongCaLam by CaLamViec entity
     * @param caLamViec The CaLamViec entity
     * @return A list of BangPhanCongCaLam entities for the specified work shift
     */
    List<BangPhanCongCaLam> findByCaLamViec(CaLamViec caLamViec) throws RemoteException;

    /**
     * Find BangPhanCongCaLam by date
     * @param ngayLamViec The work date
     * @return A list of BangPhanCongCaLam entities for the specified date
     */
    List<BangPhanCongCaLam> findByNgayLamViec(LocalDate ngayLamViec) throws RemoteException;

    /**
     * Find BangPhanCongCaLam by status
     * @param trangThai The status to search for
     * @return A list of BangPhanCongCaLam entities with the specified status
     */
    List<BangPhanCongCaLam> findByTrangThai(TrangThaiBangPhanCongCaLam trangThai) throws RemoteException;
}

