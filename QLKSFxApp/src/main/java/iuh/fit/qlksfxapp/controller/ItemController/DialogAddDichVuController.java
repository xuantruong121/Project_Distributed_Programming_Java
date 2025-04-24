package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.DichVu;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.*;
import iuh.fit.qlksfxapp.util.FormatUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DialogAddDichVuController {
    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private GridPane dichVuSelectionGrid;
    @FXML private Spinner<Integer> soLuongSpinner;
    @FXML private Label tenDichVuLabel,gia;
    private GeneralDAO generalDAO;
    private List<DichVu> data;
    private DichVu selectedDichVu;
    public void initialize() {
        loadData();
        // Gán sự kiện chọn loại phòng
        confirmButton.setOnAction(e -> confirmSelection());
        cancelButton.setOnAction(e -> closeDialog());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        soLuongSpinner.setValueFactory(valueFactory);
        soLuongSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(selectedDichVu != null){
                gia.setText(FormatUtil.formatMoney(selectedDichVu.getGiaDichVu()*newValue)+ "VND");
            }
        });
    }
    private void loadData() {
        if (generalDAO == null)
            generalDAO = new GeneralDAO();

        // Lấy tất cả loại phòng từ DB
        data = generalDAO.findAll(DichVu.class);

        // Clear grid và checkbox cũ
        dichVuSelectionGrid.getChildren().clear();

        // Hiển thị checkbox cho từng phòng phù hợp
        int row = 0;
        int col = 0;
        for (DichVu dv : data) {
            Button dvBtn = new Button(dv.getTenDichVu());
            dvBtn.setPrefWidth(100);
            dvBtn.setOnAction(e -> hienThiChiTietDichVu(dv));

            dichVuSelectionGrid.add(dvBtn, col, row);
            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
    }
    private void hienThiChiTietDichVu(DichVu dv) {
        // Hiển thị chi tiết dịch vụ
        tenDichVuLabel.setText("Tên dịch vụ: "+  dv.getTenDichVu());
        gia.setText(FormatUtil.formatMoney(dv.getGiaDichVu())+ "VND");
        if(selectedDichVu != null && selectedDichVu.getMaDichVu().equals(dv.getMaDichVu())){
            soLuongSpinner.getValueFactory().setValue(1);
        }else {
            soLuongSpinner.getValueFactory().setValue(1);
        }
        selectedDichVu = dv;
    }
    private void confirmSelection() {
        if(selectedDichVu == null){
            EventBusManager.post(new ToastEvent("Vui lòng chọn dịch vụ!", ToastEvent.ToastType.ERROR));
            return;
        }
        EventBusManager.post(new DialogAddDichVuEvent(selectedDichVu,soLuongSpinner.getValue()));
        closeDialog();
    }
    private void closeDialog() {
        // Close the dialog
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
