package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.KhachHangDAOImpl;
import iuh.fit.qlksfxapp.DAO.KhachHangDAO;
import iuh.fit.qlksfxapp.Entity.KhachHang;
import iuh.fit.qlksfxapp.controller.EventBus.DialogAddKhachHangEvent;
import iuh.fit.qlksfxapp.controller.EventBus.EventBusManager;
import iuh.fit.qlksfxapp.controller.EventBus.ToastEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import lombok.Setter;

import java.rmi.RemoteException;
import java.util.Objects;
import java.util.Optional;
@Setter
public class DialogAddKhachHangController {
    @FXML
     private TextField cccd,tenKhachHangField,soDienThoaiField,emailField;
    @FXML
    private ComboBox<String> gioiTinhComboBox,quocTichComboBox;
    @FXML private DatePicker ngaySinhPicker;
    private KhachHang khachHang;
    private GeneralDAO generalDAO;
    private    KhachHangDAO khachHangDAO;
    private  DialogAddKhachHangEvent.Context context;
    @FXML public  void initialize() {
        // Khởi tạo các ComboBox
        initComboBox();
    }
    private void initComboBox(){
        // Gán giá trị cho ComboBox giới tính
        gioiTinhComboBox.getItems().addAll("Nam", "Nữ", "Khác");
        gioiTinhComboBox.setValue("Nam"); // Chọn giá trị mặc định là "Nam"
        // Gán giá trị cho ComboBox quốc tịch
        quocTichComboBox.getItems().addAll("Việt Nam", "Mỹ", "Nhật Bản", "Hàn Quốc");
        quocTichComboBox.setValue("Việt Nam"); // Chọn giá trị mặc định là "Việt Nam"
    }
    @FXML public void handleConfirm(ActionEvent event) throws RemoteException {
        if(!checkValid()){
            return;
        }
        if(khachHang==null){
            khachHang = new KhachHang();
            khachHang.setCanCuocCongDan(cccd.getText());
            khachHang.setTenKhachHang(tenKhachHangField.getText());
            khachHang.setSoDienThoai(soDienThoaiField.getText());
            khachHang.setEmail(emailField.getText());
            khachHang.setGioiTinh(gioiTinhComboBox.getValue().equals("Nam"));
            khachHang.setQuocTich(quocTichComboBox.getValue());
            khachHang.setNgaySinh(ngaySinhPicker.getValue());
        }
        khachHangDAO = Objects.requireNonNullElseGet(khachHangDAO, KhachHangDAOImpl::new);
        if(khachHangDAO.findKhachHangByCccd(cccd.getText())!=null){
            EventBusManager.post(new DialogAddKhachHangEvent(khachHang,context));
            Optional.ofNullable(cccd.getScene().getWindow()).ifPresent(Window::hide);
        }else{
            generalDAO = Objects.requireNonNullElseGet(generalDAO, GeneralDAOImpl::new);
            boolean result = generalDAO.addOb(khachHang);
//            EventBusManager.post(new ToastEvent(result?"Thêm mới khách hàng thành công!":" Thêm mới khách hàng thất bại!", result? ToastEvent.ToastType.SUCCESS:ToastEvent.ToastType.ERROR));
//            EventBusManager.post(new DialogAddKhachHangEvent(khachHang));
            if(result){
                // Đóng cửa sổ
                EventBusManager.post(new DialogAddKhachHangEvent(khachHang,context));
                Optional.ofNullable(cccd.getScene().getWindow()).ifPresent(Window::hide);
            }
        }
    }
    private void setDisable(boolean isDisable){
        tenKhachHangField.setDisable(isDisable);
        soDienThoaiField.setDisable(isDisable);
        emailField.setDisable(isDisable);
        gioiTinhComboBox.setDisable(isDisable);
        quocTichComboBox.setDisable(isDisable);
        ngaySinhPicker.setDisable(isDisable);
    }
    @FXML public void handleCancel(ActionEvent event) {
        // Đóng cửa sổ
        Optional.ofNullable(cccd.getScene().getWindow()).ifPresent(Window::hide);
    }
    @FXML public void handleSearchCccd(MouseEvent mouseEvent) throws RemoteException {
        if(cccd.getText().isEmpty()){
            EventBusManager.post(new ToastEvent("Vui lòng nhập căn cước công dân!", ToastEvent.ToastType.ERROR));
            return;
        }
        khachHangDAO= Objects.requireNonNullElseGet(khachHangDAO, KhachHangDAOImpl::new);
        KhachHang khachHang = khachHangDAO.findKhachHangByCccd(cccd.getText());
        if(khachHang==null){
            EventBusManager.post(new ToastEvent("Không tìm thấy khách hàng!", ToastEvent.ToastType.ERROR));
            setDisable(false);
            return;
        }
        autoFill(khachHang);
        setDisable(true);
    }
    private void autoFill(KhachHang kh){
        khachHang = kh;
        tenKhachHangField.setText(kh.getTenKhachHang());
        soDienThoaiField.setText(kh.getSoDienThoai());
        emailField.setText(kh.getEmail());
        gioiTinhComboBox.setValue(kh.isGioiTinh() ? "Nam" : "Nữ");
        // Nếu quốc tịch không có trong danh sách thì thêm vào
        if (!quocTichComboBox.getItems().contains(kh.getQuocTich())) {
            quocTichComboBox.getItems().add(kh.getQuocTich());
        }else{
            quocTichComboBox.setValue(kh.getQuocTich());
        }
        ngaySinhPicker.setValue(kh.getNgaySinh());
    }
    private boolean checkValid(){
        if(cccd.getText().isEmpty() ||
                tenKhachHangField.getText().isEmpty() ||
                soDienThoaiField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                gioiTinhComboBox.getValue() == null ||
                quocTichComboBox.getValue() == null ||
                ngaySinhPicker.getValue() == null) {
            EventBusManager.post(new ToastEvent("Vui lòng điền đầy đủ thông tin!", ToastEvent.ToastType.ERROR));
            return false;
        }
        if(cccd.getText().length() != 12) {
            EventBusManager.post(new ToastEvent("Căn cước công dân phải có 12 số!", ToastEvent.ToastType.ERROR));
            return false;
        }
        if(soDienThoaiField.getText().length() != 10) {
            EventBusManager.post(new ToastEvent("Số điện thoại phải có 10 số!", ToastEvent.ToastType.ERROR));
            return false;
        }
        return true;
    }
}
