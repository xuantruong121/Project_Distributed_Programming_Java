package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.DialogAddBookingDetailEvent;
import iuh.fit.qlksfxapp.controller.EventBus.EventBusManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DialogAddBookingDetailController {
    @FXML
    private ComboBox<String> roomTypeComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private GridPane roomSelectionGrid;

    private Map<String, CheckBox> roomCheckBoxes = new HashMap<>();
    private GeneralDAO generalDAO;
    private List<Phong> data;
    @FXML
    private List<LoaiPhong> roomTypes; // Thêm biến này để lưu danh sách loại phòng
    public void initialize(List<Phong> phong) {
        this.data = phong;
        if (generalDAO == null)
            generalDAO = new GeneralDAO();

        // Lấy tất cả loại phòng từ DB
        roomTypes = generalDAO.findAll(LoaiPhong.class);
        List<String> roomTypeNames = roomTypes.stream()
                .map(LoaiPhong::getTenLoaiPhong)
                .toList();
        roomTypeComboBox.getItems().addAll(roomTypeNames);
        // Gán sự kiện chọn loại phòng
        roomTypeComboBox.setOnAction(e -> loadRoomsByType(roomTypeComboBox.getValue()));
        confirmButton.setOnAction(e -> confirmSelection());
        cancelButton.setOnAction(e -> closeDialog());

        // Chọn loại phòng mặc định và load phòng
        roomTypeComboBox.getSelectionModel().selectFirst();
        loadRoomsByType(roomTypeComboBox.getValue());
    }

    private void loadRoomsByType(String roomTypeName) {
        // Clear grid và checkbox cũ
        roomSelectionGrid.getChildren().clear();
        roomCheckBoxes.clear();

        // Lọc loại phòng tương ứng
        Optional<LoaiPhong> selectedType = roomTypes.stream()
                .filter(lp -> lp.getTenLoaiPhong().equals(roomTypeName))
                .findFirst();
        if (selectedType.isEmpty()) return;
        // Lọc danh sách phòng theo loại phòng được chọn
        List<Phong> filteredRooms = data.stream()
                .filter(p -> p.getLoaiPhong().getMaLoaiPhong().equals(selectedType.get().getMaLoaiPhong()))
                .toList();
        // Hiển thị checkbox cho từng phòng phù hợp
        int row = 0;
        int col = 0;
        for (Phong p : filteredRooms) {
            CheckBox checkBox = new CheckBox(p.getMaPhong());
            roomCheckBoxes.put(p.getMaPhong(), checkBox);
            roomSelectionGrid.add(checkBox, col, row);
            col++;
            if (col >= 5) {
                col = 0;
                row++;
            }
        }
    }
    private void confirmSelection() {
        // Process selected rooms
        List<String> selectedRooms = roomCheckBoxes.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .map(Map.Entry::getKey)
                .toList();
        EventBusManager.post(new DialogAddBookingDetailEvent(selectedRooms));
        closeDialog();
    }
    private void closeDialog() {
        // Close the dialog
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}