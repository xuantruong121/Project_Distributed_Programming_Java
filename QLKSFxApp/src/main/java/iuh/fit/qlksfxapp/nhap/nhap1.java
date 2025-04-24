package iuh.fit.qlksfxapp.nhap;

//import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.DAO.Impl.EntityManagerUtilImpl;
import iuh.fit.qlksfxapp.Entity.*;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Main;
import iuh.fit.qlksfxapp.controller.MainController;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.datafaker.Faker;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class nhap1 extends Application {
    private final Locale vietnam = new Locale("vi", "VN");
    private final Faker faker = new Faker(vietnam);
    private   EntityManager em = EntityManagerUtilImpl.createEntityManagerFactory().createEntityManager();
     private List<Phong> listPhong;
    private List<KhachHang> listKhachHang;
    private  List<NhanVien> listNhanVien;
    @Override
    public void start(Stage primaryStage) {
        listPhong=  em.createQuery("SELECT p FROM Phong p", Phong.class).getResultList();
        listKhachHang = em.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class).getResultList();
        listNhanVien = em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();

//        addDonDatPhong();
//        GeneralDAO generalDAO = new GeneralDAO();
//        DonDatPhong donDatPhong = generalDAO.findOb(DonDatPhong.class, "200425001");
//        addChiTietDonDatPhong(donDatPhong);
//        ChiTietDonDatPhong chiTietDonDatPhong = generalDAO.findOb(ChiTietDonDatPhong.class, "200425001-001");
//        addChiTietDichVu(chiTietDonDatPhong);
//        addChiTietDichVu(chiTietDonDatPhong);
//        DonDatPhong donDatPhong1 = generalDAO.findOb(DonDatPhong.class, "200425002");
//        addChiTietDonDatPhong(donDatPhong1);

//        addChiTietDichVu(donDatPhong);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

            // Tạo scene mới
            Scene scene = new Scene(root);
            // Lấy stage hiện tại
            // Lấy reference đến MainController trước khi thay đổi scene
            MainController mainController = loader.getController();

            // Thiết lập sự kiện đóng cửa sổ
            primaryStage.setOnCloseRequest(e -> {
                if (mainController != null) {
                    mainController.shutdown();
                } else {
                    Platform.exit(); // Fallback nếu controller không tồn tại
                }
            });
            // Đặt scene mới cho stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Phần mềm Quản lý Khách sạn");
            // Cho phép thay đổi kích thước và đặt maximized
            primaryStage.setResizable(true);
            primaryStage.setMaximized(true);

            // Hiển thị stage
            primaryStage.show();
            // Ghi log
            System.out.println("Giao diện chính đã được tải và hiển thị ở chế độ maximized");

        } catch (Exception e) {
            e.printStackTrace(); // ← XEM lỗi thật sự nằm ở đâu
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void addDonDatPhong(){
        try {
            Random random = new Random();
           List<TrangThaiDonDatPhong> listTrangThaiDonDatPhong = Arrays.asList(TrangThaiDonDatPhong.DA_XAC_NHAN, TrangThaiDonDatPhong.DA_HUY, TrangThaiDonDatPhong.DA_HUY_VA_HOAN_TIEN, TrangThaiDonDatPhong.TAM_HOAN, TrangThaiDonDatPhong.KHONG_DEN);
            // dondatPhong
            DonDatPhong donDatPhong = new DonDatPhong();
            donDatPhong.setMaDonDatPhong(donDatPhong.generateMaDonDatPhong());
            donDatPhong.setNhanVien(listNhanVien.get(random.nextInt(listNhanVien.size())));
            donDatPhong.setKhachHang(listKhachHang.get(random.nextInt(listKhachHang.size())));
            donDatPhong.setNgayDat(LocalDateTime.now());// now
            donDatPhong.setNgayNhan(LocalDateTime.now()); // now
            donDatPhong.setNgayTra(donDatPhong.getNgayNhan().plusYears(1)); // now +1 year
            donDatPhong.setSoLuongNguoiLon(5);
            donDatPhong.setSoLuongTreEm(2);
            donDatPhong.setTienDatCoc(0);
            donDatPhong.setTenDoan(faker.name().fullName());
            donDatPhong.setTrangThai((listTrangThaiDonDatPhong.getFirst()));
            donDatPhong.setGhiChu(faker.lorem().sentence());
            System.out.println(donDatPhong.getMaDonDatPhong() + " " + donDatPhong.getKhachHang().getMaKhachHang() + " ");
            addChiTietDonDatPhong(donDatPhong);
            DonDatPhong donDatPhong1 = new DonDatPhong();
            int ma = Integer.parseInt(donDatPhong.generateMaDonDatPhong());
            ma+=1;
            donDatPhong1.setMaDonDatPhong(String.valueOf(ma));
            donDatPhong1.setNhanVien(listNhanVien.get(random.nextInt(listNhanVien.size())));
            donDatPhong1.setKhachHang(listKhachHang.get(random.nextInt(listKhachHang.size())));
            donDatPhong1.setNgayDat(LocalDateTime.now());// now
            donDatPhong1.setNgayNhan(LocalDateTime.now().plusDays(1)); // now `+1 day
            donDatPhong1.setNgayTra(donDatPhong.getNgayNhan().plusYears(1)); // now +1 year
            donDatPhong1.setSoLuongNguoiLon(1);
            donDatPhong1.setSoLuongTreEm(1);
            donDatPhong1.setTienDatCoc(0);
            donDatPhong1.setTenDoan(faker.name().fullName());
            donDatPhong1.setTrangThai((listTrangThaiDonDatPhong.getFirst()));
            donDatPhong1.setGhiChu(faker.lorem().sentence());
            addChiTietDonDatPhong(donDatPhong1);

            em.getTransaction().begin();
            em.persist(donDatPhong);
            em.persist(donDatPhong1);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void addChiTietDonDatPhong(DonDatPhong ddp){
        try {
            Random random = new Random();
           ChiTietDonDatPhong chiTietDonDatPhong = new ChiTietDonDatPhong();
            chiTietDonDatPhong.setDonDatPhong(ddp);
            chiTietDonDatPhong.setMaChiTietDonDatPhong(chiTietDonDatPhong.generateMaChiTietDonDatPhong());
            int ran =1;
            for(int j=0;j< ((ddp.getSoLuongNguoiLon() >= 5)?2:1); j++){
               ran= random.nextInt(listPhong.size());
                Phong phong = listPhong.get(ran);
                listPhong.remove(ran);
                chiTietDonDatPhong.setPhong(phong);
            }
//            int ran = random.nextInt(listPhong.size());
//            chiTietDonDatPhong.setPhong(listPhong.get(ran));
//            listPhong.remove(ran);

            if(ddp.getNgayNhan().isBefore(LocalDateTime.now())){
                chiTietDonDatPhong.setNgayNhan(ddp.getNgayNhan());
                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG);
            }else {
                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DAT_TRUOC);
            }
            Set<KhachHang> khachHangSet = new HashSet<>();
            khachHangSet.add(ddp.getKhachHang());
            for(int j=0;j< ((ddp.getSoLuongNguoiLon() >= 5)?2:1); j++){
                ran=random.nextInt(listKhachHang.size());
                KhachHang khachHang = listKhachHang.get(ran);
                listKhachHang.remove(ran);
                khachHangSet.add(khachHang);
            }
            chiTietDonDatPhong.setKhachHang(khachHangSet);
            System.out.println(chiTietDonDatPhong.getMaChiTietDonDatPhong() + " " + chiTietDonDatPhong.getKhachHang().size() + " " + chiTietDonDatPhong.getPhong().getMaPhong());
            em.getTransaction().begin();
            em.persist(chiTietDonDatPhong);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }
    public void addChiTietDichVu(ChiTietDonDatPhong ct){
        Random random = new Random();
        List<DichVu> listDichVu = em.createQuery("SELECT dv FROM DichVu dv where dv.trangThai=true", DichVu.class).getResultList();
        ChiTietDichVu chiTietDichVu = new ChiTietDichVu();
        chiTietDichVu.setDichVu(listDichVu.get(random.nextInt(listDichVu.size())));
        chiTietDichVu.setNgaySuDung(LocalDateTime.now());
        chiTietDichVu.setSoLuong(faker.number().numberBetween(1, 5));
        chiTietDichVu.setTrangThai(false);
        chiTietDichVu.setChiTietDonDatPhong(ct);
        em.getTransaction().begin();
        em.persist(chiTietDichVu);
        em.getTransaction().commit();
    }
}
