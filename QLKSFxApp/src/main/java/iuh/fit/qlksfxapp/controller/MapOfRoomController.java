package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.DAO.DonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.ItemController.RoomItemController;
import iuh.fit.qlksfxapp.controller.ItemController.TypeOfRoomItemController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;


import java.io.IOException;
import java.util.List;

public class MapOfRoomController {

    @FXML private ScrollPane scrollPane;
    @FXML private GridPane gridPane;
    private List<Phong> allPhong;
    private int currentPage = 0;
    private final int ITEMS_PER_PAGE = 9; // 3x3 grid
    private GeneralDAO generalDAO;
    private boolean isLoading = false;

    @FXML
    public void initialize() {
        // Initialize the grid pane with room items
        generalDAO = new GeneralDAO();
        allPhong= generalDAO.findAll(Phong.class);
        loadRoomItems();
        scrollPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) { // Cuộn đến cuối
                loadMoreItems();
            }
        });
    }
    private void loadRoomItems() {
        // Xóa constraints cũ nếu có
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        // Tạo constraints cho 3 cột
        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 3);
            colConst.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(colConst);
        }

        // Tính số hàng cần thiết
        int totalRows = (int) Math.ceil((double) allPhong.size() / 3);

        // Tạo constraints cho các hàng
        for (int i = 0; i < totalRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConst);
        }

        // Load các item đầu tiên
        loadMoreItems();
        //
        loadMoreItems();
    }
    private void loadMoreItems() {
        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allPhong.size());

        if (startIndex >= allPhong.size()) return; // Đã load hết

        for (int i = startIndex; i < endIndex; i++) {
            Phong phong = allPhong.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/RoomItem.fxml"));
                Node item = loader.load();
                RoomItemController itemController = loader.getController();

                DonDatPhongDAO donDatPhongDAO = new DonDatPhongDAO();
                DonDatPhong donDatPhong = donDatPhongDAO.getDonDatPhongNowByIdPhong(phong.getMaPhong());
                itemController.setData(phong, donDatPhong);

                item.setOnMouseClicked(event -> {
                    // Xử lý sự kiện click
                });

                // Tính vị trí trong grid (n,3)
                int totalIndex = i; // Vị trí tổng thể trong danh sách
                int row = totalIndex / 3; // Hàng = tổng index / số cột
                int col = totalIndex % 3; // Cột = tổng index % số cột

                gridPane.add(item, col, row);

                // Thiết lập item mở rộng
                GridPane.setHgrow(item, Priority.ALWAYS);
                GridPane.setVgrow(item, Priority.ALWAYS);
                // Trong code Java
                // them khoang cach
                // Trong code Java
                gridPane.setHgap(10); // Khoảng cách ngang giữa các cột
                gridPane.setVgap(10); // Khoảng cách dọc giữa các hàng
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        currentPage++;
    }
    @FXML
    private void handleSearch(ActionEvent actionEvent) {
        System.out.println("Search button clicked");
    }
    // Thêm biến instance

    private void setupScrollListener() {
        scrollPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            // Kiểm tra đã cuộn gần cuối (80%) và không đang load
            if (newValue.doubleValue() > 0.8 && !isLoading && currentPage * ITEMS_PER_PAGE < allPhong.size()) {
                isLoading = true;
                Platform.runLater(() -> {
                    loadMoreItems();
                    isLoading = false;
                });
            }
        });
    }
}
//       listView.setCellFactory(param -> new ListCell<LoaiPhong>() {
//@Override
//protected void updateItem(LoaiPhong item, boolean empty) {
//    super.updateItem(item, empty);
//    if (empty || item == null) {
//        setGraphic(null);
//    } else {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/TypeOfRoomItem.fxml"));
//            HBox itemUI = loader.load();
//            TypeOfRoomItemController controller = loader.getController();
//            controller.setData(item, "Trống 10/12");
//            setGraphic(itemUI);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//            });