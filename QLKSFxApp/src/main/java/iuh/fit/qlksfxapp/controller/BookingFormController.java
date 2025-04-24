package iuh.fit.qlksfxapp.controller;

import com.google.common.eventbus.Subscribe;
import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.DonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.*;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.controller.EventBus.*;
import iuh.fit.qlksfxapp.controller.ItemController.DetailBookingShortController;
import iuh.fit.qlksfxapp.controller.ItemController.DialogAddBookingDetailController;
import iuh.fit.qlksfxapp.util.ConfirmDialog;
import iuh.fit.qlksfxapp.util.FormatUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class BookingFormController  implements MainController.DataReceivable {
    // thong tin khach hang
    @FXML
    private Label customerName, customerId, customerEmail, customerPhone, birthDate, gender, nationality, idCardNumber;
    @FXML
    private ImageView profileImage;
    // thong tin don dat phong
    @FXML
    private Label maDonDatPhong, ngayTaoDonLabel, trangThaiDonDatPhong;
    @FXML
    private TextField customerNameField, groupNameField, adultCountField, childCountField, depositField;
    @FXML
    private DatePicker checkinDatePicker, checkoutDatePicker, bookingDatePicker;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextArea notesTextArea;
    // chiTiet don dat phong
    @FXML
    private Label tienDichVuLabel, tienPhuThuLabel, tongTienLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private VBox roomItemsContainer;
    // detail booking
    @FXML
    private StackPane detailBookingForm;
    @FXML
    private AnchorPane bookingForm;
    @FXML private VBox thongTinKhachDatVBox,chiTietDonDatPhongVbox,thongTinKhachDatEmpty;
    @FXML private Label chiTietDonDatPhongEmpty;
    @FXML // don dat phong
    private Button xacNhanButton,editButton,cancelButton,themDonDatButton;
    private DonDatPhong donDatPhong;
    private List<ChiTietDonDatPhong> chiTietDonDatPhongList;
    private ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    private MemoTienChiTietDonDatPhong firstMemoTienChiTietDonDatPhong;
    private GeneralDAO generalDAO;
    private DonDatPhongDAO donDatPhongDAO;
    private AnchorPane selectedDetailBookingShort;
    private boolean isEditMode = false;
    private DetailBookingFormController detailBookingFormController;
    private KhachHang khachHangCuaEmpty;

    @FXML
    public void initialize() {
        EventBusManager.register(this);
        chiTietDonDatPhongList = new ArrayList<>();
        chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
        generalDAO = new GeneralDAO();
        donDatPhongDAO=new DonDatPhongDAO();
//        if (donDatPhong == null) {
//            GeneralDAO generalDAO = new GeneralDAO();
//            donDatPhong = generalDAO.findOb(DonDatPhong.class, "200425001");
//        }
//        initComboBox();
//        loadThongTinDonDatPhong();
//        loadThongTinKhachDat();
//        initDetailBookingShort();
//        initDetailBooking();
//        initButton();
        EventBusManager.register(this);
    }
    private void showEmptyView(boolean isEmpty){
        thongTinKhachDatVBox.setVisible(!isEmpty);
        thongTinKhachDatEmpty.setVisible(isEmpty);
        chiTietDonDatPhongVbox.setVisible(!isEmpty);
        chiTietDonDatPhongEmpty.setVisible(isEmpty);
        setDisable(false);
        cleanThongTinDonDatPhong();
        initButton();
        bookingDatePicker.setValue(LocalDate.now());
        bookingDatePicker.setDisable(true);
    }
    @Override
    public void setData(Object data) {
        if (data instanceof Map<?, ?> map) {
            Map<Phong, DonDatPhong> result = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key instanceof Phong && value instanceof DonDatPhong) {
                    result.put((Phong) key, (DonDatPhong) value);
                    donDatPhong = (DonDatPhong) value;
                } else {
                    EventBusManager.post(new ToastEvent("Dữ liệu không hợp lệ", ToastEvent.ToastType.ERROR));
                    return;
                }
            }
            if (donDatPhong.getMaDonDatPhong()!=null) {
                initComboBox();
                loadThongTinKhachDat();
                initDetailBookingShort();
                initDetailBooking();
                loadThongTinDonDatPhong();
            }else{
                showEmptyView(true);
                EventBusManager.post(new ToastEvent("Không có dữ liệu nào được truyền đến", ToastEvent.ToastType.ERROR));
            }
        } else {
            showEmptyView(true);
            EventBusManager.post(new ToastEvent("Không có dữ liệu nào được truyền đến", ToastEvent.ToastType.ERROR));
        }
    }
    // button cua booking form
    private void initButton(){
        if(donDatPhong==null || donDatPhong.getMaDonDatPhong()==null){
            themDonDatButton.setVisible(true);
            xacNhanButton.setVisible(false);
            editButton.setVisible(false);
            cancelButton.setVisible(false);
            return;
        }
        // neu co phong da nhan hoac tra thi khong cho chinh sua, ko huy
        if(chiTietDonDatPhongList == null|| chiTietDonDatPhongList.isEmpty()){
            themDonDatButton.setVisible(false);
            xacNhanButton.setVisible(false);
            editButton.setVisible(true);
            cancelButton.setVisible(true);
        }else{
            boolean flag= false;
            for(ChiTietDonDatPhong ct: chiTietDonDatPhongList ){
                if(ct.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG) ||
                        ct.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_TRA_PHONG)){
                    themDonDatButton.setVisible(false);
                    xacNhanButton.setVisible(false);
                    editButton.setVisible(false);
                    cancelButton.setVisible(false);
                    flag=true;
                    break;
                }
            }
            if(flag){
                themDonDatButton.setVisible(false);
            }
        }
    }
    private void initComboBox() {
        List<String> l = new ArrayList<>();
        l.add(TrangThaiDonDatPhong.DA_XAC_NHAN.s);
        l.add(TrangThaiDonDatPhong.DA_HUY.s);
        l.add(TrangThaiDonDatPhong.DA_HUY_VA_HOAN_TIEN.s);
        l.add(TrangThaiDonDatPhong.KHONG_DEN.s);
        l.add(TrangThaiDonDatPhong.TAM_HOAN.s);
        statusComboBox.getItems().addAll(l);
        statusComboBox.setValue(donDatPhong.getTrangThai().s);
    }
    public void loadThongTinDonDatPhong() {
        maDonDatPhong.setText(donDatPhong.getMaDonDatPhong());
        ngayTaoDonLabel.setText(FormatUtil.formatLocalDateTime(donDatPhong.getNgayDat()));
        trangThaiDonDatPhong.setText(donDatPhong.getTrangThai().s);
        KhachHang khachHang = donDatPhong.getKhachHang();
        if (khachHang != null) {
            customerNameField.setText(khachHang.getTenKhachHang());
        }
        groupNameField.setText(donDatPhong.getTenDoan());
        adultCountField.setText(String.valueOf(donDatPhong.getSoLuongNguoiLon()));
        childCountField.setText(String.valueOf(donDatPhong.getSoLuongTreEm()));
        depositField.setText(String.valueOf(donDatPhong.getTienDatCoc()));
        checkinDatePicker.setValue(donDatPhong.getNgayNhan().toLocalDate());
        checkoutDatePicker.setValue(donDatPhong.getNgayTra().toLocalDate());
        bookingDatePicker.setValue(donDatPhong.getNgayDat().toLocalDate());
        notesTextArea.setText(donDatPhong.getGhiChu());
        initButton();
    }
    private void loadThongTinKhachDat() {
        KhachHang khachHang;
        if(donDatPhong == null || donDatPhong.getKhachHang() == null) {
            khachHang = khachHangCuaEmpty;
        }else{
            khachHang = donDatPhong.getKhachHang();
        }
        customerName.setText(khachHang.getTenKhachHang());
        customerId.setText(khachHang.getMaKhachHang());
        customerEmail.setText(khachHang.getEmail());
        customerPhone.setText(khachHang.getSoDienThoai());
        birthDate.setText(khachHang.getNgaySinh().toString());
        gender.setText(khachHang.isGioiTinh() ? "Nam" : "Nữ");
        nationality.setText(khachHang.getQuocTich());
        idCardNumber.setText(khachHang.getCanCuocCongDan());
    }
    public void initDetailBookingShort() {
        roomItemsContainer.getChildren().clear();
        chiTietDonDatPhongDAO.closeEntityManager();
        chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
        chiTietDonDatPhongList = chiTietDonDatPhongDAO.findChiTietDonDatPhongTheoMaDonDatPhong(donDatPhong.getMaDonDatPhong());
        for (ChiTietDonDatPhong c : chiTietDonDatPhongList) {
            if(c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_HUY))
                continue;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/DetailBookingShort.fxml"));
                AnchorPane bookingItem = loader.load();
                // Lấy controller để truyền dữ liệu
                MemoTienChiTietDonDatPhong dt = new MemoTienChiTietDonDatPhong();
                dt.setChiTietDonDatPhong(c);
                double tienDichVuValue = chiTietDonDatPhongDAO.getTongTienDichVuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
                double tienPhongValue = chiTietDonDatPhongDAO.getTienPhongTheoMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
                double tongTienPhuThuValue = chiTietDonDatPhongDAO.getTongTienPhuThuByMaChiTietDonDatPhong(c.getMaChiTietDonDatPhong());
                double tongTienValue = tienPhongValue + tienDichVuValue + tongTienPhuThuValue;
                dt.setTienPhong(tienPhongValue);
                dt.setTienDichVu(tienDichVuValue);
                dt.setTienPhuThu(tongTienPhuThuValue);
                dt.setTongTien(tongTienValue);
                if (firstMemoTienChiTietDonDatPhong == null)
                    firstMemoTienChiTietDonDatPhong = dt;
                DetailBookingShortController controller = loader.getController();
                controller.setData(dt, this::handleDetailBookingShortClick);

                bookingItem.setUserData(dt);
                // Thêm vào VBox
                roomItemsContainer.getChildren().add(bookingItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initTongTienCuaBookingDetail();
    }
    public void initTongTienCuaBookingDetail(){
        if(chiTietDonDatPhongList== null || chiTietDonDatPhongList.isEmpty()){
            tienDichVuLabel.setText("Tiền dịch vụ: 0 VND");
            tienPhuThuLabel.setText("Tiền phụ thu: 0 VND");
            tongTienLabel.setText("Tổng tiền: 0 VND");
            return;
        }
        chiTietDonDatPhongDAO.closeEntityManager();
        chiTietDonDatPhongDAO= new ChiTietDonDatPhongDAO();
        double tienPhongValue = chiTietDonDatPhongDAO.getTongTienPhongByMaDonDatPhong(donDatPhong.getMaDonDatPhong());
        double tienDichVuValue = chiTietDonDatPhongDAO.getTongTienDichVuByMaDonDatPhong(donDatPhong.getMaDonDatPhong());
        double tongTienPhuThuValue = chiTietDonDatPhongDAO.getTongTienPhuThuByMaDonDatPhong(donDatPhong.getMaDonDatPhong());
        tienDichVuLabel.setText("Tiền dịch vụ: " + FormatUtil.formatMoney(tienDichVuValue) + " VND");
        tienPhuThuLabel.setText("Tiền phụ thu: " + FormatUtil.formatMoney(tongTienPhuThuValue) + " VND");
        tongTienLabel.setText("Tổng tiền: " + FormatUtil.formatMoney(tienPhongValue+tienDichVuValue+tongTienPhuThuValue) + " VND");
    }
    private void initDetailBooking() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetailBookingform.fxml"));
            Node bookingForm = loader.load();
            detailBookingFormController = loader.getController();
            detailBookingFormController.setMainController(this);
            detailBookingFormController.setData(firstMemoTienChiTietDonDatPhong);
            detailBookingForm.getChildren().setAll(bookingForm);
            AnchorPane.setBottomAnchor(bookingForm, 0.0);
            AnchorPane.setTopAnchor(bookingForm, 0.0);
            AnchorPane.setLeftAnchor(bookingForm, 0.0);
            AnchorPane.setRightAnchor(bookingForm, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleDetailBookingShortClick(AnchorPane anchorPane) {
        for (Node node : roomItemsContainer.getChildren()) {
            if (node instanceof AnchorPane) {
                node.getStyleClass().remove("selected");
            }
        }
        anchorPane.getStyleClass().add("selected");
        selectedDetailBookingShort = anchorPane;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetailBookingForm.fxml"));
            Node bookingForm = loader.load();
            DetailBookingFormController controller = loader.getController();
            Object data = anchorPane.getUserData();
            if (data instanceof MemoTienChiTietDonDatPhong) {
                firstMemoTienChiTietDonDatPhong = (MemoTienChiTietDonDatPhong) data;
                controller.setMainController(this);
                controller.setData((MemoTienChiTietDonDatPhong) data);
                detailBookingForm.getChildren().clear();
                detailBookingForm.getChildren().setAll(bookingForm);
            } else {
                controller.setData(null);
                EventBusManager.post(new ToastEvent("Dữ liệu không hợp lệ", ToastEvent.ToastType.ERROR));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   @FXML
    private void handleHuyDatDonDatPhong() {
        EventBusManager.post(new ConfirmDialogEvent(
                "Bạn có chắc chắn muốn hủy đơn đặt phòng này không?",
                () -> {
                    donDatPhong.setTrangThai(TrangThaiDonDatPhong.DA_HUY);
                    generalDAO= Objects.requireNonNullElse(generalDAO, new GeneralDAO());
                    boolean r = generalDAO.updateOb(donDatPhong);
                    if (r) {
                        EventBusManager.post(new ToastEvent("Hủy đơn đặt phòng thành công", ToastEvent.ToastType.SUCCESS));
                        loadThongTinDonDatPhong();
                    } else {
                        EventBusManager.post(new ToastEvent("Hủy đơn đặt phòng thất bại", ToastEvent.ToastType.ERROR));
                    }
                    return r;
                },
                () -> System.out.println("Đã hủy"),
                List.of("Hủy thành công", "Hủy thất bại")
        ));
    }
    @FXML
    private void handleChinhSuaDonDatPhong(ActionEvent event) {
        Button button = (Button) event.getSource();
        isEditMode= !isEditMode;
        switch (button.getId()) {
            case "editButton" -> {
                if (!isEditMode) {
                    button.setText("Chỉnh sửa");
                    xacNhanButton.setVisible(false);
                    setDisable(true);
                } else {
                    button.setText("Hủy bỏ");
                    xacNhanButton.setVisible(true);
                    setDisable(false);
                }
            }
            case "xacNhanButton" -> {
                if(checkValidDonDatPhong()){
                    donDatPhong.setTenDoan(groupNameField.getText());
                    donDatPhong.setSoLuongNguoiLon(Integer.parseInt(adultCountField.getText()));
                    donDatPhong.setSoLuongTreEm(Integer.parseInt(childCountField.getText()));
                    donDatPhong.setTienDatCoc(Double.parseDouble(depositField.getText()));
                    donDatPhong.setGhiChu(notesTextArea.getText());
                    donDatPhong.setNgayNhan(checkinDatePicker.getValue().atStartOfDay());
                    donDatPhong.setNgayTra(checkinDatePicker.getValue().atStartOfDay());
                    EventBusManager.post(new ConfirmDialogEvent(
                            "Bạn có chắc chắn muốn cập nhật đơn đặt phòng này không?",
                            () -> {
                                // Xử lý cập nhật đơn đặt phòng
                                generalDAO= Objects.requireNonNullElse(generalDAO, new GeneralDAO());
                                boolean r = generalDAO.updateOb(donDatPhong);
                                if (r) {
                                    isEditMode = false;
                                    generalDAO.updateOb(donDatPhong);
                                    loadThongTinDonDatPhong();
                                }
                                return r;
                            },
                            () -> System.out.println("Đã cập nhật"),
                            List.of("Cập nhật thành công", "Cập nhật thất bại")
                    ));
                }
            }
            default -> {

            }
        }
    }
    private boolean checkValidDonDatPhong(){
        try {
            int a = Integer.parseInt(adultCountField.getText());
            if (a <= 0) {
                EventBusManager.post(new ToastEvent("Số lượng người lớn >0", ToastEvent.ToastType.ERROR));
                adultCountField.setText("1");
                return false;
            }
            int b= Integer.parseInt(childCountField.getText());
            if(b<0){
                EventBusManager.post(new ToastEvent("Số lượng trẻ em >=0", ToastEvent.ToastType.ERROR));
                childCountField.setText("0");
                return false;
            }
            double c = Double.parseDouble(depositField.getText());
            if (c < 0) {
                EventBusManager.post(new ToastEvent("Tiền đặt cọc >=0", ToastEvent.ToastType.ERROR));
                depositField.setText("0");
                return false;
            }
        }
        catch (NumberFormatException e){
            EventBusManager.post(new ToastEvent("Số lượng là kiểu số", ToastEvent.ToastType.ERROR));
            adultCountField.setText("1");
            childCountField.setText("0");
            depositField.setText("0");
            return false;
        }
        if(bookingDatePicker.getValue().isAfter(checkinDatePicker.getValue())){
            EventBusManager.post(new ToastEvent("Ngày đặt phòng không được sau ngày nhận phòng", ToastEvent.ToastType.ERROR));
            bookingDatePicker.setValue(LocalDateTime.now().toLocalDate());
            return false;
        }
        if(checkinDatePicker.getValue().isAfter(checkoutDatePicker.getValue())){
            EventBusManager.post(new ToastEvent("Ngày nhận phòng không được sau ngày trả phòng", ToastEvent.ToastType.ERROR));
            checkinDatePicker.setValue(LocalDateTime.now().toLocalDate());
            return false;
        }
        return true;
    }
    private void setDisable(boolean idDisable){
        groupNameField.setDisable(idDisable);
        adultCountField.setDisable(idDisable);
        childCountField.setDisable(idDisable);
        depositField.setDisable(idDisable);
        checkinDatePicker.setDisable(idDisable);
        checkoutDatePicker.setDisable(idDisable);
        bookingDatePicker.setDisable(idDisable);
        notesTextArea.setDisable(idDisable);
    }
    private void cleanThongTinDonDatPhong(){
        maDonDatPhong.setText("");
        ngayTaoDonLabel.setText("");
        trangThaiDonDatPhong.setText("");
        customerNameField.setText("");
        groupNameField.setText("");
        adultCountField.setText("0");
        childCountField.setText("0");
        depositField.setText("");
        checkinDatePicker.setValue(null);
        checkoutDatePicker.setValue(null);
        bookingDatePicker.setValue(null);
        notesTextArea.setText("");
    }
    @FXML private void handleDoiKhachDat(){
        if(donDatPhong.getTrangThai().equals(TrangThaiDonDatPhong.DA_HUY)){
            EventBusManager.post(new ToastEvent("Không thể đổi khách hàng cho đơn đã hủy", ToastEvent.ToastType.ERROR));
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/util/DialogAddKhachHang.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            // Căn giữa Dialog trên màn hình cha
            if (bookingForm != null && bookingForm.getScene() != null) {
                Stage parentStage = (Stage) bookingForm.getScene().getWindow();
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
            KhachHang khachHang = event.getKhachHang();
            if(donDatPhong==null|| donDatPhong.getMaDonDatPhong()==null){
                khachHangCuaEmpty= khachHang;
                customerNameField.setText(khachHang.getTenKhachHang());
                thongTinKhachDatVBox.setVisible(true);
                thongTinKhachDatEmpty.setVisible(false);
                loadThongTinKhachDat();
                return;
            }
            if(khachHang != null){
                donDatPhong.setKhachHang(khachHang);
                generalDAO= Objects.requireNonNullElse(generalDAO, new GeneralDAO());
                boolean r = generalDAO.updateOb(donDatPhong);
                if (r) {
                    thongTinKhachDatVBox.setVisible(true);
                    thongTinKhachDatEmpty.setVisible(false);
                    loadThongTinKhachDat();
                    customerNameField.setText(khachHang.getTenKhachHang());
                    EventBusManager.post(new ToastEvent("Đổi khách hàng thành công", ToastEvent.ToastType.SUCCESS));
                } else {
                    EventBusManager.post(new ToastEvent("Đổi khách hàng thất bại", ToastEvent.ToastType.ERROR));
                }

            }else{
                EventBusManager.post(new ToastEvent("Không có khách hàng nào được chọn", ToastEvent.ToastType.ERROR));
            }
        });
    }
    @FXML private void handleAddAndRemoveBookingDetail(MouseEvent mouseEvent) {
        if(donDatPhong.getTrangThai().equals(TrangThaiDonDatPhong.DA_HUY)){
            EventBusManager.post(new ToastEvent("Không thể thêm phòng vào đơn đã hủy", ToastEvent.ToastType.ERROR));
            return;
        }
        ImageView imageView = (ImageView) mouseEvent.getSource();
        switch (imageView.getId()) {
            case "addButton" -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/util/DialogAddBookingDetail.fxml"));
                    Parent root = loader.load();
                    DialogAddBookingDetailController controller = loader.getController();
                    controller.initialize(getPhongTrongKhongTrungLich(donDatPhong)); // Gọi hàm initialize với dữ liệu phòng

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    // Căn giữa Dialog trên màn hình cha
                    if (bookingForm != null && bookingForm.getScene() != null) {
                        Stage parentStage = (Stage) bookingForm.getScene().getWindow();
                        stage.setOnShown(event -> {
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
                break;
            }
            case "removeButton" -> {
                if (selectedDetailBookingShort != null) {
                    List<String> messages = new ArrayList<>();
                    messages.add("Huỷ đặt phòng thành công");
                    messages.add("Huỷ đặt phòng thất bại");
                    EventBusManager.post(new ConfirmDialogEvent(
                            "Bạn có chắc chắn muốn huỷ đặt phòng này không?",
                            () -> {
                                // Xử lý huỷ đặt phòng
                                MemoTienChiTietDonDatPhong memoTienChiTietDonDatPhong = (MemoTienChiTietDonDatPhong) selectedDetailBookingShort.getUserData();
                               ChiTietDonDatPhong chiTietDonDatPhong = memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
                               if(chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong() != TrangThaiChiTietDonDatPhong.DAT_TRUOC) {
                                   messages.set(1, "Chỉ có thể huỷ đặt phòng đã đặt trước");
                                   return false;
                               }
                                chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_HUY);
                                generalDAO= Objects.requireNonNullElse(generalDAO, new GeneralDAO());
                                boolean r=  generalDAO.updateOb(chiTietDonDatPhong);
                                if (r)
                                    initDetailBookingShort();
                                return r;
                            },
                            () -> System.out.println("Đã huỷ"),
                            messages
                    ));
                }
            }
        }
    }
    public List<Phong> getPhongTrongKhongTrungLich(DonDatPhong donDatPhong) {
        // Khởi tạo DAO nếu chưa có (sử dụng toán tử null-safe)
        donDatPhongDAO.closeEntityManager();
        donDatPhongDAO =new DonDatPhongDAO();
        chiTietDonDatPhongDAO.closeEntityManager();
        chiTietDonDatPhongDAO=new ChiTietDonDatPhongDAO();
        generalDAO = Objects.requireNonNullElseGet(generalDAO, GeneralDAO::new);

        // Lấy danh sách đơn đặt phòng trùng khung ngày
        List<DonDatPhong> donDatPhongTrungNgay = donDatPhongDAO.getListDonDatPhongTheoNgayDenVaNgayDi(
                donDatPhong.getNgayNhan(),
                donDatPhong.getNgayTra()
        );

        // Lấy tất cả phòng trống
        List<Phong> phongTrong = generalDAO.findAll(Phong.class).stream()
                .filter(p -> p.getTrangThaiPhong() == TrangThaiPhong.TRONG)
                .collect(Collectors.toCollection(ArrayList::new)); // Dùng ArrayList để có thể remove

        // Lấy tất cả phòng đã đặt trong các đơn trùng ngày
        Set<Phong> phongDaDat = donDatPhongTrungNgay.stream()
                .flatMap(ddp -> chiTietDonDatPhongDAO.findChiTietDonDatPhongTheoMaDonDatPhong(ddp.getMaDonDatPhong()).stream())
                .map(ChiTietDonDatPhong::getPhong)
                .collect(Collectors.toSet());

        // Loại bỏ các phòng đã đặt khỏi danh sách phòng trống
        phongTrong.removeAll(phongDaDat);
        return phongTrong;
    }
    @Subscribe
    public void DialogAddBookingDetailEvent(DialogAddBookingDetailEvent event) {
        Platform.runLater(() -> {
            List<String> selectedRooms = event.getSelectedRooms();
            if(selectedRooms != null){
                for (String room : selectedRooms) {
                    generalDAO= Objects.requireNonNullElse(generalDAO, new GeneralDAO());
                    ChiTietDonDatPhong chiTietDonDatPhong = new ChiTietDonDatPhong();
                    chiTietDonDatPhong.setPhong(generalDAO.findOb(Phong.class, room));
                    chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DAT_TRUOC);
                    chiTietDonDatPhong.setDonDatPhong(donDatPhong);
                    generalDAO.addOb(chiTietDonDatPhong);
                }
                // xoa DetailBookingShort truoc do
                roomItemsContainer.getChildren().clear();
                initDetailBookingShort();
                EventBusManager.post(new ToastEvent("Thêm phòng thành công", ToastEvent.ToastType.SUCCESS));
            } else {
                EventBusManager.post(new ToastEvent("Không có phòng nào được chọn", ToastEvent.ToastType.ERROR));
            }
        });
    }
    public void cleanup() {
        EventBusManager.unregister(this);
    }
    public void closePage(){
        if(detailBookingFormController!=null)
            detailBookingFormController.cleanup();
        cleanup();
        donDatPhongDAO.getEm().close();
        chiTietDonDatPhongDAO.getEm().close();
    }
    @FXML  private void handleThemDonDat(ActionEvent event) {
        if(khachHangCuaEmpty==null){
            EventBusManager.post(new ToastEvent("Vui lòng thêm khách hàng trước", ToastEvent.ToastType.ERROR));
            return;
        }
        if(checkValidDonDatPhong()){
            DonDatPhong donDatPhong = new DonDatPhong();
            donDatPhong.setTenDoan(groupNameField.getText());
            donDatPhong.setSoLuongNguoiLon(Integer.parseInt(adultCountField.getText()));
            donDatPhong.setSoLuongTreEm(Integer.parseInt(childCountField.getText()));
            donDatPhong.setTienDatCoc(Double.parseDouble(depositField.getText()));
            donDatPhong.setGhiChu(notesTextArea.getText());
            donDatPhong.setTrangThai(TrangThaiDonDatPhong.DA_XAC_NHAN);
            donDatPhong.setKhachHang(khachHangCuaEmpty);

            donDatPhong.setNgayDat( LocalDateTime.of(bookingDatePicker.getValue(), LocalTime.now()));
            donDatPhong.setNgayNhan(checkinDatePicker.getValue().atStartOfDay());
            donDatPhong.setNgayTra(checkoutDatePicker.getValue().atStartOfDay());
            generalDAO = Objects.requireNonNullElseGet(generalDAO, GeneralDAO::new);
           donDatPhong.setNhanVien(generalDAO.findOb(NhanVien.class,"RAT-250001"));// ------------------------------------------------------------------------------------------
            donDatPhong.setMaDonDatPhong(donDatPhong.generateMaDonDatPhong());
            ValidatorFactory factory = Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory();
            Validator validator = factory.getValidator();

            // Kiểm tra validation
            Set<ConstraintViolation<DonDatPhong>> violations = validator.validate(donDatPhong);
            if (!violations.isEmpty()) {
                for (ConstraintViolation<DonDatPhong> violation : violations) {
                    String errorMessage = violation.getMessage();
                    EventBusManager.post(new ToastEvent(errorMessage, ToastEvent.ToastType.ERROR));
                    break;
                }
                return;
            }
            boolean r = generalDAO.addOb(donDatPhong);
            if (r) {
                EventBusManager.post(new ToastEvent("Thêm đơn đặt phòng thành công", ToastEvent.ToastType.SUCCESS));
                this.donDatPhong = donDatPhong;
                showEmptyView(false);
                loadThongTinDonDatPhong();
                initDetailBookingShort();
                setDisable(true);
            } else {
                EventBusManager.post(new ToastEvent("Thêm đơn đặt phòng thất bại", ToastEvent.ToastType.ERROR));
            }
        }
    }
    @FXML  private  void handleAddCustomer(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/util/DialogAddKhachHang.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            // Căn giữa Dialog trên màn hình cha
            if (bookingForm != null && bookingForm.getScene() != null) {
                Stage parentStage = (Stage) bookingForm.getScene().getWindow();
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
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class MemoTienChiTietDonDatPhong{
        private  ChiTietDonDatPhong chiTietDonDatPhong;
        private  double tienPhong;
        private double tienDichVu;
        private  double tienPhuThu;
        private  double tongTien;
    }
}
