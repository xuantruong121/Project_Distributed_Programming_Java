package iuh.fit.qlksfxapp.controller;

import com.google.common.eventbus.Subscribe;
import iuh.fit.qlksfxapp.DAO.*;
import iuh.fit.qlksfxapp.DAO.Impl.ChiTietDonDatPhongDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import iuh.fit.qlksfxapp.Entity.*;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.controller.EventBus.*;
import iuh.fit.qlksfxapp.controller.ItemController.ConfirmDialogController;
import iuh.fit.qlksfxapp.controller.ItemController.DialogAddBookingDetailController;
import iuh.fit.qlksfxapp.controller.ItemController.DialogAddDichVuController;
import iuh.fit.qlksfxapp.controller.ItemController.DialogAddKhachHangController;
import iuh.fit.qlksfxapp.util.ConfirmDialog;
import iuh.fit.qlksfxapp.util.FormatUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.rmi.RemoteException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class DetailBookingFormController {
    private BookingFormController.MemoTienChiTietDonDatPhong memoTienChiTietDonDatPhong;
    // thong tin chi tiet don dat phong
    @FXML private VBox roomItem;
    @FXML private Label typeRoomAndId,
            status,
            soLuongNguoiLon,
            ngayNhanLabel,
            ngayTraLabel,
            tienPhuThuChiTietDonDat,
            tienDichVuChiTietDonDat,
            tienPhongChiTietDonDat,
            tongTienChiTietDonDat;
    @FXML private Button nhanPhongBtn,traPhongBtn,thanhToanBtn,huyDatBtn;
    @FXML private ImageView imageRoom;

//    khachhang
    @FXML private BorderPane khachHangTrongPhong;
    @FXML private Label khachHangTitle;
    @FXML private ImageView removeButton,addButton;
    @FXML private TableView<KhachHang> guestTable;
    @FXML private TableColumn<KhachHang, String> maKhachHangColumn;
    @FXML private TableColumn<KhachHang, String> tenKhachHangColumn;
    @FXML private TableColumn<KhachHang, String> soDienThoaiColumn;
    @FXML private TableColumn<KhachHang, String> canCuocCongDanColumn;
    @FXML private TableColumn<KhachHang, String> emailColumn;
    @FXML private TableColumn<KhachHang, String> gioiTinhColumn;
    @FXML private TableColumn<KhachHang, String> ngaySinhColumn;
    @FXML private TableColumn<KhachHang, String> quocTichColumn;
    // dich vu
    @FXML private Label dichVuTitle;
    @FXML private ImageView removeDichVuButton,addDichVuButton;
    @FXML private TableView<ChiTietDichVu> serviceTable;
    @FXML private TableColumn<ChiTietDichVu, String> anhDichVuColumn;
    @FXML private TableColumn<ChiTietDichVu, String> tenDichVuColumn;
    @FXML private TableColumn<ChiTietDichVu, String> ngaySuDungColumn;
    @FXML private TableColumn<ChiTietDichVu, String> giaDichVuColumn;
    @FXML private TableColumn<ChiTietDichVu, String> soLuongColumn;
    @FXML private TableColumn<ChiTietDichVu, String> tongTienColumn;
    // phu thu
    @FXML private Label phuThuTitle;
    @FXML private TableView<PhuThu> phuThuTable;
    @FXML private TableColumn<PhuThu, String> anhPhuThuColumn;
    @FXML private TableColumn<PhuThu, String> tenPhuThuColumn;
    @FXML private TableColumn<PhuThu, String> giaPhuThuColumn;
    @FXML private TableColumn<PhuThu, String> tongTienPhuThuColumn;

    //    @FXML public void initialize(){
////        GeneralDAO generalDAO = new GeneralDAO();
////        ChiTietDonDatPhong chiTietDonDatPhong = generalDAO.findOb(ChiTietDonDatPhong.class, "200425001-001");
////        setData(chiTietDonDatPhong);
//    }
    private GeneralDAOImpl generalDAO;
    private ChiTietDonDatPhongDAOImpl chiTietDonDatPhongDAO;
    private BookingFormController mainController;
    private boolean isHuy= false;
    private boolean isDaThanhToan= false;
    public void setData(BookingFormController.MemoTienChiTietDonDatPhong memoTienChiTietDonDatPhong){
        this.memoTienChiTietDonDatPhong =memoTienChiTietDonDatPhong;
        isHuy = memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY);
        isDaThanhToan = memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN);
        EventBusManager.register(this);
        init();
        setThongTinDonDatPhong();
        setKhachHangTable();
        setDichVu();
        setPhuThu();
        setStyle();
        System.out.println(mainController==null?"null":mainController.getClass());
    }
    public void init() {
        maKhachHangColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getMaKhachHang()));
        tenKhachHangColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getTenKhachHang()));
        soDienThoaiColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getSoDienThoai()));
        canCuocCongDanColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getCanCuocCongDan()));
        emailColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getEmail()));
        gioiTinhColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().isGioiTinh() ? "Nam" : "Nữ"));
        ngaySinhColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getNgaySinh().toString()));
        quocTichColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getQuocTich()));
        //
        // Cột ảnh
        anhDichVuColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            private final StackPane container = new StackPane();

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(true);

                // Đưa imageView vào StackPane để căn giữa
                container.getChildren().add(imageView);
                container.setPrefSize(100, 100); // Tuỳ chỉnh theo kích thước cell
                container.setAlignment(Pos.CENTER); // Căn giữa trong StackPane
            }

            @Override
            protected void updateItem(String hinhAnhPath, boolean empty) {
                super.updateItem(hinhAnhPath, empty);
                if (empty || hinhAnhPath == null || hinhAnhPath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        String imagePath = "/images/service/" + hinhAnhPath;
                        InputStream inputStream = getClass().getResourceAsStream(imagePath);
                        if (inputStream != null) {
                            Image img = new Image(inputStream);
                            imageView.setImage(img);
                        } else {
                            Image img = new Image(getClass().getResourceAsStream("/images/placeholder.png"));
                            imageView.setImage(img);
                        }
                        setGraphic(container); // Sử dụng container để căn giữa
                    } catch (Exception e) {
                        e.printStackTrace();
                        setGraphic(null);
                    }
                }
            }
        });

        anhDichVuColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getDichVu().getHinhAnh()));
        // Các cột còn lại
        tenDichVuColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDichVu().getTenDichVu()));
        ngaySuDungColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(FormatUtil.formatLocalDateTime(data.getValue().getNgaySuDung())));
        giaDichVuColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(FormatUtil.formatMoney(data.getValue().getDichVu().getGiaDichVu())+" VND"));
        soLuongColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(String.valueOf(data.getValue().getSoLuong())));
        tongTienColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(FormatUtil.formatMoney( data.getValue().getDichVu().getGiaDichVu() * data.getValue().getSoLuong())+" VND"));

        // phu thu
        anhPhuThuColumn.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
        anhPhuThuColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(true);
            }
            @Override
            protected void updateItem(String hinhAnhPath, boolean empty) {
                super.updateItem(hinhAnhPath, empty);
                if (empty || hinhAnhPath == null || hinhAnhPath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        // Construct the image path
                        String imagePath = "/images/service/" + hinhAnhPath;
                        // Get the resource URL
                        InputStream inputStream = getClass().getResourceAsStream(imagePath);
                        if (inputStream != null) {
                            Image img = new Image(inputStream);
                            imageView.setImage(img);
                            setGraphic(imageView);
                        } else {
                            Image img = new Image(getClass().getResourceAsStream("/images/placeholder.png"));
                            imageView.setImage(img);
                            setGraphic(imageView);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        setGraphic(null);
                    }
                }
            }
        });
        // Các cột còn lại
        Phong p = memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getPhong();
        tongTienPhuThuColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(FormatUtil.formatMoney(data.getValue().getGiaPhuThu()*p.getLoaiPhong().getGia())+" VND"));

        tenPhuThuColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTenPhuThu()));
        giaPhuThuColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>((data.getValue().getGiaPhuThu()*100) +" %"));
        tongTienPhuThuColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(FormatUtil.formatMoney(data.getValue().getGiaPhuThu()*p.getLoaiPhong().getGia())+" VND"));
    }
    public void setKhachHangTable(){
        ChiTietDonDatPhong chiTietDonDatPhong = memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
        khachHangTitle.setText("Khách hàng của phòng " + chiTietDonDatPhong.getPhong().getMaPhong());
        guestTable.getItems().clear();
        if(chiTietDonDatPhong.getKhachHang() != null){
            for (KhachHang khachHang: chiTietDonDatPhong.getKhachHang()){
                guestTable.getItems().add(khachHang);
            }
        }
    }
    private void setThongTinDonDatPhong(){
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
            // 2. Tải ChiTietDonDatPhong với các quan hệ cần thiết
        ChiTietDonDatPhong chiTietDonDatPhong;
        try {
            chiTietDonDatPhong = em.createQuery(
                            "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                    "LEFT JOIN FETCH c.phong " +
                                    "LEFT JOIN FETCH c.khachHang " +
                                    "WHERE c.maChiTietDonDatPhong = :ma",
                            ChiTietDonDatPhong.class)
                    .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                    .getSingleResult();
            memoTienChiTietDonDatPhong.setChiTietDonDatPhong(chiTietDonDatPhong);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tải thông tin chi tiết đơn đặt phòng", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        try {
            // Construct the image path
            String imagePath = "/images/room/" + chiTietDonDatPhong.getPhong().getHinhAnh();
            // Get the resource URL
            URL imageUrl = getClass().getResource(imagePath);
            System.out.println("Image URL: " + imageUrl);
            if (imageUrl != null) {
                // Create and set the image
                Image image = new Image(imageUrl.toExternalForm());
                imageRoom.setImage(image);
            } else {
                // Handle case when image doesn't exist
                System.err.println("Image not found: " + imageUrl);
                // Optionally set a default image
                URL defaultImageUrl = getClass().getResource("/images/placeholder.png");
                if (defaultImageUrl != null) {
                    imageRoom.setImage(new Image(defaultImageUrl.toExternalForm()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other errors
        }
        typeRoomAndId.setText(chiTietDonDatPhong.getPhong().getLoaiPhong().getTenLoaiPhong() + " / " + chiTietDonDatPhong.getPhong().getMaPhong());
        status.setText(chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().s);
        soLuongNguoiLon.setText(chiTietDonDatPhong.getKhachHang().size() + "");
        ngayNhanLabel.setText("Ngày nhận: "+FormatUtil.formatLocalDateTime(chiTietDonDatPhong.getNgayNhan()));
        ngayTraLabel.setText("Ngày trả: "+FormatUtil.formatLocalDateTime(chiTietDonDatPhong.getNgayTra()));
        tienPhuThuChiTietDonDat.setText("Tiền phụ thu: "+ FormatUtil.formatMoney( memoTienChiTietDonDatPhong.getTienPhuThu())+" VND");
        tienDichVuChiTietDonDat.setText("Tiền dịch vụ: "+FormatUtil.formatMoney( memoTienChiTietDonDatPhong.getTienDichVu())+" VND");
        tienPhongChiTietDonDat.setText("Tiền phòng: "+FormatUtil.formatMoney(memoTienChiTietDonDatPhong.getTienPhong())+" VND");
        tongTienChiTietDonDat.setText("Tổng tiền: "+FormatUtil.formatMoney(memoTienChiTietDonDatPhong.getTongTien())+" VND");
        // button
        if(chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG)){
            nhanPhongBtn.setVisible(false);
            traPhongBtn.setVisible(true);
            thanhToanBtn.setVisible(true);
            huyDatBtn.setVisible(false);
        } else if ( chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_TRA_PHONG)){
            nhanPhongBtn.setVisible(false);
            traPhongBtn.setVisible(false);
            thanhToanBtn.setVisible(true);
            huyDatBtn.setVisible(false);
        } else if (chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DAT_TRUOC)){
            nhanPhongBtn.setVisible(true);
            traPhongBtn.setVisible(false);
            thanhToanBtn.setVisible(false);
            huyDatBtn.setVisible(true);
        } else {
            nhanPhongBtn.setVisible(false);
            traPhongBtn.setVisible(false);
            thanhToanBtn.setVisible(false);
            huyDatBtn.setVisible(false);
        }
    }
    private void setDichVu() {
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            ChiTietDonDatPhong managedChiTiet = em.createQuery(
                            "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                    "LEFT JOIN FETCH c.chiTietDichVu " +
                                    "LEFT JOIN FETCH c.phong " +
                                    "WHERE c.maChiTietDonDatPhong = :ma", ChiTietDonDatPhong.class)
                    .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                    .getSingleResult();

            dichVuTitle.setText("Dịch vụ của phòng " + managedChiTiet.getPhong().getMaPhong());
            serviceTable.getItems().clear();
            serviceTable.getItems().addAll(managedChiTiet.getChiTietDichVu());

            memoTienChiTietDonDatPhong.setChiTietDonDatPhong(managedChiTiet);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi tải danh sách dịch vụ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    private void setPhuThu() {
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Truy vấn với JOIN FETCH để load luôn collection phuThu và phong
            ChiTietDonDatPhong managedChiTiet = em.createQuery(
                            "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                    "LEFT JOIN FETCH c.phuThu " +
                                    "LEFT JOIN FETCH c.phong " +
                                    "WHERE c.maChiTietDonDatPhong = :ma", ChiTietDonDatPhong.class)
                    .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                    .getSingleResult();

            phuThuTitle.setText("Phụ thu của phòng " + managedChiTiet.getPhong().getMaPhong());
            phuThuTable.getItems().clear();

            if (managedChiTiet.getPhuThu() != null) {
                phuThuTable.getItems().addAll(managedChiTiet.getPhuThu());
            }

            memoTienChiTietDonDatPhong.setChiTietDonDatPhong(managedChiTiet);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    private void setStyle(){
        int index = memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getTrangThaiChiTietDonDatPhong().ordinal();
        String[] txfill={
                "txfill-datTruoc",
                "txfill-daNhanPhong",
                "txfill-daTraPhong",
                "txfill-daHuy",
                "txfill-daThanhToan"
        };
        String[] bgfill={
                "bgfill-datTruoc",
                "bgfill-daNhanPhong",
                "bgfill-daTraPhong",
                "bgfill-daHuy",
                "bgfill-daThanhToan"
        };
        String txfillClass = txfill[index];
        String bgfillClass = bgfill[index];
        typeRoomAndId.getStyleClass().removeIf(s -> s.startsWith("txfill-"));
        typeRoomAndId.getStyleClass().add(txfillClass);
        status.getStyleClass().removeIf(s -> s.startsWith("bgfill-"));
        status.getStyleClass().add(bgfillClass);
    }
    private void addKhachHang(KhachHang kh) {
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // 1. Tải ChiTietDonDatPhong với các quan hệ cần thiết
            ChiTietDonDatPhong chiTietDonDatPhong = em.createQuery(
                            "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                    "LEFT JOIN FETCH c.donDatPhong " +
                                    "LEFT JOIN FETCH c.phuThu " +
                                    "LEFT JOIN FETCH c.khachHang " +  // Thêm fetch khachHang để tránh LazyInitializationException
                                    "WHERE c.maChiTietDonDatPhong = :ma",
                            ChiTietDonDatPhong.class)
                    .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                    .getSingleResult();
            // 2. Kiểm tra khách hàng đã tồn tại chưa (sử dụng collection đã được fetch)
            boolean khachHangExists = chiTietDonDatPhong.getKhachHang().stream()
                    .anyMatch(k -> k.getMaKhachHang().equals(kh.getMaKhachHang()));

            if (khachHangExists) {
                EventBusManager.post(new ToastEvent("Khách hàng đã tồn tại trong danh sách", ToastEvent.ToastType.ERROR));
                transaction.rollback();
                return;
            }
            // 3. Tải khách hàng từ database (nếu cần)
            KhachHang managedKh = em.find(KhachHang.class, kh.getMaKhachHang());
            if (managedKh == null) {
                EventBusManager.post(new ToastEvent("Khách hàng không tồn tại trong hệ thống", ToastEvent.ToastType.ERROR));
                transaction.rollback();
                return;
            }
            // 4. Thêm khách hàng vào collection (không cần set lại toàn bộ collection)
            chiTietDonDatPhong.getKhachHang().add(managedKh);
            transaction.commit();
            EventBusManager.post(new ToastEvent("Thêm khách hàng thành công", ToastEvent.ToastType.SUCCESS));
            // 5. Cập nhật UI trước khi commit
            guestTable.getItems().add(managedKh);
            // 6. Cập nhật reference sau khi transaction thành công
            memoTienChiTietDonDatPhong.setChiTietDonDatPhong(chiTietDonDatPhong);
            setThongTinDonDatPhong();
            mainController.initTongTienCuaBookingDetail();
            mainController.initDetailBookingShort();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            EventBusManager.post(new ToastEvent("Lỗi khi thêm khách hàng: " + e.getMessage(), ToastEvent.ToastType.ERROR));
            throw new RuntimeException("Lỗi khi thêm khách hàng", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
//    private void addKhachHang(KhachHang kh){
//        ChiTietDonDatPhong chiTietDonDatPhong = chiTietDonDatPhongDAO.findChiTietWithKhachHang(memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong());
//        if(chiTietDonDatPhong.getKhachHang().contains(kh)){
//            EventBusManager.post(new ToastEvent("Khách hàng đã tồn tại trong danh sách", ToastEvent.ToastType.ERROR));
//            return;
//        }
////        generalDAO = Objects.requireNonNullElseGet(generalDAO, GeneralDAO::new);
//        KhachHang khachHangFromDatabase = generalDAO.findOb(KhachHang.class,kh.getMaKhachHang());
//        chiTietDonDatPhong.getKhachHang().add(khachHangFromDatabase);
//        boolean rs= generalDAO.updateOb(chiTietDonDatPhong);
//        if(rs) {
//            EventBusManager.post(new ToastEvent("Thêm khách hàng thành công", ToastEvent.ToastType.SUCCESS));
//            guestTable.getItems().add(kh);
//            // lấy lại chiTietdonDatPhong tư database de giu relationship voi khachHang
////            ChiTietDonDatPhong chiTietDonDatPhong1 = generalDAO.findOb(ChiTietDonDatPhong.class, chiTietDonDatPhong.getMaChiTietDonDatPhong());
////            memoTienChiTietDonDatPhong.setChiTietDonDatPhong(chiTietDonDatPhong1);
//        } else
//            EventBusManager.post(new ToastEvent("Thêm khách hàng thất bại", ToastEvent.ToastType.ERROR));
//    }
    private void calMonney() throws RemoteException {
//        chiTietDonDatPhongDAO = Objects.requireNonNullElseGet(chiTietDonDatPhongDAO, ChiTietDonDatPhongDAO::new);
        chiTietDonDatPhongDAO.closeEntityManager();
        chiTietDonDatPhongDAO= new ChiTietDonDatPhongDAOImpl();
        try {
            memoTienChiTietDonDatPhong.setTienDichVu(chiTietDonDatPhongDAO.getTongTienDichVuByMaChiTietDonDatPhong(memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong()));
            memoTienChiTietDonDatPhong.setTongTien(memoTienChiTietDonDatPhong.getTienPhong() + memoTienChiTietDonDatPhong.getTienDichVu() + memoTienChiTietDonDatPhong.getTienPhuThu());
        } catch (RemoteException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi", "Không thể tính tổng tiền: " + e.getMessage());
            throw e; // Re-throw to handle in calling methods
        }
    }
    public void setMainController(BookingFormController controller) {
        this.mainController = controller;
        generalDAO=controller.getGeneralDAO();
        chiTietDonDatPhongDAO=controller.getChiTietDonDatPhongDAO();
    }
    @FXML  private void handleClickButton(ActionEvent event) {
        if(isHuy || isDaThanhToan){
            EventBusManager.post(new ToastEvent("Đơn đặt phòng đã bị huỷ or thanh toán", ToastEvent.ToastType.ERROR));
            return;
        }
        Button source = (Button) event.getSource();
        switch (source.getId()) {
            case "nhanPhongBtn":{
                if(generalDAO == null)
                    generalDAO = new GeneralDAOImpl();
                ChiTietDonDatPhong chiTietDonDatPhong = memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG);
                chiTietDonDatPhong.setNgayNhan(LocalDateTime.now());
                if(generalDAO.updateOb(chiTietDonDatPhong)){
                    EventBusManager.post(new ToastEvent("Nhận phòng thành công", ToastEvent.ToastType.SUCCESS));
                    setData(memoTienChiTietDonDatPhong);
                    mainController.initDetailBookingShort();
                    mainController.loadThongTinDonDatPhong();
                }else
                    EventBusManager.post(new ToastEvent("Nhận phòng thất bại", ToastEvent.ToastType.ERROR));
                break;
            }
            case "traPhongBtn":{
                if(generalDAO == null)
                    generalDAO = new GeneralDAOImpl();
                ChiTietDonDatPhong chiTietDonDatPhong = memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_TRA_PHONG);
                chiTietDonDatPhong.setNgayTra(LocalDateTime.now());
                if(generalDAO.updateOb(chiTietDonDatPhong)){
                    EventBusManager.post(new ToastEvent("Trả phòng thành công", ToastEvent.ToastType.SUCCESS));
                    setData(memoTienChiTietDonDatPhong);
                    mainController.initDetailBookingShort();
                    mainController.loadThongTinDonDatPhong();
                }
                else
                    EventBusManager.post(new ToastEvent("Trả phòng thất bại", ToastEvent.ToastType.ERROR));
                break;
            }
            case "thanhToanBtn":{
                ChiTietDonDatPhong chiTietDonDatPhong = memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_THANH_TOAN);
                if(chiTietDonDatPhong.getNgayTra()== null)
                    chiTietDonDatPhong.setNgayTra(LocalDateTime.now());

//                EventBusManager.post(new ToastEvent("Thanh toán thành công", ToastEvent.ToastType.SUCCESS));
//                setData(memoTienChiTietDonDatPhong);
//                mainController.initTongTienCuaBookingDetail();
//                mainController.initDetailBookingShort();
                if(generalDAO.updateOb(chiTietDonDatPhong)){
                    EventBusManager.post(new ToastEvent("Thanh toán thành công", ToastEvent.ToastType.SUCCESS));
                    setData(memoTienChiTietDonDatPhong);
                    try {
                        mainController.initTongTienCuaBookingDetail();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        showErrorAlert("Lỗi", "Không thể tính tổng tiền: " + e.getMessage());
                    }
                    mainController.initDetailBookingShort();
                    mainController.loadThongTinDonDatPhong();
                }
                else
                    EventBusManager.post(new ToastEvent("Thanh toán thất bại", ToastEvent.ToastType.ERROR));
                break;
            }
            case "huyDatBtn":{
                ChiTietDonDatPhong chiTietDonDatPhong = memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_HUY);
                EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn huỷ đơn đặt phòng này không?",
                        () -> {
                            boolean suc= generalDAO.updateOb(chiTietDonDatPhong);
                            if(suc) {
                                setData(memoTienChiTietDonDatPhong);
                                // update tong tien
                                try {
                                    mainController.initTongTienCuaBookingDetail();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    showErrorAlert("Lỗi", "Không thể tính tổng tiền: " + e.getMessage());
                                }
                                mainController.initDetailBookingShort();
                            }
                            return suc;
                        },
                        () -> System.out.println("Đã huỷ"),
                        List.of("Huỷ đơn đặt phòng thành công", "Huỷ đơn đặt phòng thất bại")
                ));
                break;
            }
            default:
                System.out.println("Unknown button clicked");
        }
    }
    @FXML private void handleRemoveClick(MouseEvent event) {
        if(isHuy || isDaThanhToan){
            EventBusManager.post(new ToastEvent("Đơn đặt phòng đã bị huỷ  or thanh toán", ToastEvent.ToastType.ERROR));
            return;
        }
        ImageView source = (ImageView) event.getSource();
        switch (source.getId()) {
            case "removeButton":{
                    ObservableList<KhachHang> selectedItems = guestTable.getSelectionModel().getSelectedItems();
                    if (selectedItems.isEmpty()) {
                        EventBusManager.post(new ToastEvent("Vui lòng chọn khách hàng để xóa", ToastEvent.ToastType.ERROR));
                        return;
                    }
                EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn xóa khách hàng này?",
                        () -> {
                            EntityManager em = null;
                            boolean success = false;
                            try {
                                em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
                                em.getTransaction().begin();

                                // 1. Tải ChiTietDonDatPhong với các quan hệ cần thiết
                                ChiTietDonDatPhong chiTietDonDatPhong = em.createQuery(
                                                "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                                        "LEFT JOIN FETCH c.donDatPhong " +
                                                        "LEFT JOIN FETCH c.phuThu " +
                                                        "LEFT JOIN FETCH c.khachHang " + // Thêm fetch khachHang để tránh LazyInitialization
                                                        "WHERE c.maChiTietDonDatPhong = :ma",
                                                ChiTietDonDatPhong.class)
                                        .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                                        .getSingleResult();

                                // 2. Xử lý xóa các khách hàng được chọn
                                List<KhachHang> itemsToRemove = new ArrayList<>(selectedItems);
                                for (KhachHang item : itemsToRemove) {
                                    KhachHang managedItem = em.find(KhachHang.class, item.getMaKhachHang());
                                    if (managedItem != null) {
                                        chiTietDonDatPhong.getKhachHang().remove(managedItem);
                                    }
                                }

                                em.getTransaction().commit();
                                success = true;

                                // 3. Cập nhật UI sau khi commit thành công
                                memoTienChiTietDonDatPhong.setChiTietDonDatPhong(chiTietDonDatPhong);
                                setKhachHangTable();
                                calMonney();
                                mainController.initTongTienCuaBookingDetail();
                                mainController.initDetailBookingShort();
                                return true;
                            } catch (Exception e) {
                                if (em != null && em.getTransaction().isActive()) {
                                    em.getTransaction().rollback();
                                }
                                EventBusManager.post(new ToastEvent("Lỗi khi xóa khách hàng: " + e.getMessage(), ToastEvent.ToastType.ERROR));
                                return false;
                            } finally {
                                if (em != null && em.isOpen()) {
                                    em.close();
                                }
                            }
                        },
                        () -> {
                            // Hành động khi hủy bỏ
                        },
                        List.of("Xóa khách hàng thành công", "Xóa khách hàng thất bại")
                ));
            }
                break;
            case "removeDichVuButton":{
                ObservableList<ChiTietDichVu> selectedItems = serviceTable.getSelectionModel().getSelectedItems();
                if (selectedItems.isEmpty()) {
                    EventBusManager.post(new ToastEvent("Vui lòng chọn dịch vụ để xóa", ToastEvent.ToastType.ERROR));
                    return;
                }
                // Copy để tránh lỗi ConcurrentModificationException
                EventBusManager.post( new ConfirmDialogEvent(
                        "Bạn có chắc muốn xóa dịch vụ này?",
                        () -> {

                            EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
                            em.getTransaction().begin();
                            try {
                                // Fetch ChiTietDonDatPhong với các quan hệ cần thiết
                                ChiTietDonDatPhong chiTietDonDatPhong = em.createQuery(
                                                "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                                        "LEFT JOIN FETCH c.phuThu " +
                                                        "LEFT JOIN FETCH c.donDatPhong " +
                                                        "WHERE c.maChiTietDonDatPhong = :ma",
                                                ChiTietDonDatPhong.class)
                                        .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                                        .getSingleResult();

                                // Không cần fetch riêng chiTietDichVus vì sẽ làm việc với collection hiện có

                                // Fetch khachHang
                                String query = "SELECT k FROM ChiTietDonDatPhong c " +
                                        "JOIN c.khachHang k " +
                                        "WHERE c.maChiTietDonDatPhong = :ma";
                                Set<KhachHang> khachHangs = new HashSet<>(em.createQuery(query, KhachHang.class)
                                        .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                                        .getResultList());

                                // Cập nhật khách hàng (ManyToMany có thể set trực tiếp)
                                chiTietDonDatPhong.setKhachHang(khachHangs);

                                // Xóa các items được chọn
                                List<ChiTietDichVu> itemsToRemove = new ArrayList<>(selectedItems);
                                for (ChiTietDichVu item : itemsToRemove) {
                                    ChiTietDichVu managedItem = em.find(ChiTietDichVu.class, item.getMaChiTietDichVu());
                                    if (managedItem != null) {
                                        chiTietDonDatPhong.getChiTietDichVu().remove(managedItem);
                                    }
                                }
                                em.getTransaction().commit();
                                memoTienChiTietDonDatPhong.setChiTietDonDatPhong(chiTietDonDatPhong);
                                setDichVu();
                                try {
                                    calMonney();
                                } catch (RemoteException e) {
                                    // Already handled in calMonney method
                                }
                                setThongTinDonDatPhong();
                                try {
                                    mainController.initTongTienCuaBookingDetail();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    showErrorAlert("Lỗi", "Không thể tính tổng tiền: " + e.getMessage());
                                }
                                mainController.initDetailBookingShort();
                                return true;
                            } catch (Exception e) {
                                if (em.getTransaction().isActive()) {
                                    em.getTransaction().rollback();
                                    return false;
                                }
                                throw e;
                            } finally {
                                em.close();
                            }
                        },
                        () ->{},
                        List.of("Xóa dịch vụ thành công", "Xóa dịch vụ thất bại")
                ));
            }
                break;
            default:
                System.out.println("Unknown button clicked");
        }
    }
    @FXML private void handleAddClick(MouseEvent event) throws IOException {
        if(isHuy || isDaThanhToan){
            EventBusManager.post(new ToastEvent("Đơn đặt phòng đã bị huỷ  or thanh toán", ToastEvent.ToastType.ERROR));
            return;
        }
        ImageView source = (ImageView) event.getSource();
        String path= "";
        switch (source.getId()) {
            // khach hang
            case "addButton":{
                path = "/fxml/util/DialogAddKhachHang.fxml";
                break;
            }
            case "addDichVuButton":{
                path = "/fxml/util/DialogAddDichVu.fxml";
                break;
            }
            default:
                System.out.println("Unknown button clicked");
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            // Căn giữa Dialog trên màn hình cha
            if (khachHangTrongPhong != null && khachHangTrongPhong.getScene() != null) {
                Stage parentStage = (Stage) khachHangTrongPhong.getScene().getWindow();
                stage.setOnShown(e -> {
                    double centerX = parentStage.getX() + (parentStage.getWidth() - stage.getWidth()) / 2;
                    double centerY = parentStage.getY() + (parentStage.getHeight() - stage.getHeight()) / 2;
                    stage.setX(centerX);
                    stage.setY(centerY);
                });
            }
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Subscribe
    public void DialogAddKhachHangEvent(DialogAddKhachHangEvent event){
        Platform.runLater(() -> {
            if(event.getKhachHang() != null){
                addKhachHang(event.getKhachHang());
            }else{
                EventBusManager.post(new ToastEvent("Không có khách hàng nào được chọn", ToastEvent.ToastType.ERROR));
            }
        });
    }
    @Subscribe
    public void DialogAddDichVuEvent(DialogAddDichVuEvent event){
        Platform.runLater(() -> {
            if(event.getSelectedDichVu() != null) {
                EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                EntityTransaction transaction = em.getTransaction();

                try {
                    transaction.begin();

                    // 1. Kiểm tra dịch vụ được chọn
                    if (event.getSelectedDichVu() == null) {
                        EventBusManager.post(new ToastEvent("Không có dịch vụ nào được chọn", ToastEvent.ToastType.ERROR));
                        transaction.rollback();
                        return;
                    }

                    // 2. Tải ChiTietDonDatPhong với các quan hệ cần thiết
                    ChiTietDonDatPhong chiTietDonDatPhong = em.createQuery(
                                    "SELECT DISTINCT c FROM ChiTietDonDatPhong c " +
                                            "LEFT JOIN FETCH c.donDatPhong " +
                                            "LEFT JOIN FETCH c.phuThu " +
                                            "LEFT JOIN FETCH c.chiTietDichVu " + // Thêm fetch chiTietDichVu để tránh LazyInitializationException
                                            "WHERE c.maChiTietDonDatPhong = :ma",
                                    ChiTietDonDatPhong.class)
                            .setParameter("ma", memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getMaChiTietDonDatPhong())
                            .getSingleResult();

                    // 3. Kiểm tra dịch vụ đã tồn tại chưa
                    DichVu selectedDichVu = event.getSelectedDichVu();
                    boolean dichVuExists = chiTietDonDatPhong.getChiTietDichVu().stream()
                            .anyMatch(dv -> dv.getDichVu().getMaDichVu().equals(selectedDichVu.getMaDichVu()));

                    if (dichVuExists) {
                        EventBusManager.post(new ToastEvent("Dịch vụ đã tồn tại trong đơn đặt phòng", ToastEvent.ToastType.ERROR));
                        transaction.rollback();
                        return;
                    }
                    // 4. Tạo mới chi tiết dịch vụ
                    ChiTietDichVu chiTietDichVu = new ChiTietDichVu();
                    chiTietDichVu.setDichVu(em.find(DichVu.class, selectedDichVu.getMaDichVu()));
                    chiTietDichVu.setSoLuong(event.getSoLuong());
                    chiTietDichVu.setChiTietDonDatPhong(chiTietDonDatPhong);
                    chiTietDichVu.setNgaySuDung(LocalDateTime.now());
                    chiTietDichVu.setTrangThai(false);

                    // 5. Tạo mã duy nhất cho chi tiết dịch vụ
                    String newMaChiTietDichVu = chiTietDichVu.generateMaChiTietDichVu();
                    chiTietDichVu.setMaChiTietDichVu(newMaChiTietDichVu);

                    // 6. Thêm vào database và collection
                    em.persist(chiTietDichVu);
                    chiTietDonDatPhong.getChiTietDichVu().add(chiTietDichVu);
                    transaction.commit();

                    // 8. Cập nhật thông tin sau khi commit thành công
                    EventBusManager.post(new ToastEvent("Thêm dịch vụ thành công", ToastEvent.ToastType.SUCCESS));
                    // 7. Cập nhật UI trước khi commit
                    memoTienChiTietDonDatPhong.setChiTietDonDatPhong(chiTietDonDatPhong);
                    setDichVu();
                    try {
                        calMonney();
                    } catch (RemoteException e) {
                        // Already handled in calMonney method
                    }
                    setThongTinDonDatPhong();
                    try {
                        mainController.initTongTienCuaBookingDetail();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        showErrorAlert("Lỗi", "Không thể tính tổng tiền: " + e.getMessage());
                    }
                    mainController.initDetailBookingShort();

                } catch (Exception e) {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                    EventBusManager.post(new ToastEvent("Lỗi khi thêm dịch vụ: " + e.getMessage(), ToastEvent.ToastType.ERROR));
                    throw new RuntimeException("Lỗi khi thêm dịch vụ", e);
                } finally {
                    if (em != null && em.isOpen()) {
                        em.close();
                    }
                }
            } else {
                EventBusManager.post(new ToastEvent("Không có dịch vụ nào được chọn", ToastEvent.ToastType.ERROR));
            }
        });
    }
    public void cleanup() {
        EventBusManager.unregister(this);
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
