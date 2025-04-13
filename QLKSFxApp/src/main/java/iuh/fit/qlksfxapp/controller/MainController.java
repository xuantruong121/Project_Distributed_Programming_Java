package iuh.fit.qlksfxapp.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController {
    @FXML
    private Pane menuPane;
    @FXML
    private Pane contentPane;
    @FXML
    private VBox menuVBox;
    @FXML
    private Button toggleButton;
    @FXML
    private Button roomManagementButton;
    @FXML
    private VBox roomManagementSubMenu;
    @FXML
    private Button statisticsButton;
    @FXML
    private VBox statisticsSubMenu;
    @FXML
    private ImageView logo;

    private boolean isSidebarExpanded = true;
    private final double EXPANDED_WIDTH = 225.0;
    private final double COLLAPSED_WIDTH = 50.0;
    private boolean isRoomManagementMenuOpen = false;
    private boolean isStatisticsMenuOpen = false;
    private Button expandButton; // Nút để mở rộng sidebar khi đã thu gọn

    @FXML
    private void initialize() {
        // Khởi tạo content pane mặc định
        showRoomBookingPane();

        // Tăng kích thước chữ cho các nút menu
        setMenuButtonsFontSize("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Tạo nút để mở rộng sidebar khi đã thu gọn
        createExpandButton();
    }

    private void createExpandButton() {
        expandButton = new Button("≡");
        expandButton.setStyle("-fx-background-color: #2E2E2E; -fx-text-fill: white; " +
                "-fx-font-size: 20px; -fx-border-color: white; -fx-border-radius: 5;");
        expandButton.setPrefWidth(30.0);
        expandButton.setPrefHeight(30.0);
        expandButton.setLayoutX(10.0);
        expandButton.setLayoutY(10.0);
        expandButton.setOnAction(event -> toggleSidebar());
        expandButton.setVisible(false); // Khởi tạo ẩn
    }

    private void setMenuButtonsFontSize(String style) {
        // Áp dụng style cho tất cả các nút trong menu
        for (var node : menuVBox.getChildren()) {
            if (node instanceof Button) {
                String currentStyle = ((Button) node).getStyle();
                ((Button) node).setStyle(currentStyle + "; " + style);
            } else if (node instanceof VBox) {
                for (var subNode : ((VBox) node).getChildren()) {
                    if (subNode instanceof Button) {
                        String currentStyle = ((Button) subNode).getStyle();
                        ((Button) subNode).setStyle(currentStyle + "; " + style);
                    }
                }
            }
        }

        // Áp dụng cho các nút trong submenu
        for (var node : roomManagementSubMenu.getChildren()) {
            if (node instanceof Button) {
                String currentStyle = ((Button) node).getStyle();
                ((Button) node).setStyle(currentStyle + "; -fx-font-size: 14px;");
            }
        }

        for (var node : statisticsSubMenu.getChildren()) {
            if (node instanceof Button) {
                String currentStyle = ((Button) node).getStyle();
                ((Button) node).setStyle(currentStyle + "; -fx-font-size: 14px;");
            }
        }
    }

    @FXML
    private void toggleSidebar() {
        if (isSidebarExpanded) {
            // Thu gọn sidebar
            collapseSidebar();
        } else {
            // Mở rộng sidebar
            expandSidebar();
        }
    }

    private void collapseSidebar() {
        // Ẩn các phần tử trong sidebar
        menuVBox.setVisible(false);
        logo.setVisible(false);
        toggleButton.setVisible(false);

        // Thu gọn sidebar
        menuPane.setPrefWidth(COLLAPSED_WIDTH);
        contentPane.setLayoutX(COLLAPSED_WIDTH);
        contentPane.setPrefWidth(menuPane.getScene().getWindow().getWidth() - COLLAPSED_WIDTH);

        // Hiển thị nút mở rộng
        if (!menuPane.getChildren().contains(expandButton)) {
            menuPane.getChildren().add(expandButton);
        }
        expandButton.setVisible(true);

        // Đóng các submenu nếu đang mở
        if (isRoomManagementMenuOpen) {
            toggleRoomManagementMenu();
        }
        if (isStatisticsMenuOpen) {
            toggleStatisticsMenu();
        }

        isSidebarExpanded = false;
    }

    private void expandSidebar() {
        // Hiển thị lại các phần tử trong sidebar
        menuVBox.setVisible(true);
        logo.setVisible(true);
        toggleButton.setVisible(true);

        // Mở rộng sidebar
        menuPane.setPrefWidth(EXPANDED_WIDTH);
        contentPane.setLayoutX(EXPANDED_WIDTH);
        contentPane.setPrefWidth(menuPane.getScene().getWindow().getWidth() - EXPANDED_WIDTH);

        // Ẩn nút mở rộng
        expandButton.setVisible(false);

        isSidebarExpanded = true;
    }

    @FXML
    private void toggleRoomManagementMenu() {
        isRoomManagementMenuOpen = !isRoomManagementMenuOpen;
        roomManagementSubMenu.setVisible(isRoomManagementMenuOpen);
        roomManagementSubMenu.setManaged(isRoomManagementMenuOpen);
        roomManagementButton.setText("QUẢN LÝ ĐẶT PHÒNG " + (isRoomManagementMenuOpen ? "▲" : "▼"));
    }

    @FXML
    private void toggleStatisticsMenu() {
        isStatisticsMenuOpen = !isStatisticsMenuOpen;
        statisticsSubMenu.setVisible(isStatisticsMenuOpen);
        statisticsSubMenu.setManaged(isStatisticsMenuOpen);
        statisticsButton.setText("THỐNG KÊ " + (isStatisticsMenuOpen ? "▲" : "▼"));
    }

    @FXML
    private void showRoomBookingPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightblue;");
        Label label = new Label("Đơn đặt phòng");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    // Các phương thức khác giữ nguyên
    @FXML
    private void showRoomSalesPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightgreen;");
        Label label = new Label("Bán đồ phòng");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    private void showRoomTypePane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightyellow;");
        Label label = new Label("Thông tin loại phòng");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    private void showRevenueStatsPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightblue;");
        Label label = new Label("Thống kê doanh thu");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    private void showInventoryStatsPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightgreen;");
        Label label = new Label("Thống kê kho");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    private void showCustomerStatsPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightyellow;");
        Label label = new Label("Thống kê khách");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    private void showPromotionsPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightblue;");
        Label label = new Label("Quản lý khuyến mãi");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    private void showReportsPane() {
        contentPane.getChildren().clear();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightgreen;");
        Label label = new Label("Đơn báo cáo");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }
}