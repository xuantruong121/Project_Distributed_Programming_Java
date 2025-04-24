package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.TaiKhoanDAO;
import iuh.fit.qlksfxapp.Entity.LoaiNhanVien;
import iuh.fit.qlksfxapp.Entity.NhanVien;
import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import iuh.fit.qlksfxapp.util.PasswordHasher;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Triển khai cục bộ của TaiKhoanDAO để sử dụng khi không thể kết nối đến RMI Server
 * Lưu ý: Đây chỉ là giải pháp tạm thời để kiểm tra ứng dụng
 */
public class LocalTaiKhoanDAOImpl implements TaiKhoanDAO {

    private static final Map<String, TaiKhoan> taiKhoanMap = new HashMap<>();

    static {
        // Tạo một số tài khoản mẫu để kiểm tra
        initSampleAccounts();
    }

    private static void initSampleAccounts() {
        try {
            // Tạo loại nhân viên
            LoaiNhanVien loaiNhanVien = new LoaiNhanVien();
            loaiNhanVien.setMaLoaiNhanVien("LNV01");
            loaiNhanVien.setTenLoaiNhanVien("Nhân viên quản lý");

            // Tạo nhân viên admin
            NhanVien adminNV = new NhanVien();
            adminNV.setMaNhanVien("admin");
            adminNV.setTenNhanVien("Administrator");
            adminNV.setSoDienThoai("0123456789");
            adminNV.setCanCuocCongDan("123456789012");
            adminNV.setEmail("admin@example.com");
            adminNV.setDiaChi("123 Admin Street");
            adminNV.setNgaySinh(LocalDate.of(1990, 1, 1));
            adminNV.setGioiTinh(true);
            adminNV.setTrangThai("Đang làm việc");
            adminNV.setLoaiNhanVien(loaiNhanVien);

            // Tạo tài khoản admin với mật khẩu "Admin@123"
            TaiKhoan adminTK = new TaiKhoan();
            adminTK.setNhanVien(adminNV);
            // Mật khẩu dạng văn bản thường (không băm)
            adminTK.setMatKhau("Admin@123");

            // Thêm vào map
            taiKhoanMap.put(adminNV.getMaNhanVien(), adminTK);

            // Tạo nhân viên user
            NhanVien userNV = new NhanVien();
            userNV.setMaNhanVien("user");
            userNV.setTenNhanVien("Regular User");
            userNV.setSoDienThoai("0987654321");
            userNV.setCanCuocCongDan("210987654321");
            userNV.setEmail("user@example.com");
            userNV.setDiaChi("456 User Avenue");
            userNV.setNgaySinh(LocalDate.of(1995, 5, 5));
            userNV.setGioiTinh(false);
            userNV.setTrangThai("Đang làm việc");
            userNV.setLoaiNhanVien(loaiNhanVien);

            // Tạo tài khoản user với mật khẩu "User@123"
            TaiKhoan userTK = new TaiKhoan();
            userTK.setNhanVien(userNV);
            // Mật khẩu dạng văn bản thường (không băm)
            userTK.setMatKhau("User@123");

            // Thêm vào map
            taiKhoanMap.put(userNV.getMaNhanVien(), userTK);

        } catch (Exception e) {
            System.err.println("Error initializing sample accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() throws RemoteException {
        return new ArrayList<>(taiKhoanMap.values());
    }

    @Override
    public TaiKhoan findByMaTaiKhoan(String maTaiKhoan) throws RemoteException {
        return taiKhoanMap.get(maTaiKhoan);
    }

    @Override
    public TaiKhoan findByUsername(String username) throws RemoteException {
        return taiKhoanMap.get(username);
    }

    @Override
    public TaiKhoan authenticate(String username, String password) throws RemoteException {
        TaiKhoan taiKhoan = taiKhoanMap.get(username);

        if (taiKhoan != null) {
            // Kiểm tra mật khẩu trực tiếp (không băm)
            if (password.equals(taiKhoan.getMatKhau())) {
                return taiKhoan;
            }
        }

        return null;
    }

    // Triển khai các phương thức của GeneralDAO
    @Override
    public <T> boolean addOb(T ob) throws RemoteException {
        if (ob instanceof TaiKhoan) {
            TaiKhoan taiKhoan = (TaiKhoan) ob;
            taiKhoanMap.put(taiKhoan.getNhanVien().getMaNhanVien(), taiKhoan);
            return true;
        }
        return false;
    }

    @Override
    public <T> boolean updateOb(T ob) throws RemoteException {
        if (ob instanceof TaiKhoan) {
            TaiKhoan taiKhoan = (TaiKhoan) ob;
            if (taiKhoanMap.containsKey(taiKhoan.getNhanVien().getMaNhanVien())) {
                taiKhoanMap.put(taiKhoan.getNhanVien().getMaNhanVien(), taiKhoan);
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> boolean deleteOb(T ob) throws RemoteException {
        if (ob instanceof TaiKhoan) {
            TaiKhoan taiKhoan = (TaiKhoan) ob;
            if (taiKhoanMap.containsKey(taiKhoan.getNhanVien().getMaNhanVien())) {
                taiKhoanMap.remove(taiKhoan.getNhanVien().getMaNhanVien());
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> T findOb(Class<T> entityClass, Object id) throws RemoteException {
        if (entityClass.equals(TaiKhoan.class) && id instanceof String) {
            return entityClass.cast(taiKhoanMap.get(id));
        }
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) throws RemoteException {
        if (entityClass.equals(TaiKhoan.class)) {
            List<T> result = new ArrayList<>();
            for (TaiKhoan taiKhoan : taiKhoanMap.values()) {
                result.add(entityClass.cast(taiKhoan));
            }
            return result;
        }
        return new ArrayList<>();
    }
}
