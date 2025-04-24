package iuh.fit.qlksfxapp.RMI;

import iuh.fit.qlksfxapp.DAO.*;
import iuh.fit.qlksfxapp.Entity.*;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RMIClientMenu {
    private static Registry registry;
    private static Scanner scanner;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // DAO interfaces
    private static TaiKhoanDAO taiKhoanDAO;
    private static CaLamViecDAO caLamViecDAO;
    private static HoaDonDAO hoaDonDAO;
    private static PhongDAO phongDAO;
    private static KhachHangDAO khachHangDAO;
    private static DichVuDAO dichVuDAO;
    private static NhanVienDAO nhanVienDAO;
    private static DonDatPhongDAO donDatPhongDAO;
    private static ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    private static ChiTietDichVuDAO chiTietDichVuDAO;

    public static void main(String[] args) {
        try {
            // Initialize scanner
            scanner = new Scanner(System.in);

            // Set system properties for RMI
            String projectDir = System.getProperty("user.dir");
            System.setProperty("java.security.policy", projectDir + "/rmi.policy");

            // Get the registry
            registry = LocateRegistry.getRegistry("192.168.99.238", 9090);

            // Look up the DAO services from the registry
            taiKhoanDAO = (TaiKhoanDAO) registry.lookup("taiKhoanDAO");
            caLamViecDAO = (CaLamViecDAO) registry.lookup("caLamViecDAO");
            hoaDonDAO = (HoaDonDAO) registry.lookup("hoaDonDAO");
            phongDAO = (PhongDAO) registry.lookup("phongDAO");
            khachHangDAO = (KhachHangDAO) registry.lookup("khachHangDAO");
            dichVuDAO = (DichVuDAO) registry.lookup("dichVuDAO");
            nhanVienDAO = (NhanVienDAO) registry.lookup("nhanVienDAO");
            donDatPhongDAO = (DonDatPhongDAO) registry.lookup("donDatPhongDAO");
            chiTietDonDatPhongDAO = (ChiTietDonDatPhongDAO) registry.lookup("chiTietDonDatPhongDAO");
            chiTietDichVuDAO = (ChiTietDichVuDAO) registry.lookup("chiTietDichVuDAO");

            System.out.println("Connected to RMI Server successfully!");

            // Display the main menu
            showMainMenu();

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void showMainMenu() {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY KHACH SAN =====");
            System.out.println("1. Quan ly Tai Khoan");
            System.out.println("2. Quan ly Ca Lam Viec");
            System.out.println("3. Quan ly Hoa Don");
            System.out.println("4. Quan ly Phong");
            System.out.println("5. Quan ly Khach Hang");
            System.out.println("6. Quan ly Dich Vu");
            System.out.println("7. Quan ly Nhan Vien");
            System.out.println("8. Quan ly Don Dat Phong");
            System.out.println("9. Quan ly Chi Tiet Don Dat Phong");
            System.out.println("10. Quan ly Chi Tiet Dich Vu");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manageTaiKhoan();
                        break;
                    case 2:
                        manageCaLamViec();
                        break;
                    case 3:
                        manageHoaDon();
                        break;
                    case 4:
                        managePhong();
                        break;
                    case 5:
                        manageKhachHang();
                        break;
                    case 6:
                        manageDichVu();
                        break;
                    case 7:
                        manageNhanVien();
                        break;
                    case 8:
                        manageDonDatPhong();
                        break;
                    case 9:
                        manageChiTietDonDatPhong();
                        break;
                    case 10:
                        manageChiTietDichVu();
                        break;
                    case 0:
                        System.out.println("Cam on ban da su dung chuong trinh!");
                        break;
                    default:
                        System.out.println("Lua chon khong hop le. Vui long chon lai!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
                choice = -1;
            } catch (Exception e) {
                System.err.println("Loi: " + e.getMessage());
                e.printStackTrace();
                choice = -1;
            }
        } while (choice != 0);
    }

    // Quản lý Tài Khoản
    private static void manageTaiKhoan() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY TAI KHOAN =====");
            System.out.println("1. Xem danh sach tai khoan");
            System.out.println("2. Tim tai khoan theo ma");
            System.out.println("3. Them tai khoan moi");
            System.out.println("4. Cap nhat tai khoan");
            System.out.println("5. Xoa tai khoan");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách tài khoản
                    List<TaiKhoan> taiKhoans = taiKhoanDAO.getAllTaiKhoan();
                    System.out.println("\nDanh sach tai khoan:");
                    for (TaiKhoan tk : taiKhoans) {
                        System.out.println(tk);
                    }
                    break;
                case 2:
                    // Tìm tài khoản theo mã
                    System.out.print("Nhap ma tai khoan: ");
                    String maTK = scanner.nextLine();
                    TaiKhoan tk = taiKhoanDAO.findByMaTaiKhoan(maTK);
                    if (tk != null) {
                        System.out.println("Thong tin tai khoan: " + tk);
                    } else {
                        System.out.println("Khong tim thay tai khoan voi ma " + maTK);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Ca Làm Việc
    private static void manageCaLamViec() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY CA LAM =====");
            System.out.println("1. Xem danh sach ca lam");
            System.out.println("2. Tim ca lam theo ma");
            System.out.println("3. Them ca lam moi");
            System.out.println("4. Cap nhat ca lam");
            System.out.println("5. Xoa ca lam");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách ca làm
                    List<CaLamViec> caLams = caLamViecDAO.getAllCaLamViec();
                    System.out.println("\nDanh sach ca lam:");
                    for (CaLamViec cl : caLams) {
                        System.out.println(cl);
                    }
                    break;
                case 2:
                    // Tìm ca làm theo mã
                    System.out.print("Nhap ma ca lam: ");
                    String maCa = scanner.nextLine();
                    CaLamViec cl = caLamViecDAO.findByMaCaLam(maCa);
                    if (cl != null) {
                        System.out.println("Thong tin ca lam: " + cl);
                    } else {
                        System.out.println("Khong tim thay ca lam voi ma " + maCa);
                    }
                    break;
                case 3:
                    // Thêm ca làm mới
                    CaLamViec newCaLam = createCaLam();
                    caLamViecDAO.addOb(newCaLam);
                    System.out.println("Them ca lam thanh cong!");
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Hóa Đơn
    private static void manageHoaDon() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY HOA DON =====");
            System.out.println("1. Xem danh sach hoa don");
            System.out.println("2. Tim hoa don theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách hóa đơn
                    List<HoaDon> hoaDons = hoaDonDAO.getAllHoaDon();
                    System.out.println("\nDanh sach hoa don:");
                    for (HoaDon hd : hoaDons) {
                        System.out.println(hd);
                    }
                    break;
                case 2:
                    // Tìm hóa đơn theo mã
                    System.out.print("Nhap ma hoa don: ");
                    String maHD = scanner.nextLine();
                    HoaDon hd = hoaDonDAO.findByMaHoaDon(maHD);
                    if (hd != null) {
                        System.out.println("Thong tin hoa don: " + hd);
                    } else {
                        System.out.println("Khong tim thay hoa don voi ma " + maHD);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Phòng
    private static void managePhong() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY PHONG =====");
            System.out.println("1. Xem danh sach phong");
            System.out.println("2. Tim phong theo ma");
            System.out.println("3. Tim phong theo trang thai");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách phòng
                    List<Phong> phongs = phongDAO.getAllPhong();
                    System.out.println("\nDanh sach phong:");
                    for (Phong p : phongs) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    // Tìm phòng theo mã
                    System.out.print("Nhap ma phong: ");
                    String maPhong = scanner.nextLine();
                    Phong p = phongDAO.findByMaPhong(maPhong);
                    if (p != null) {
                        System.out.println("Thong tin phong: " + p);
                    } else {
                        System.out.println("Khong tim thay phong voi ma " + maPhong);
                    }
                    break;
                case 3:
                    // Tìm phòng theo trạng thái
                    System.out.println("Chọn trạng thái phòng:");
                    System.out.println("1. TRONG");
                    System.out.println("2. DANG_SU_DUNG");
                    System.out.println("3. DANG_DON_DEP");
                    System.out.println("4. DAT_TRUOC");
                    System.out.println("5. DANG_SUA_CHUA");
                    System.out.println("6. KHONG_SU_DUNG");
                    System.out.print("Chọn trạng thái: ");
                    int trangThaiChoice = Integer.parseInt(scanner.nextLine());

                    TrangThaiPhong trangThaiPhong = null;
                    switch (trangThaiChoice) {
                        case 1: trangThaiPhong = TrangThaiPhong.TRONG; break;
                        case 2: trangThaiPhong = TrangThaiPhong.DANG_SU_DUNG; break;
                        case 3: trangThaiPhong = TrangThaiPhong.DANG_DON_DEP; break;
                        case 4: trangThaiPhong = TrangThaiPhong.DAT_TRUOC; break;
                        case 5: trangThaiPhong = TrangThaiPhong.DANG_SUA_CHUA; break;
                        case 6: trangThaiPhong = TrangThaiPhong.KHONG_SU_DUNG; break;
                        default: System.out.println("Lựa chọn không hợp lệ!"); break;
                    }

                    if (trangThaiPhong != null) {
                        List<Phong> phongsByStatus = phongDAO.findByTrangThai(trangThaiPhong);
                        System.out.println("\nDanh sách phòng có trạng thái " + trangThaiPhong + ":");
                        if (phongsByStatus.isEmpty()) {
                            System.out.println("Không có phòng nào có trạng thái này.");
                        } else {
                            for (Phong phong : phongsByStatus) {
                                System.out.println(phong);
                            }
                        }
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Khách Hàng
    private static void manageKhachHang() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY KHACH HANG =====");
            System.out.println("1. Xem danh sach khach hang");
            System.out.println("2. Tim khach hang theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách khách hàng
                    List<KhachHang> khachHangs = khachHangDAO.getAllKhachHang();
                    System.out.println("\nDanh sach khach hang:");
                    for (KhachHang kh : khachHangs) {
                        System.out.println(kh);
                    }
                    break;
                case 2:
                    // Tìm khách hàng theo mã
                    System.out.print("Nhap ma khach hang: ");
                    String maKH = scanner.nextLine();
                    KhachHang kh = khachHangDAO.findByMaKhachHang(maKH);
                    if (kh != null) {
                        System.out.println("Thong tin khach hang: " + kh);
                    } else {
                        System.out.println("Khong tim thay khach hang voi ma " + maKH);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Dịch Vụ
    private static void manageDichVu() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY DICH VU =====");
            System.out.println("1. Xem danh sach dich vu");
            System.out.println("2. Tim dich vu theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách dịch vụ
                    List<DichVu> dichVus = dichVuDAO.getAllDichVu();
                    System.out.println("\nDanh sach dich vu:");
                    for (DichVu dv : dichVus) {
                        System.out.println(dv);
                    }
                    break;
                case 2:
                    // Tìm dịch vụ theo mã
                    System.out.print("Nhap ma dich vu: ");
                    String maDV = scanner.nextLine();
                    DichVu dv = dichVuDAO.findByMaDichVu(maDV);
                    if (dv != null) {
                        System.out.println("Thong tin dich vu: " + dv);
                    } else {
                        System.out.println("Khong tim thay dich vu voi ma " + maDV);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Nhân Viên
    private static void manageNhanVien() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY NHAN VIEN =====");
            System.out.println("1. Xem danh sach nhan vien");
            System.out.println("2. Tim nhan vien theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách nhân viên
                    List<NhanVien> nhanViens = nhanVienDAO.getAllNhanVien();
                    System.out.println("\nDanh sach nhan vien:");
                    for (NhanVien nv : nhanViens) {
                        System.out.println(nv);
                    }
                    break;
                case 2:
                    // Tìm nhân viên theo mã
                    System.out.print("Nhap ma nhan vien: ");
                    String maNV = scanner.nextLine();
                    NhanVien nv = nhanVienDAO.findByMaNhanVien(maNV);
                    if (nv != null) {
                        System.out.println("Thong tin nhan vien: " + nv);
                    } else {
                        System.out.println("Khong tim thay nhan vien voi ma " + maNV);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Đơn Đặt Phòng
    private static void manageDonDatPhong() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY DON DAT PHONG =====");
            System.out.println("1. Xem danh sach don dat phong");
            System.out.println("2. Tim don dat phong theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách đơn đặt phòng
                    List<DonDatPhong> donDatPhongs = donDatPhongDAO.getAllDonDatPhong();
                    System.out.println("\nDanh sach don dat phong:");
                    for (DonDatPhong ddp : donDatPhongs) {
                        System.out.println(ddp);
                    }
                    break;
                case 2:
                    // Tìm đơn đặt phòng theo mã
                    System.out.print("Nhap ma don dat phong: ");
                    String maDDP = scanner.nextLine();
                    DonDatPhong ddp = donDatPhongDAO.findByMaDonDatPhong(maDDP);
                    if (ddp != null) {
                        System.out.println("Thong tin don dat phong: " + ddp);
                    } else {
                        System.out.println("Khong tim thay don dat phong voi ma " + maDDP);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Chi Tiết Đơn Đặt Phòng
    private static void manageChiTietDonDatPhong() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY CHI TIET DON DAT PHONG =====");
            System.out.println("1. Xem danh sach chi tiet don dat phong");
            System.out.println("2. Tim chi tiet don dat phong theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách chi tiết đơn đặt phòng
                    List<ChiTietDonDatPhong> chiTietDonDatPhongs = chiTietDonDatPhongDAO.getAllChiTietDonDatPhong();
                    System.out.println("\nDanh sach chi tiet don dat phong:");
                    for (ChiTietDonDatPhong ctddp : chiTietDonDatPhongs) {
                        System.out.println(ctddp);
                    }
                    break;
                case 2:
                    // Tìm chi tiết đơn đặt phòng theo mã
                    System.out.print("Nhap ma chi tiet don dat phong: ");
                    String maCTDDP = scanner.nextLine();
                    ChiTietDonDatPhong ctddp = chiTietDonDatPhongDAO.findByMaChiTietDonDatPhong(maCTDDP);
                    if (ctddp != null) {
                        System.out.println("Thong tin chi tiet don dat phong: " + ctddp);
                    } else {
                        System.out.println("Khong tim thay chi tiet don dat phong voi ma " + maCTDDP);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Quản lý Chi Tiết Dịch Vụ
    private static void manageChiTietDichVu() throws Exception {
        int choice = 0;

        do {
            System.out.println("\n===== QUAN LY CHI TIET DICH VU =====");
            System.out.println("1. Xem danh sach chi tiet dich vu");
            System.out.println("2. Tim chi tiet dich vu theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách chi tiết dịch vụ
                    List<ChiTietDichVu> chiTietDichVus = chiTietDichVuDAO.getAllChiTietDichVu();
                    System.out.println("\nDanh sach chi tiet dich vu:");
                    for (ChiTietDichVu ctdv : chiTietDichVus) {
                        System.out.println(ctdv);
                    }
                    break;
                case 2:
                    // Tìm chi tiết dịch vụ theo mã
                    System.out.print("Nhap ma chi tiet dich vu: ");
                    String maCTDV = scanner.nextLine();
                    ChiTietDichVu ctdv = chiTietDichVuDAO.findByMaChiTietDichVu(maCTDV);
                    if (ctdv != null) {
                        System.out.println("Thong tin chi tiet dich vu: " + ctdv);
                    } else {
                        System.out.println("Khong tim thay chi tiet dich vu voi ma " + maCTDV);
                    }
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh");
                    break;
                default:
                    System.out.println("Chuc nang dang phat trien!");
            }
        } while (choice != 0);
    }

    // Helper method to create a new CaLamViec
    private static CaLamViec createCaLam() throws Exception {
        CaLamViec caLamViec = new CaLamViec();

        System.out.println("\n===== THEM CA LAM MOI =====");

        System.out.print("Nhap ma ca: ");
        String maCa = scanner.nextLine();
        caLamViec.setMaCaLam(maCa);

        System.out.print("Nhap ten ca lam: ");
        String tenCaLam = scanner.nextLine();
        caLamViec.setTenCaLam(tenCaLam);

        System.out.print("Nhap gio bat dau (HH:mm): ");
        String gioBatDauStr = scanner.nextLine();
        LocalTime thoiGianBatDau = LocalTime.parse(gioBatDauStr, timeFormatter);
        caLamViec.setThoiGianBatDau(thoiGianBatDau);

        System.out.print("Nhap gio ket thuc (HH:mm): ");
        String gioKetThucStr = scanner.nextLine();
        LocalTime thoiGianKetThuc = LocalTime.parse(gioKetThucStr, timeFormatter);
        caLamViec.setThoiGianKetThuc(thoiGianKetThuc);

        System.out.print("Nhap mo ta: ");
        String moTa = scanner.nextLine();
        caLamViec.setMoTa(moTa);

        return caLamViec;
    }

    private static void pressEnterToContinue() {
        System.out.println("\nNhan Enter de tiep tuc...");
        scanner.nextLine();
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public static void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        RMIClientMenu.dateTimeFormatter = dateTimeFormatter;
    }
}
