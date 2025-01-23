package Data;
import DAO.EntityManagerUtil;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import net.datafaker.Faker;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import util.NameStandardization;
import util.PasswordHasher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import Enum.*;

public class FakeData{
    private final Locale vietnam = new Locale("vi", "VN");
    private final Faker faker = new Faker(vietnam);

    public void addCaLamViec() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            CaLamViec caLamViec = new CaLamViec();
            caLamViec.setTenCaLam("Ca Sáng");
            caLamViec.setThoiGianBatDau(java.time.LocalTime.of(6, 0));
            caLamViec.setThoiGianKetThuc(java.time.LocalTime.of(14, 0));
            caLamViec.setMoTa("Làm việc từ 6h sáng đến 14h chiều");
            //
            CaLamViec caLamViec1 = new CaLamViec();
            caLamViec1.setTenCaLam("Ca Chiều");
            caLamViec1.setThoiGianBatDau(java.time.LocalTime.of(14, 0));
            caLamViec1.setThoiGianKetThuc(java.time.LocalTime.of(22, 0));
            caLamViec1.setMoTa("Làm việc từ 14h chiều đến 22h tối");
            //
            CaLamViec caLamViec2 = new CaLamViec();
            caLamViec2.setTenCaLam("Ca Tối");
            caLamViec2.setThoiGianBatDau(java.time.LocalTime.of(22, 0));
            caLamViec2.setThoiGianKetThuc(java.time.LocalTime.of(6, 0));
            caLamViec2.setMoTa("Làm việc từ 22h tối đến 6h sáng");
            //
            em.persist(caLamViec);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(caLamViec1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(caLamViec2);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

    }
    public void addDichVu() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        List<LoaiDichVu> loaiDichVuList = em.createQuery("SELECT l FROM LoaiDichVu l", LoaiDichVu.class).getResultList();
        try {
            Random random = new Random();
            em.getTransaction().begin();
            DichVu dichVu = new DichVu();
            dichVu.setMaDichVu(dichVu.generateMaDichVu());
            dichVu.setTenDichVu("Giặt sấy");
            dichVu.setDonViTinh("Kg");
            dichVu.setGiaDichVu(20000);
            dichVu.setHinhAnh(dichVu.getMaDichVu() + ".jpg");
            dichVu.setMoTa("Giặt sấy quần áo");
            dichVu.setTrangThai(true);
            dichVu.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu1 = new DichVu();
            dichVu1.setMaDichVu(dichVu1.generateMaDichVu());
            dichVu1.setTenDichVu("Massage");
            dichVu1.setDonViTinh("30 phút");
            dichVu1.setGiaDichVu(300000);
            dichVu1.setHinhAnh(dichVu1.getMaDichVu() + ".jpg");
            dichVu1.setMoTa("Massage giúp thư giãn cơ thể");
            dichVu1.setTrangThai(true);
            dichVu1.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu1);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu2 = new DichVu();
            dichVu2.setMaDichVu(dichVu2.generateMaDichVu());
            dichVu2.setTenDichVu("Dọn phòng");
            dichVu2.setDonViTinh("Lần");
            dichVu2.setGiaDichVu(10000);
            dichVu2.setHinhAnh(dichVu2.getMaDichVu() + ".jpg");
            dichVu2.setMoTa("Dọn phòng sạch sẽ");
            dichVu2.setTrangThai(true);
            dichVu2.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu2);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu3 = new DichVu();
            dichVu3.setMaDichVu(dichVu3.generateMaDichVu());
            dichVu3.setTenDichVu("Buffet ăn uống cho người lớn");
            dichVu3.setDonViTinh("Người");
            dichVu3.setGiaDichVu(300000);
            dichVu3.setHinhAnh(dichVu3.getMaDichVu() + ".jpg");
            dichVu3.setMoTa("Buffet ăn uống cho người lớn");
            dichVu3.setTrangThai(true);
            dichVu3.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu3);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu4 = new DichVu();
            dichVu4.setMaDichVu(dichVu4.generateMaDichVu());
            dichVu4.setTenDichVu("Buffet ăn uống cho trẻ em");
            dichVu4.setDonViTinh("Người");
            dichVu4.setGiaDichVu(100000);
            dichVu4.setHinhAnh(dichVu4.getMaDichVu() + ".jpg");//https://raw.githubusercontent.com/truong965/pic-truong/refs/heads/main/"
            dichVu4.setMoTa("Buffet ăn uống cho trẻ em");
            dichVu4.setTrangThai(true);
            dichVu4.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu4);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu5 = new DichVu();
            dichVu5.setMaDichVu(dichVu5.generateMaDichVu());
            dichVu5.setTenDichVu("Dịch vụ giữ trẻ");
            dichVu5.setDonViTinh("giờ");
            dichVu5.setGiaDichVu(50000);
            dichVu5.setHinhAnh(dichVu5.getMaDichVu() + ".jpg");
            dichVu5.setMoTa("Dịch vụ giữ trẻ");
            dichVu5.setTrangThai(true);
            dichVu5.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu5);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu6 = new DichVu();
            dichVu6.setMaDichVu(dichVu6.generateMaDichVu());
            dichVu6.setTenDichVu("Hồ bơi");
            dichVu6.setDonViTinh("Người");
            dichVu6.setGiaDichVu(10000);
            dichVu6.setHinhAnh(dichVu6.getMaDichVu() + ".jpg");
            dichVu6.setMoTa("Hồ bơi");
            dichVu6.setTrangThai(true);
            dichVu6.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu6);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            DichVu dichVu7 = new DichVu();
            dichVu7.setMaDichVu(dichVu7.generateMaDichVu());
            dichVu7.setTenDichVu("Giường phụ");
            dichVu7.setDonViTinh("Đêm");
            dichVu7.setGiaDichVu(100000);
            dichVu7.setHinhAnh(dichVu7.getMaDichVu() + ".jpg");
            dichVu7.setMoTa("Giường phụ");
            dichVu7.setTrangThai(true);
            dichVu7.setLoaiDichVu(loaiDichVuList.get(random.nextInt(loaiDichVuList.size())));
            em.persist(dichVu7);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addChuongTrinhKhuyenMai() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai();
        try {
            em.getTransaction().begin();
            chuongTrinhKhuyenMai.setMaChuongTrinhKhuyenMai(chuongTrinhKhuyenMai.generateMaChuongTrinhKhuyenMai());
            chuongTrinhKhuyenMai.setTenChuongTrinhKhuyenMai("Khuyến mãi 1/1");
            chuongTrinhKhuyenMai.setMoTa("Khuyến mãi 1/1 giảm giá 30% cho tất cả các dịch vụ");
            chuongTrinhKhuyenMai.setLoaiKhuyenMai(LoaiKhuyenMai.THEO_PHAN_TRAM);
            chuongTrinhKhuyenMai.setGiaTriKhuyenMai(30);
            chuongTrinhKhuyenMai.setNgayApDung(LocalDateTime.of(2022, 1, 1, 0, 0));
            chuongTrinhKhuyenMai.setNgayKetThuc(LocalDateTime.of(2022, 1, 10, 23, 59));
            chuongTrinhKhuyenMai.setTrangThai(false);
            chuongTrinhKhuyenMai.setHinhAnh(chuongTrinhKhuyenMai.getMaChuongTrinhKhuyenMai() + ".jpg");
            em.persist(chuongTrinhKhuyenMai);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addDieuKienApDung() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            DieuKienApDung dieuKienApDung = new DieuKienApDung();

            dieuKienApDung.setLoaiDieuKien(LoaiDieuKien.KHACH_HANG);
            dieuKienApDung.setGiaTriDieuKien(10);
            dieuKienApDung.setChuongTrinhKhuyenMai(em.find(ChuongTrinhKhuyenMai.class, "CT25-001"));
            dieuKienApDung.setMaDieuKien(dieuKienApDung.generateMaDieuKien());
            System.out.println(dieuKienApDung.getMaDieuKien());
            em.persist(dieuKienApDung);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addKhachHang() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            for (int i = 0; i <= 10; i++) {

                KhachHang khachHang = new KhachHang();
                khachHang.setMaKhachHang(khachHang.generateMaKhachHang());//210125+000001-000010
                khachHang.setTenKhachHang(NameStandardization.standardize( faker.name().fullName()));
                String[] twoFirstDigit =new String[]{ "03", "05", "07", "08", "09"};
                Random random = new Random();
                String twoDigit= twoFirstDigit[random.nextInt(twoFirstDigit.length)];
                khachHang.setSoDienThoai(twoDigit + faker.phoneNumber().subscriberNumber(8));
                khachHang.setCanCuocCongDan(faker.number().digits(12));
                khachHang.setEmail(faker.internet().emailAddress());
                int minAge = 15;
                int maxAge = 60;
                // Calculate year range
                int currentYear = LocalDate.now().getYear();
                int startYear = currentYear - maxAge;
                int endYear = currentYear - minAge;
                // Generate random year, month, and day

                int year = startYear + random.nextInt(endYear - startYear + 1);
                int month = 1 + random.nextInt(12);
                int day = 1 + random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth());
                // Create the birthdate
                LocalDate birthdate = LocalDate.of(year, month, day);
                // Convert Date to LocalDate for modern Java usage
                khachHang.setNgaySinh(birthdate);
                khachHang.setGioiTinh(faker.bool().bool());
                khachHang.setDiemTichLuy(faker.number().numberBetween(0, 100));
                khachHang.setQuocTich(faker.address().country());
//                System.out.println(khachHang.toString());
                // Tạo factory để tạo validator
                ValidatorFactory factory = Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new ParameterMessageInterpolator())
                        .buildValidatorFactory();
                Validator validator = factory.getValidator();

                // Kiểm tra validation
                Set<ConstraintViolation<KhachHang>> violations = validator.validate(khachHang);
                if (!violations.isEmpty()) {
                    for (ConstraintViolation<KhachHang> violation : violations) {
                        System.out.println(violation.getMessage());
                    }
                    // Nếu có vi phạm, không persist vào DB
                    return;
                }
                em.getTransaction().begin();
                em.persist(khachHang);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addKho() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            for (int i = 0; i < 2; i++) {
                em.getTransaction().begin();
                Kho kho = new Kho();
                kho.setMaKho(kho.generateMaKho());
                kho.setTenKho(faker.commerce().productName());
                kho.setViTri(faker.address().fullAddress());
                em.persist(kho);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addLoaiDichVu() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            LoaiDichVu loaiDichVu = new LoaiDichVu();
            loaiDichVu.setMaLoaiDichVu(loaiDichVu.generateMaLoaiDichVu());
            loaiDichVu.setTenLoaiDichVu("Dịch vụ ăn uống");
            em.persist(loaiDichVu);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiDichVu loaiDichVu1 = new LoaiDichVu();
            loaiDichVu1.setMaLoaiDichVu(loaiDichVu1.generateMaLoaiDichVu());
            loaiDichVu1.setTenLoaiDichVu("Dịch vụ giải trí");
            em.persist(loaiDichVu1);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiDichVu loaiDichVu2 = new LoaiDichVu();
            loaiDichVu2.setMaLoaiDichVu(loaiDichVu2.generateMaLoaiDichVu());
            loaiDichVu2.setTenLoaiDichVu("Dịch vụ giữ trẻ");
            em.persist(loaiDichVu2);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiDichVu loaiDichVu3 = new LoaiDichVu();
            loaiDichVu3.setMaLoaiDichVu(loaiDichVu3.generateMaLoaiDichVu());
            loaiDichVu3.setTenLoaiDichVu("Dịch vụ phòng");
            em.persist(loaiDichVu3);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiDichVu loaiDichVu4 = new LoaiDichVu();
            loaiDichVu4.setMaLoaiDichVu(loaiDichVu4.generateMaLoaiDichVu());
            loaiDichVu4.setTenLoaiDichVu("Dịch vụ khác");
            em.persist(loaiDichVu4);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addLoaiNhanVien() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {

            em.getTransaction().begin();
            LoaiNhanVien loaiNhanVien = new LoaiNhanVien();
            loaiNhanVien.setMaLoaiNhanVien(loaiNhanVien.generateMaLoaiNhanVien());
            loaiNhanVien.setTenLoaiNhanVien("Nhân viên quản lý");
            em.persist(loaiNhanVien);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiNhanVien loaiNhanVien1 = new LoaiNhanVien();
            loaiNhanVien1.setMaLoaiNhanVien(loaiNhanVien1.generateMaLoaiNhanVien());
            loaiNhanVien1.setTenLoaiNhanVien("Nhân viên lễ tân");
            em.persist(loaiNhanVien1);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiNhanVien loaiNhanVien2 = new LoaiNhanVien();
            loaiNhanVien2.setMaLoaiNhanVien(loaiNhanVien2.generateMaLoaiNhanVien());
            loaiNhanVien2.setTenLoaiNhanVien("Nhân viên buồng phòng");
            em.persist(loaiNhanVien2);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }

    }
    public void addLoaiPhong() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {

            em.getTransaction().begin();
            LoaiPhong loaiPhong = new LoaiPhong();
            loaiPhong.setMaLoaiPhong(loaiPhong.generateMaLoaiPhong());
            loaiPhong.setTenLoaiPhong("Standard");
            loaiPhong.setSoLuongNguoiLon(2);
            loaiPhong.setSoLuongTreEm(1);
            loaiPhong.setDienTich(12);
            loaiPhong.setGia(800000);
            loaiPhong.setMoTa(faker.lorem().sentence());
            em.persist(loaiPhong);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiPhong loaiPhong1 = new LoaiPhong();
            loaiPhong1.setMaLoaiPhong(loaiPhong1.generateMaLoaiPhong());
            loaiPhong1.setTenLoaiPhong("Superior");
            loaiPhong1.setSoLuongNguoiLon(4);
            loaiPhong1.setSoLuongTreEm(2);
            loaiPhong1.setDienTich(20);
            loaiPhong1.setGia(1600000);
            loaiPhong1.setMoTa(faker.lorem().sentence());
            em.persist(loaiPhong1);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiPhong loaiPhong2 = new LoaiPhong();
            loaiPhong2.setMaLoaiPhong(loaiPhong2.generateMaLoaiPhong());
            loaiPhong2.setTenLoaiPhong("Deluxe");
            loaiPhong2.setSoLuongNguoiLon(6);
            loaiPhong2.setSoLuongTreEm(3);
            loaiPhong2.setDienTich(56);
            loaiPhong2.setGia(2200000);
            loaiPhong2.setMoTa(faker.lorem().sentence());
            em.persist(loaiPhong2);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addLoaiPhuThu() {

        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            LoaiPhuThu loaiPhuThu = new LoaiPhuThu();
            loaiPhuThu.setMaLoaiPhuThu(loaiPhuThu.generateMaLoaiPhuThu());
            loaiPhuThu.setTenLoaiPhuThu("Phụ thu check-in sớm");
            em.persist(loaiPhuThu);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            LoaiPhuThu loaiPhuThu1 = new LoaiPhuThu();
            loaiPhuThu1.setMaLoaiPhuThu(loaiPhuThu1.generateMaLoaiPhuThu());
            loaiPhuThu1.setTenLoaiPhuThu("Phụ thu check-out muộn");
            em.persist(loaiPhuThu1);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addLoaiVatTu() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            for (int i = 0; i < 10; i++) {
                em.getTransaction().begin();
                LoaiVatTu loaiVatTu = new LoaiVatTu();
                loaiVatTu.setMaLoaiVatTu(loaiVatTu.generateMaLoaiVatTu());
                loaiVatTu.setTenLoaiVatTu(faker.commerce().department());
                em.persist(loaiVatTu);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }
    }
    public void addNhanVien() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {

            String[] loaiNV = new String[]{"LNV01", "LNV02", "LNV03"};
            String[] trangThai = new String[]{"Đang làm việc", "Đã nghỉ việc", "Nghỉ thai sản", "Nghỉ phép", "Nghỉ không lương"};
            Random random = new Random();

            for (int i = 0; i <= 10; i++) {
                String maLoaiNhanVien = loaiNV[random.nextInt(loaiNV.length)];
                NhanVien nhanVien = new NhanVien();
                nhanVien.setLoaiNhanVien(em.find(LoaiNhanVien.class, maLoaiNhanVien));
                nhanVien.setMaNhanVien(nhanVien.generateMaNhanVien());
                nhanVien.setTenNhanVien(faker.name().fullName());
                String[] twoFirstDigit =new String[]{ "03", "05", "07", "08", "09"};
                String twoDigit= twoFirstDigit[random.nextInt(twoFirstDigit.length)];
                nhanVien.setSoDienThoai(twoDigit + faker.phoneNumber().subscriberNumber(8));
                nhanVien.setCanCuocCongDan(faker.number().digits(12));
                nhanVien.setEmail(faker.internet().emailAddress());
                nhanVien.setDiaChi(faker.address().fullAddress());
                int minAge = 19;
                int maxAge = 60;
                // Calculate year range
                int currentYear = LocalDate.now().getYear();
                int startYear = currentYear - maxAge;
                int endYear = currentYear - minAge;
                // Generate random year, month, and day

                int year = startYear + random.nextInt(endYear - startYear + 1);
                int month = 1 + random.nextInt(12);
                int day = 1 + random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth());
                // Create the birthdate
                LocalDate birthdate = LocalDate.of(year, month, day);
                // Convert Date to LocalDate for modern Java usage
                nhanVien.setNgaySinh(birthdate);
                nhanVien.setGioiTinh(faker.bool().bool());
                nhanVien.setTrangThai(trangThai[random.nextInt(trangThai.length)]);

                // Tạo factory để tạo validator
                ValidatorFactory factory = Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new ParameterMessageInterpolator())
                        .buildValidatorFactory();
                Validator validator = factory.getValidator();

                // Kiểm tra validation
                Set<ConstraintViolation<NhanVien>> violations = validator.validate(nhanVien);
                if (!violations.isEmpty()) {
                    for (ConstraintViolation<NhanVien> violation : violations) {
                        System.out.println(violation.getMessage());
                    }
                    // Nếu có vi phạm, không persist vào DB
                    return;
                }
                em.getTransaction().begin();
                em.persist(nhanVien);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void addPhong() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            String[] loaiPhong = new String[]{"LP1", "LP2", "LP3"};
            String[] viTri = new String[]{"Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4"};
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 5; j++) {
                    em.getTransaction().begin();
                    Phong phong = new Phong();
                    phong.setLoaiPhong(em.find(LoaiPhong.class, loaiPhong[0]));
                    phong.setViTri(viTri[i]);
                    phong.setMoTa(faker.lorem().sentence());
                    phong.setTrangThaiPhong(TrangThaiPhong.TRONG);
                    phong.setMaPhong(phong.generateMaPhong());
                    phong.setHinhAnh(phong.getMaPhong() + ".jpg");
                    em.persist(phong);
                    em.getTransaction().commit();
                }
            }
            for (int i = 0; i < 6; i++) {
                em.getTransaction().begin();
                Phong phong = new Phong();
                phong.setLoaiPhong(em.find(LoaiPhong.class, loaiPhong[1]));
                phong.setViTri(((i + 1) % 6) != 0 ? viTri[2] : viTri[3]);
                phong.setMoTa(faker.lorem().sentence());
                phong.setTrangThaiPhong(TrangThaiPhong.TRONG);
                phong.setMaPhong(phong.generateMaPhong());
                phong.setHinhAnh(phong.getMaPhong() + ".jpg");
                em.persist(phong);
                em.getTransaction().commit();
            }
            for (int i = 0; i < 4; i++) {
                em.getTransaction().begin();
                Phong phong = new Phong();
                phong.setLoaiPhong(em.find(LoaiPhong.class, loaiPhong[2]));
                phong.setViTri(viTri[3]);
                phong.setMoTa(faker.lorem().sentence());
                phong.setTrangThaiPhong(TrangThaiPhong.TRONG);
                phong.setMaPhong(phong.generateMaPhong());
                phong.setHinhAnh(phong.getMaPhong() + ".jpg");
                em.persist(phong);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addPhuThu() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            PhuThu phuThu = new PhuThu();
            phuThu.setMaPhuThu(phuThu.generateMaPhuThu());
            phuThu.setTenPhuThu("Phụ thu check-out muộn 13h – 15h");
            phuThu.setGiaPhuThu(0.3);
            phuThu.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT02"));
            phuThu.setHinhAnh(phuThu.getMaPhuThu() + ".jpg");
            em.persist(phuThu);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            PhuThu phuThu1 = new PhuThu();
            phuThu1.setMaPhuThu(phuThu1.generateMaPhuThu());
            phuThu1.setTenPhuThu("Phụ thu check-out muộn 15h01 – 17h");
            phuThu1.setGiaPhuThu(0.5);
            phuThu1.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT02"));
            phuThu1.setHinhAnh(phuThu1.getMaPhuThu() + ".jpg");
            em.persist(phuThu1);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            PhuThu phuThu2 = new PhuThu();
            phuThu2.setMaPhuThu(phuThu2.generateMaPhuThu());
            phuThu2.setTenPhuThu("Phụ thu check-out muộn sau 17h");
            phuThu2.setGiaPhuThu(1);
            phuThu2.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT02"));
            phuThu2.setHinhAnh(phuThu2.getMaPhuThu() + ".jpg");
            em.persist(phuThu2);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            PhuThu phuThu3 = new PhuThu();
            phuThu3.setMaPhuThu(phuThu3.generateMaPhuThu());
            phuThu3.setTenPhuThu("Phụ thu check-in sớm trước 0h");
            phuThu3.setGiaPhuThu(1);
            phuThu3.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT01"));
            phuThu3.setHinhAnh(phuThu3.getMaPhuThu() + ".jpg");
            em.persist(phuThu3);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            PhuThu phuThu4 = new PhuThu();
            phuThu4.setMaPhuThu(phuThu4.generateMaPhuThu());
            phuThu4.setTenPhuThu("Phụ thu check-in sớm từ 0h01 – 5h");
            phuThu4.setGiaPhuThu(0.8);
            phuThu4.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT01"));
            phuThu4.setHinhAnh(phuThu4.getMaPhuThu() + ".jpg");
            em.persist(phuThu4);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            PhuThu phuThu5 = new PhuThu();
            phuThu5.setMaPhuThu(phuThu5.generateMaPhuThu());
            phuThu5.setTenPhuThu("Phụ thu check-in sớm từ 5h01 – 9h");
            phuThu5.setGiaPhuThu(0.5);
            phuThu5.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT01"));
            phuThu5.setHinhAnh(phuThu5.getMaPhuThu() + ".jpg");
            em.persist(phuThu5);
            em.getTransaction().commit();
            //
            em.getTransaction().begin();
            PhuThu phuThu6 = new PhuThu();
            phuThu6.setMaPhuThu(phuThu6.generateMaPhuThu());
            phuThu6.setTenPhuThu("Phụ thu check-in sớm từ 9h01 – 14h");
            phuThu6.setGiaPhuThu(0.3);
            phuThu6.setLoaiPhuThu(em.find(LoaiPhuThu.class, "LPT01"));
            phuThu6.setHinhAnh(phuThu6.getMaPhuThu() + ".jpg");
            em.persist(phuThu6);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addTaiKhoan() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            List<NhanVien> listNhanVien = EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
            for (NhanVien nhanVien : listNhanVien) {
                em.getTransaction().begin();
                TaiKhoan taiKhoan = new TaiKhoan();
                String ps = PasswordHasher.generatePassword(6);
                while (!TaiKhoan.checkPassW(ps)) {
                    ps = PasswordHasher.generatePassword(6);
                }
                taiKhoan.setMatKhau(PasswordHasher.hashPassword(ps, PasswordHasher.generateSalt()));
                taiKhoan.setNhanVien(nhanVien);
                em.persist(taiKhoan);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addVatTu() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            List<LoaiVatTu> listLoaiVatTu = em.createQuery("SELECT lvt FROM LoaiVatTu lvt", LoaiVatTu.class).getResultList();
            for (LoaiVatTu loaiVatTu : listLoaiVatTu) {
                for (int i = 0; i < 2; i++) {
                    em.getTransaction().begin();
                    VatTu vatTu = new VatTu();
                    vatTu.setMaVatTu(vatTu.generateMaVatTu());
                    vatTu.setTenVatTu(faker.commerce().productName());
                    vatTu.setLoaiVatTu(loaiVatTu);
                    vatTu.setGia(faker.number().numberBetween(10000, 100000));
                    List<String> units = Arrays.asList("kg", "liter", "meter", "cm", "inch", "g", "ml", "yard", "pound", "dozen");
                    Random random = new Random();
                    vatTu.setDonViTinh(units.get(random.nextInt(units.size())));
                    vatTu.setHinhAnh(vatTu.getMaVatTu() + ".jpg");
                    em.persist(vatTu);
                    em.getTransaction().commit();
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addVatTuTrongKho() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            List<Kho> listKho = em.createQuery("SELECT k FROM Kho k", Kho.class).getResultList();
            List<VatTu> listVatTu = em.createQuery("SELECT vt FROM VatTu vt", VatTu.class).getResultList();
            for (Kho kho : listKho) {
                for (VatTu vatTu : listVatTu) {
                    em.getTransaction().begin();
                    VatTuTrongKho vatTuTrongKho = new VatTuTrongKho();
                    vatTuTrongKho.setKho(kho);
                    vatTuTrongKho.setVatTu(vatTu);
                    vatTuTrongKho.setSoLuong(faker.number().numberBetween(10, 100));
                    vatTuTrongKho.setViTri(faker.address().fullAddress());
                    vatTuTrongKho.setSoLuongToiThieu(faker.number().numberBetween(1, 10));
                    em.persist(vatTuTrongKho);
                    em.getTransaction().commit();
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void addBangPhanCongCaLam(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            Random random = new Random();
            List<NhanVien> listNhanVien = em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
            List<CaLamViec> listCaLamViec = em.createQuery("SELECT clv FROM CaLamViec clv", CaLamViec.class).getResultList();

            for (NhanVien nhanVien : listNhanVien) {
                CaLamViec caLamViec = listCaLamViec.get(random.nextInt(listCaLamViec.size()));
                em.getTransaction().begin();
                BangPhanCongCaLam bangPhanCongCaLam = new BangPhanCongCaLam();
                bangPhanCongCaLam.setNhanVien(nhanVien);
                bangPhanCongCaLam.setCaLamViec(caLamViec);
                LocalDate minDate = LocalDate.now().minusYears(1);
                LocalDate maxDate = LocalDate.now().plusYears(1);
                long days = ChronoUnit.DAYS.between(minDate, maxDate);
                long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
                bangPhanCongCaLam.setNgayLamViec(minDate.plusDays(randomDays));
                if(bangPhanCongCaLam.getNgayLamViec().isAfter(LocalDate.now())) {
                    bangPhanCongCaLam.setTrangThai(TrangThaiBangPhanCongCaLam.DA_lEN_lICH);
                }else if(bangPhanCongCaLam.getNgayLamViec().isBefore(LocalDate.now())) {
                    bangPhanCongCaLam.setTrangThai(TrangThaiBangPhanCongCaLam.HOAN_THANH);
                }else{
                    bangPhanCongCaLam.setTrangThai(TrangThaiBangPhanCongCaLam.CHUA_HOAN_THANH);
                }
                em.persist(bangPhanCongCaLam);
                em.getTransaction().commit();

            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public  void addChiTietPhong(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            Random random = new Random();
            List<Phong> listPhong = em.createQuery("SELECT p FROM Phong p", Phong.class).getResultList();
            List<VatTu> listVatTu = em.createQuery("SELECT vt FROM VatTu vt", VatTu.class).getResultList();
            List<TrangThaiVatTu> listTrangThaiVatTu = Arrays.asList(TrangThaiVatTu.DANG_SU_DUNG, TrangThaiVatTu.DANG_SUA_CHUA, TrangThaiVatTu.HONG_HOC, TrangThaiVatTu.THAT_LAC);
            for (Phong phong : listPhong) {
                for(int i=0;i<random.nextInt(3);i++) {
                    ChiTietPhong chiTietPhong = new ChiTietPhong();
                    chiTietPhong.setPhong(phong);
                    chiTietPhong.setVatTu(listVatTu.get(random.nextInt(listVatTu.size())));
                    chiTietPhong.setSoLuong(random.nextInt(3));
                    chiTietPhong.setTrangThaiVatTu(listTrangThaiVatTu.get(random.nextInt(listTrangThaiVatTu.size())));
                    em.getTransaction().begin();
                    em.persist(chiTietPhong);
                    em.getTransaction().commit();
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addDonDatPhong(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            Random random = new Random();
            List<KhachHang> listKhachHang = em.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class).getResultList();
            List<NhanVien> listNhanVien = em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
            List<TrangThaiDonDatPhong> listTrangThaiDonDatPhong = Arrays.asList(TrangThaiDonDatPhong.DA_XAC_NHAN, TrangThaiDonDatPhong.DA_HUY, TrangThaiDonDatPhong.DA_HUY_VA_HOAN_TIEN, TrangThaiDonDatPhong.TAM_HOAN, TrangThaiDonDatPhong.KHONG_DEN);
            for (int i=0;i<5;i++) {
                DonDatPhong donDatPhong = new DonDatPhong();
                donDatPhong.setMaDonDatPhong(donDatPhong.generateMaDonDatPhong());
                donDatPhong.setNhanVien(listNhanVien.get(random.nextInt(listNhanVien.size())));
                donDatPhong.setKhachHang(listKhachHang.get(random.nextInt(listKhachHang.size())));
                LocalDateTime minDate = LocalDateTime.now().minusMonths(1);
                LocalDateTime maxDate = LocalDateTime.now().plusMonths(1);
                long seconds = ChronoUnit.SECONDS.between(minDate, maxDate);
                long randomSeconds = ThreadLocalRandom.current().nextLong(seconds + 1);
                donDatPhong.setNgayDat(minDate.plusSeconds(randomSeconds));
                donDatPhong.setNgayNhan(donDatPhong.getNgayDat().plusDays(random.nextInt(10)));
                donDatPhong.setNgayTra(donDatPhong.getNgayNhan().plusDays(random.nextInt(10)));
                donDatPhong.setSoLuongNguoiLon(random.nextInt(9)+1);
                donDatPhong.setSoLuongTreEm(random.nextInt(2));
                donDatPhong.setTienDatCoc(0);
                if(donDatPhong.getSoLuongNguoiLon()>6){
                    donDatPhong.setTenDoan(faker.name().fullName());
                }
                donDatPhong.setTrangThai(listTrangThaiDonDatPhong.get(random.nextInt(listTrangThaiDonDatPhong.size())));
                donDatPhong.setGhiChu(faker.lorem().sentence());

                // Tạo factory để tạo validator
                ValidatorFactory factory = Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new ParameterMessageInterpolator())
                        .buildValidatorFactory();
                Validator validator = factory.getValidator();

                // Kiểm tra validation
                Set<ConstraintViolation<DonDatPhong>> violations = validator.validate(donDatPhong);
                if (!violations.isEmpty()) {
                    for (ConstraintViolation<DonDatPhong> violation : violations) {
                        System.out.println(violation.getMessage());
                    }
                    // Nếu có vi phạm, không persist vào DB
                    return;
                }
                em.getTransaction().begin();
                em.persist(donDatPhong);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
//             System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
    public void addDoiTuongApDungKhuyenMai(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = em.createQuery("SELECT ctkm FROM ChuongTrinhKhuyenMai ctkm where ctkm.maChuongTrinhKhuyenMai = :maCT", ChuongTrinhKhuyenMai.class).setParameter("maCT", "CT25-001").getSingleResult();
            List<DichVu> listDichVu = em.createQuery("SELECT dv FROM DichVu dv", DichVu.class).getResultList();
            for (DichVu dv: listDichVu) {
                DoiTuongApDungKhuyenMai doiTuongApDungKhuyenMai = new DoiTuongApDungKhuyenMai();
                doiTuongApDungKhuyenMai.setChuongTrinhKhuyenMai(chuongTrinhKhuyenMai);
                doiTuongApDungKhuyenMai.setDichVu(dv);
                em.getTransaction().begin();
                em.persist(doiTuongApDungKhuyenMai);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addLichSuVatTuTrongKho(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {

            List<VatTuTrongKho> listVatTuTrongKho = em.createQuery("SELECT vttk FROM VatTuTrongKho vttk", VatTuTrongKho.class).getResultList();
            List<NhanVien> listNhanVien = em.createQuery("SELECT nv FROM NhanVien nv join nv.loaiNhanVien lnv where lnv.tenLoaiNhanVien= :ten", NhanVien.class).setParameter("ten","Nhân viên quản lý").getResultList();
            Random random = new Random();
            for (VatTuTrongKho vttk: listVatTuTrongKho) {
                LichSuVatTuTrongKho lichSuVatTuTrongKho = new LichSuVatTuTrongKho();
                lichSuVatTuTrongKho.setVatTuTrongKho(vttk);
                lichSuVatTuTrongKho.setNhanVien(listNhanVien.get(random.nextInt(listNhanVien.size())));
                lichSuVatTuTrongKho.setSoLuongThayDoi(faker.number().numberBetween(1, 10));

                LocalDateTime minDate = LocalDateTime.now().minusYears(1);
                LocalDateTime maxDate = LocalDateTime.now().plusYears(1);
                long days = ChronoUnit.DAYS.between(minDate, maxDate)+ChronoUnit.HOURS.between(minDate, maxDate);
                long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
                lichSuVatTuTrongKho.setNgayThayDoi(minDate.plusDays(randomDays));

                em.getTransaction().begin();
                em.persist(lichSuVatTuTrongKho);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public void addChiTietDichVuThanhToanNgoai(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            // cac dich vu thanh toan ngoai don dat phong
            Random random = new Random();
            List<DichVu> listDichVu = em.createQuery("SELECT dv FROM DichVu dv where dv.trangThai=true", DichVu.class).getResultList();
            ChiTietDichVu chiTietDichVu = new ChiTietDichVu();
            chiTietDichVu.setDichVu(listDichVu.get(random.nextInt(listDichVu.size())));
            chiTietDichVu.setNgaySuDung(LocalDateTime.now());
            chiTietDichVu.setSoLuong(faker.number().numberBetween(1, 5));
            chiTietDichVu.setTrangThai(true);
            em.getTransaction().begin();
            em.persist(chiTietDichVu);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    public ChiTietDichVu createChiTietDichVuThanhToan(LocalDateTime ngaySuDung){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            // cac dich vu thanh toan ngoai don dat phong
            Random random = new Random();
            List<DichVu> listDichVu = em.createQuery("SELECT dv FROM DichVu dv where dv.trangThai=true", DichVu.class).getResultList();
            ChiTietDichVu chiTietDichVu = new ChiTietDichVu();
            chiTietDichVu.setDichVu(listDichVu.get(random.nextInt(listDichVu.size())));
            chiTietDichVu.setNgaySuDung(ngaySuDung);
            chiTietDichVu.setSoLuong(faker.number().numberBetween(1, 5));
            chiTietDichVu.setTrangThai(false);
            em.getTransaction().begin();
            em.persist(chiTietDichVu);
            em.getTransaction().commit();
            return chiTietDichVu;
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }
        return null;
    }

    public void addChiTietDonDatPhong(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            Random random = new Random();
            List<DonDatPhong> donDatPhong = em.createQuery("SELECT ddp FROM DonDatPhong ddp", DonDatPhong.class).getResultList();
            List<Phong> listPhong = em.createQuery("SELECT p FROM Phong p", Phong.class).getResultList();
            List<KhachHang> listKhachHang = em.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class).getResultList();
            for (DonDatPhong ddp: donDatPhong) {
                int loop =1;
                String typeOfRoom;
                if(ddp.getSoLuongNguoiLon()+ddp.getSoLuongTreEm()<=3){
                    typeOfRoom = "Standard";
                }else if(ddp.getSoLuongNguoiLon()+ddp.getSoLuongTreEm()<=6){
                    typeOfRoom = "Superior";
                }else{
                    loop = (ddp.getSoLuongNguoiLon()+ddp.getSoLuongTreEm())%9!=0? (ddp.getSoLuongNguoiLon()+ddp.getSoLuongTreEm())/9+1:(ddp.getSoLuongNguoiLon()+ddp.getSoLuongTreEm())/9;
                    typeOfRoom = "Deluxe";
                }
                for(int i=0;i<loop;i++){
                    ChiTietDonDatPhong chiTietDonDatPhong = new ChiTietDonDatPhong();
                    chiTietDonDatPhong.setDonDatPhong(ddp);
                    chiTietDonDatPhong.setMaChiTietDonDatPhong(chiTietDonDatPhong.generateMaChiTietDonDatPhong());
                    System.out.println(chiTietDonDatPhong.getMaChiTietDonDatPhong());
                    Phong p= listPhong.stream().filter(phong -> phong.getLoaiPhong().getTenLoaiPhong().equals(typeOfRoom) && phong.getTrangThaiPhong().equals(TrangThaiPhong.TRONG)).findFirst().orElse(null);
                    if(p==null){
                       break;
                    }
                    listPhong.remove(p);
                    chiTietDonDatPhong.setPhong(p);
                    chiTietDonDatPhong.setNgayNhan(ddp.getNgayNhan());
                    chiTietDonDatPhong.setNgayTra(ddp.getNgayTra());
                    if(ddp.getTrangThai().equals(TrangThaiDonDatPhong.DA_HUY) || ddp.getTrangThai().equals(TrangThaiDonDatPhong.DA_HUY_VA_HOAN_TIEN) || ddp.getTrangThai().equals(TrangThaiDonDatPhong.KHONG_DEN)) {
                        chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_HUY);
                    }else if(ddp.getTrangThai().equals(TrangThaiDonDatPhong.TAM_HOAN)){
                        chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DAT_TRUOC);
                    }else {
                        chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN);
                    }
                    ChiTietDichVu chiTietDichVu = createChiTietDichVuThanhToan(ddp.getNgayNhan().plusHours(2));
                    if(chiTietDichVu!=null){
                        Set<ChiTietDichVu> chiTietDichVuSet = new HashSet<>();
                        chiTietDonDatPhong.setChiTietDichVu(chiTietDichVuSet);
                    }
                    Set<KhachHang> khachHangSet = new HashSet<>();
                    khachHangSet.add(ddp.getKhachHang());
                    for(int j=0;j<ddp.getSoLuongNguoiLon();j++){
                        KhachHang khachHang = listKhachHang.get(random.nextInt(listKhachHang.size()));
                        khachHangSet.add(khachHang);
                    }
                    chiTietDonDatPhong.setKhachHang(khachHangSet);
                    em.getTransaction().begin();
                    em.persist(chiTietDonDatPhong);
                    em.getTransaction().commit();
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }

    }
    public void addDonBaoCao(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            Random random = new Random();
            List<NhanVien> listNhanVien = em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
            List<ChiTietDonDatPhong> listChiTietDonDatPhong = em.createQuery("SELECT ctdp FROM ChiTietDonDatPhong ctdp", ChiTietDonDatPhong.class).getResultList();
                DonBaoCao donBaoCao = new DonBaoCao();
                donBaoCao.setNhanVien(listNhanVien.get(random.nextInt(listNhanVien.size())));
                donBaoCao.setChiTietDonDatPhong(listChiTietDonDatPhong.get(random.nextInt(listChiTietDonDatPhong.size())));
                donBaoCao.setNgayLap(LocalDateTime.now());
                donBaoCao.setMoTa(faker.lorem().sentence());
                donBaoCao.setTrangThaiDonBaoCao(TrangThaiDonBaoCao.DANG_XU_LY);
                donBaoCao.setTongChiPhiUocTinh(faker.number().numberBetween(1000000, 10000000));
                em.getTransaction().begin();
                em.persist(donBaoCao);
                em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
             System.out.println(e.getMessage());
        }finally {
            em.close();
        }

    }
    public void addChiTietDonBaoCao(){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
                DonBaoCao donBaoCao = em.createQuery("SELECT dbc FROM DonBaoCao dbc", DonBaoCao.class).getResultList().get(0);
                ChiTietDonBaoCao chiTietDonBaoCao = new ChiTietDonBaoCao();
                chiTietDonBaoCao.setDonBaoCao(donBaoCao);
                VatTu vt= em.createQuery("SELECT vt FROM VatTu vt", VatTu.class).getResultList().get(0);
                chiTietDonBaoCao.setVatTu(vt);
                chiTietDonBaoCao.setSoLuong(1);
                chiTietDonBaoCao.setMucDoThietHai(MucDoThietHai.NHE);

                em.getTransaction().begin();
                em.persist(chiTietDonBaoCao);
                em.getTransaction().commit();

                addTaiLieuChungCu(chiTietDonBaoCao);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
    public void addTaiLieuChungCu(ChiTietDonBaoCao ctdbc){
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
                TaiLieuChungCu taiLieuChungCu = new TaiLieuChungCu();
                taiLieuChungCu.setChiTietDonBaoCao(ctdbc);
                taiLieuChungCu.setMaTaiLieu(taiLieuChungCu.generateMaTaiLieu());
                taiLieuChungCu.setHinhAnh(taiLieuChungCu.getMaTaiLieu()+".jpg");
                taiLieuChungCu.setVideo(taiLieuChungCu.getMaTaiLieu()+".mp4");
                em.getTransaction().begin();
                em.persist(taiLieuChungCu);
                em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    public void addHoaDon() {
        EntityManager em = EntityManagerUtil.createEntityManagerFactory().createEntityManager();
        try {
            //Bat dau giao dich
            em.getTransaction().begin();

            List<DonDatPhong> listDonDatPhong = em.createQuery("SELECT ddp FROM DonDatPhong ddp", DonDatPhong.class).getResultList();

            for (DonDatPhong ddp : listDonDatPhong) {
                List<ChiTietDonDatPhong> listChiTietDonDatPhong = em
                        .createQuery("SELECT ctdp FROM ChiTietDonDatPhong ctdp WHERE ctdp.donDatPhong.maDonDatPhong = :ma", ChiTietDonDatPhong.class)
                        .setParameter("ma", ddp.getMaDonDatPhong())
                        .getResultList();

                //Tao hoa don
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(hoaDon.generateMaHoaDon());
                hoaDon.setKhachHang(ddp.getKhachHang());
                hoaDon.setChiTietDonDatPhong(new HashSet<>(listChiTietDonDatPhong));
                hoaDon.setNgayTao(LocalDateTime.now());
                hoaDon.setTongTien(faker.number().numberBetween(1000000, 10000000));
                hoaDon.setGhiChu(faker.lorem().sentence());

                //Luu hoa don vao giao dich
                em.persist(hoaDon);
            }

            //Ket thuc giao dich va commit du lieu
            em.getTransaction().commit();

        } catch (Exception e) {
            //Neu co loi xay ra, rollback de khong luu du lieu
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Lỗi khi thêm hoá đơn: " + e.getMessage());
        } finally {
            //Dong entity manager
            em.close();
        }
    }

}
