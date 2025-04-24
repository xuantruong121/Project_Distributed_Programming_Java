package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.DialogAddBookingDetailEvent;
import iuh.fit.qlksfxapp.controller.EventBus.EventBusManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.rmi.RemoteException;
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
            generalDAO = new GeneralDAOImpl();

        // Lấy tất cả loại phòng từ DB
        try {
            roomTypes = generalDAO.findAll(LoaiPhong.class);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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

        // In thông tin debug
        System.out.println("Lọc phòng theo loại: " + roomTypeName + ", mã loại: " + selectedType.get().getMaLoaiPhong());
        System.out.println("Tổng số phòng trống: " + data.size());

        // Lọc danh sách phòng theo loại phòng được chọn
        List<Phong> filteredRooms = data.stream()
                .filter(p -> p.getLoaiPhong().getMaLoaiPhong().equals(selectedType.get().getMaLoaiPhong()))
                .toList();

        // In thông tin debug
        System.out.println("Số phòng trống thuộc loại " + roomTypeName + ": " + filteredRooms.size());
        for (Phong p : filteredRooms) {
            System.out.println("Phòng trống: " + p.getMaPhong() + ", trạng thái: " + p.getTrangThaiPhong());
        }

        // Xóa tất cả RowConstraints hiện tại
        roomSelectionGrid.getRowConstraints().clear();

        // Tính toán số hàng cần thiết (5 phòng mỗi hàng)
        // Sử dụng double để tránh lỗi chia nguyên
        double roomCount = filteredRooms.size();
        int numRows = (int) Math.ceil(roomCount / 5.0);
        System.out.println("Số phòng: " + roomCount + ", Số hàng cần thiết: " + numRows);

        // Đảm bảo có ít nhất 1 hàng
        if (numRows == 0) numRows = 1;

        // Tạo động các RowConstraints
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(30.0);
            rowConstraints.setPrefHeight(40.0);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            roomSelectionGrid.getRowConstraints().add(rowConstraints);
        }

        // Hiển thị checkbox cho từng phòng phù hợp
        int row = 0;
        int col = 0;
        for (Phong p : filteredRooms) {
            // Tạo CheckBox với thông tin phòng
            CheckBox checkBox = new CheckBox(p.getMaPhong() + " - " + p.getLoaiPhong().getTenLoaiPhong());
            checkBox.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

            // Thêm tooltip để hiển thị thông tin chi tiết hơn
            Tooltip tooltip = new Tooltip("Mã phòng: " + p.getMaPhong() +
                                    "\nLoại phòng: " + p.getLoaiPhong().getTenLoaiPhong() +
                                    "\nTrạng thái: " + p.getTrangThaiPhong());
            tooltip.setStyle("-fx-font-size: 12px;");
            checkBox.setTooltip(tooltip);

            // Lưu vào map và thêm vào grid
            roomCheckBoxes.put(p.getMaPhong(), checkBox);
            roomSelectionGrid.add(checkBox, col, row);
            col++;
            if (col >= 5) {
                col = 0;
                row++;
            }
        }

        // Hiển thị thông báo nếu không có phòng nào
        if (filteredRooms.isEmpty()) {
            Label noRoomsLabel = new Label("Không có phòng trống thuộc loại " + roomTypeName);
            noRoomsLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            roomSelectionGrid.add(noRoomsLabel, 0, 0, 5, 1);
        }
    }
    private void confirmSelection() {
        // Process selected rooms
        List<String> selectedRooms = roomCheckBoxes.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .map(Map.Entry::getKey)
                .toList();

        // In ra thông tin debug
        System.out.println("Số phòng được chọn: " + selectedRooms.size());
        for (String roomId : selectedRooms) {
            System.out.println("Phòng được chọn: " + roomId);
        }

        // Kiểm tra nếu không có phòng nào được chọn
        if (selectedRooms.isEmpty()) {
            // Hiển thị thông báo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Không có phòng nào được chọn");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn ít nhất một phòng để thêm vào đơn đặt phòng.");
            alert.showAndWait();
            return;
        }

        EventBusManager.post(new DialogAddBookingDetailEvent(selectedRooms));
        closeDialog();
    }
    private void closeDialog() {
        // Close the dialog
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}