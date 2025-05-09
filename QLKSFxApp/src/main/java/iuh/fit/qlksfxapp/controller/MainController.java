package iuh.fit.qlksfxapp.controller;

import com.google.common.eventbus.Subscribe;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.*;
import iuh.fit.qlksfxapp.util.ConfirmDialog;
import javafx.application.Platform;
import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import iuh.fit.qlksfxapp.controller.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {
    @FXML
    private Pane menuPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private VBox menuVBox;
    @FXML
    private Button toggleButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button bookingManagementButton;
    @FXML
    private VBox bookingManagementSubMenu;
    private VBox roomManagementSubMenu;
    @FXML
    private Button statisticsButton;
    @FXML
    private VBox statisticsSubMenu;
    @FXML
    private Button promotionsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button serviceManagementButton;
    @FXML
    private Button customerManagementButton;
    @FXML
    private Button staffManagementButton;
    @FXML
    private Button inventoryManagementButton;
    @FXML
    private Button discountManagementButton;
    @FXML
    private Button roomManagementButton;
    @FXML
    private ImageView logo;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView userAvatar;
    @FXML
    private Button userProfileButton;
    @FXML
    private Button notificationButton;
    @FXML
    private Label greetingLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userRoleLabel;

    private boolean isSidebarExpanded = true;
    private boolean isRoomManagementMenuOpen = false;
    private boolean isStatisticsMenuOpen = false;
    private Button expandButton; // Button to expand the sidebar when collapsed

    // Track the active menu button for highlighting
    private Button activeMenuButton;
    private String currentFxml;
    private Object currentController;
    @FXML
    private void initialize() {
        // Initialize default content pane
        showMainPane();

        // Create button to expand sidebar when collapsed
        createExpandButton();

        // Set up the sidebar menu with active indicators
        setupSidebarMenu();

        // Update greeting based on time of day
        updateGreeting();
        EventBusManager.register(this);
    }

    private void setupSidebarMenu() {
        // Set home button as default active menu
        setActiveMenu(homeButton);

        // Add click listeners to all main menu buttons
        homeButton.setOnMouseClicked(event -> {
            showMainPane();
            setActiveMenu(homeButton);
        });

        // Other button listeners can be added here
    }

    private void setActiveMenu(Button button) {
        // Remove active style from previous button
        if (activeMenuButton != null) {
            activeMenuButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
                    "-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: BASELINE_LEFT; " +
                    "-fx-padding: 5 15 5 15;");
        }

        // Set active style to current button
        button.setStyle("-fx-background-color: #1a56db; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: BASELINE_LEFT; " +
                "-fx-padding: 5 15 5 15;");

        activeMenuButton = button;
    }

    private void updateGreeting() {
        int hour = java.time.LocalTime.now().getHour();
        String greeting;

        if (hour >= 5 && hour < 12) {
            greeting = "Chào buổi sáng";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Chào buổi chiều";
        } else {
            greeting = "Chào buổi tối";
        }

        greetingLabel.setText(greeting + ", chúc bạn có một ngày tuyệt vời!");
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
        expandButton.setVisible(false); // Initially hidden
    }


    @FXML
    private void toggleSidebar() {
        if (isSidebarExpanded) {
            // Collapse sidebar
            collapseSidebar();
        } else {
            // Expand sidebar
            expandSidebar();
        }
    }

    private void collapseSidebar() {
        // Hide sidebar elements
        menuVBox.setVisible(false);
        logo.setVisible(false);
        toggleButton.setVisible(false);

        // Collapse sidebar width
        double COLLAPSED_WIDTH = 50.0;
        menuPane.setPrefWidth(COLLAPSED_WIDTH);
        contentPane.setLayoutX(COLLAPSED_WIDTH);
        contentPane.setPrefWidth(menuPane.getScene().getWindow().getWidth() - COLLAPSED_WIDTH);

        // Show expand button
        if (!menuPane.getChildren().contains(expandButton)) {
            menuPane.getChildren().add(expandButton);
        }
        expandButton.setVisible(true);

        // Close submenus if open
        if (isRoomManagementMenuOpen) {
            toggleBookingManagementMenu();
        }
        if (isStatisticsMenuOpen) {
            toggleStatisticsMenu();
        }

        isSidebarExpanded = false;
    }

    private void expandSidebar() {
        // Show sidebar elements
        menuVBox.setVisible(true);
        logo.setVisible(true);
        toggleButton.setVisible(true);

        // Expand sidebar width
        double EXPANDED_WIDTH = 225.0;
        menuPane.setPrefWidth(EXPANDED_WIDTH);
        contentPane.setLayoutX(EXPANDED_WIDTH);
        contentPane.setPrefWidth(menuPane.getScene().getWindow().getWidth() - EXPANDED_WIDTH);

        // Hide expand button
        expandButton.setVisible(false);

        isSidebarExpanded = true;
    }

    @FXML
    private void toggleBookingManagementMenu() {
        isRoomManagementMenuOpen = !isRoomManagementMenuOpen;
        bookingManagementSubMenu.setVisible(isRoomManagementMenuOpen);
        bookingManagementSubMenu.setManaged(isRoomManagementMenuOpen);
        bookingManagementButton.setText("QUẢN LÝ ĐẶT PHÒNG " + (isRoomManagementMenuOpen ? "▲" : "▼"));

        // Set active state if opened
        if (isRoomManagementMenuOpen) {
            setActiveMenu(bookingManagementButton);
        }
    }

    @FXML
    private void toggleStatisticsMenu() {
        isStatisticsMenuOpen = !isStatisticsMenuOpen;
        statisticsSubMenu.setVisible(isStatisticsMenuOpen);
        statisticsSubMenu.setManaged(isStatisticsMenuOpen);
        statisticsButton.setText("THỐNG KÊ " + (isStatisticsMenuOpen ? "▲" : "▼"));

        // Set active state if opened
        if (isStatisticsMenuOpen) {
            setActiveMenu(statisticsButton);
        }
    }
    // Room Management submenu handlers
    @FXML
    private void showRoomManagementPane() {
        try {
            // Load the Booking Management interface from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuanLyPhong.fxml"));
            Parent roomManagementView = loader.load();

            // Clear previous content and add new interface to content pane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(roomManagementView);

            // Set anchor properties to fill the content pane
            AnchorPane.setTopAnchor(roomManagementView, 0.0);
            AnchorPane.setBottomAnchor(roomManagementView, 0.0);
            AnchorPane.setLeftAnchor(roomManagementView, 0.0);
            AnchorPane.setRightAnchor(roomManagementView, 0.0);

            // Mark the Room Management button as active
            setActiveMenu(roomManagementButton);

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            System.err.println("Cannot load Room Management interface: " + e.getMessage());

            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải giao diện Quản lý Phòng");
            alert.setContentText("Lỗi: " + cause.getMessage());
            alert.showAndWait();

            // Hiển thị một giao diện thông báo đơn giản thay thế
            Pane errorPane = new Pane();
            errorPane.setStyle("-fx-background-color: white;");
            Label errorLabel = new Label("Không thể tải giao diện Quản lý Phòng. Vui lòng kiểm tra lại cấu hình Hibernate.");
            errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-padding: 20;");
            errorPane.getChildren().add(errorLabel);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(errorPane);
            AnchorPane.setTopAnchor(errorPane, 0.0);
            AnchorPane.setBottomAnchor(errorPane, 0.0);
            AnchorPane.setLeftAnchor(errorPane, 0.0);
            AnchorPane.setRightAnchor(errorPane, 0.0);

            // Mark the Room Management button as active
            setActiveMenu(bookingManagementButton);
        }
    }

    // Statistics submenu handlers
    @FXML
    private void showRevenueStatsPane() {
        updateContent("Thống kê doanh thu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RevenueChartView.fxml"));
        Parent view = null;
        try {
            view = loader.load();
            // Truyền dữ liệu nếu controller có phương thức setData
//            Object controller = loader.getController();
//            if (controller instanceof BookingFormController) {
//                ((DataReceivable) controller).setData(event.getData());
//                currentController = controller;
//                currentFxml = "/fxml/BookingForm.fxml";
//            }
            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    private void showInventoryStatsPane() {
        updateContent("Thống kê kho");
    }

    @FXML
    private void showCustomerStatsPane() {
        updateContent("Thống kê khách");
    }

    // Other menu handlers
    @FXML
    private void showPromotionsPane() {
        updateContent("Quản lý khuyến mãi");
        setActiveMenu(promotionsButton);
    }

    @FXML
    private void showReportsPane() {
        updateContent("Đơn báo cáo");
        setActiveMenu(reportsButton);
    }

    @FXML
    private void showServiceManagementPane() {
        try {
            // Load the Service Management interface from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuanLyDichVu.fxml"));
            Parent serviceManagementView = loader.load();

            // Clear previous content and add new interface to content pane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(serviceManagementView);

            // Set anchor properties to fill the content pane
            AnchorPane.setTopAnchor(serviceManagementView, 0.0);
            AnchorPane.setBottomAnchor(serviceManagementView, 0.0);
            AnchorPane.setLeftAnchor(serviceManagementView, 0.0);
            AnchorPane.setRightAnchor(serviceManagementView, 0.0);

            // Mark the Service Management button as active
            setActiveMenu(serviceManagementButton);

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            System.err.println("Cannot load Service Management interface: " + e.getMessage());

            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải giao diện Quản lý Dịch vụ");
            alert.setContentText("Lỗi: " + cause.getMessage());
            alert.showAndWait();

            // Hiển thị một giao diện thông báo đơn giản thay thế
            Pane errorPane = new Pane();
            errorPane.setStyle("-fx-background-color: white;");
            Label errorLabel = new Label("Không thể tải giao diện Quản lý Dịch vụ. Vui lòng kiểm tra lại cấu hình Hibernate.");
            errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-padding: 20;");
            errorPane.getChildren().add(errorLabel);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(errorPane);
            AnchorPane.setTopAnchor(errorPane, 0.0);
            AnchorPane.setBottomAnchor(errorPane, 0.0);
            AnchorPane.setLeftAnchor(errorPane, 0.0);
            AnchorPane.setRightAnchor(errorPane, 0.0);

            // Mark the Service Management button as active
            setActiveMenu(serviceManagementButton);
        }
    }

    @FXML
    private void showCustomerManagementPane() {
        try {
            // Load the Customer Management interface from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuanLyKhachHang.fxml"));
            Parent customerManagementView = loader.load();

            // Clear previous content and add new interface to content pane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(customerManagementView);

            // Set anchor properties to fill the content pane
            AnchorPane.setTopAnchor(customerManagementView, 0.0);
            AnchorPane.setBottomAnchor(customerManagementView, 0.0);
            AnchorPane.setLeftAnchor(customerManagementView, 0.0);
            AnchorPane.setRightAnchor(customerManagementView, 0.0);

            // Mark the Customer Management button as active
            setActiveMenu(customerManagementButton);

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            System.err.println("Cannot load Customer Management interface: " + e.getMessage());

            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải giao diện Quản lý Khách hàng");
            alert.setContentText("Lỗi: " + cause.getMessage());
            alert.showAndWait();

            // Hiển thị một giao diện thông báo đơn giản thay thế
            Pane errorPane = new Pane();
            errorPane.setStyle("-fx-background-color: white;");
            Label errorLabel = new Label("Không thể tải giao diện Quản lý Khách hàng. Vui lòng kiểm tra lại cấu hình Hibernate.");
            errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-padding: 20;");
            errorPane.getChildren().add(errorLabel);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(errorPane);
            AnchorPane.setTopAnchor(errorPane, 0.0);
            AnchorPane.setBottomAnchor(errorPane, 0.0);
            AnchorPane.setLeftAnchor(errorPane, 0.0);
            AnchorPane.setRightAnchor(errorPane, 0.0);

            // Mark the Customer Management button as active
            setActiveMenu(customerManagementButton);
        }
    }

    @FXML
    private void showStaffManagementPane() {
        try {
            // Load the Staff Management interface from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuanLyNhanVien.fxml"));
            Parent staffManagementView = loader.load();

            // Clear previous content and add new interface to content pane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(staffManagementView);

            // Set anchor properties to fill the content pane
            AnchorPane.setTopAnchor(staffManagementView, 0.0);
            AnchorPane.setBottomAnchor(staffManagementView, 0.0);
            AnchorPane.setLeftAnchor(staffManagementView, 0.0);
            AnchorPane.setRightAnchor(staffManagementView, 0.0);

            // Mark the Staff Management button as active
            setActiveMenu(staffManagementButton);

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            System.err.println("Cannot load Staff Management interface: " + e.getMessage());

            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải giao diện Quản lý Nhân viên");
            alert.setContentText("Lỗi: " + cause.getMessage());
            alert.showAndWait();

            // Hiển thị một giao diện thông báo đơn giản thay thế
            Pane errorPane = new Pane();
            errorPane.setStyle("-fx-background-color: white;");
            Label errorLabel = new Label("Không thể tải giao diện Quản lý Nhân viên. Vui lòng kiểm tra lại cấu hình Hibernate.");
            errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-padding: 20;");
            errorPane.getChildren().add(errorLabel);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(errorPane);
            AnchorPane.setTopAnchor(errorPane, 0.0);
            AnchorPane.setBottomAnchor(errorPane, 0.0);
            AnchorPane.setLeftAnchor(errorPane, 0.0);
            AnchorPane.setRightAnchor(errorPane, 0.0);

            // Mark the Staff Management button as active
            setActiveMenu(staffManagementButton);
        }
    }

    @FXML
    private void showInventoryManagementPane() {
        updateContent("Quản lý kho");
        setActiveMenu(inventoryManagementButton);
    }

    @FXML
    private void showDiscountManagementPane() {
        updateContent("Quản lý khuyến mãi");
        setActiveMenu(discountManagementButton);
    }

    @FXML
    public void showMainPane() {
        loadDashboardContent();
    }

    private void loadDashboardContent() {
        // Here you would implement loading the dashboard components
        // such as room statistics, booking information, etc.
        Parent newContent = null;
        try {
            URL resource = getClass().getResource("/fxml/TrangChu.fxml");
            FXMLLoader loader = new FXMLLoader(resource);
            newContent = loader.load();
            contentPane.getChildren().setAll(newContent);
            AnchorPane.setTopAnchor(newContent, 0.0);
            AnchorPane.setBottomAnchor(newContent, 0.0);
            AnchorPane.setLeftAnchor(newContent, 0.0);
            AnchorPane.setRightAnchor(newContent, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateContent(String title) {
        // Update content pane
        contentPane.getChildren().clear();

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: " + "white" + ";");
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");
        pane.getChildren().add(label);
        contentPane.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    @FXML
    public void showUserProfile() {
        try {
            // Tạo FXMLLoader để tải UserProfile.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserProfile.fxml"));
            Parent userProfileView = loader.load();

            // Clear previous content and add new interface to content pane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(userProfileView);

            // Set anchor properties to fill the content pane
            AnchorPane.setTopAnchor(userProfileView, 0.0);
            AnchorPane.setBottomAnchor(userProfileView, 0.0);
            AnchorPane.setLeftAnchor(userProfileView, 0.0);
            AnchorPane.setRightAnchor(userProfileView, 0.0);

        } catch (IOException e) {
            e.printStackTrace();

            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể mở thông tin người dùng");
            alert.setContentText("Lỗi: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void handleSubBookingManager(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonType = (String) clickedButton.getUserData();

        try {
            // Giả sử bạn có các FXML tương ứng với từng buttonText
            String fxmlFile = switch (buttonType) {
                case "BOOKING" -> "/fxml/BookingForm.fxml";
                case "MAP" -> "/fxml/MapOfRoom.fxml";
                case "TYPE_INFO" -> "/fxml/InfoTypeOfRoom.fxml";
                default -> null;
            };

            if (fxmlFile != null) {
                // cleanup nếu trang hiện tại là BookingForm
                if (currentFxml != null && currentFxml.equals("/fxml/BookingForm.fxml") && currentController instanceof BookingFormController cc) {
                    cc.closePage();
                } else if (currentFxml != null && currentFxml.equals("/fxml/MapOfRoom.fxml") && currentController instanceof MapOfRoomController cc) {
                    cc.closePage();
                }
                URL resource = getClass().getResource(fxmlFile);
                if (resource == null) {
                    throw new IOException("Không tìm thấy file: " + fxmlFile);
                }

                FXMLLoader loader = new FXMLLoader(resource);
                Parent newContent = loader.load();
                currentController = loader.getController();
                if(currentController instanceof  BookingFormController){
                    ((BookingFormController) currentController).setData(null);
                }
                currentFxml = fxmlFile;

                contentPane.getChildren().setAll(newContent);
                AnchorPane.setTopAnchor(newContent, 0.0);
                AnchorPane.setBottomAnchor(newContent, 0.0);
                AnchorPane.setLeftAnchor(newContent, 0.0);
                AnchorPane.setRightAnchor(newContent, 0.0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    public void handleSubRoomManager(ActionEvent event) {
//        Button clickedButton = (Button) event.getSource();
//        String buttonType = (String) clickedButton.getUserData();
//        try {
//            // Giả sử bạn có các FXML tương ứng với từng buttonText
//            String fxmlFile = switch (buttonType) {
//                case "BOOKING" -> "/fxml/BookingForm.fxml";
//                case "MAP" -> "/fxml/MapOfRoom.fxml";
//                case "TYPE_INFO" -> "/fxml/InfoTypeOfRoom.fxml";
//                default -> null;
//            };
//            if (fxmlFile != null) {
//                URL resource = getClass().getResource(fxmlFile);
//                System.out.println("Resource URL: " + resource);
//                if (resource == null) {
//                    throw new IOException("Cannot find resource: " + fxmlFile);
//                }
//
//                FXMLLoader loader = new FXMLLoader(resource);
//                Parent newContent = loader.load();
//                contentPane.getChildren().setAll(newContent); // Better than clear+add
//                //
//                AnchorPane.setTopAnchor(newContent, 0.0);
//                AnchorPane.setBottomAnchor(newContent, 0.0);
//                AnchorPane.setLeftAnchor(newContent, 0.0);
//                AnchorPane.setRightAnchor(newContent, 0.0);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Consider showing an alert to the user
//        }
//    }
    @Subscribe
    public void handleNavigationEvent(NavigationEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(event.getFxmlPath()));
            Parent view = loader.load();

            // Truyền dữ liệu nếu controller có phương thức setData
            Object controller = loader.getController();
            if (controller instanceof BookingFormController) {
                ((DataReceivable) controller).setData(event.getData());
                currentController = controller;
                currentFxml = "/fxml/BookingForm.fxml";
            }
            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Interface để nhận dữ liệu
    public interface DataReceivable {
        void setData(Object data);
    }

    @Subscribe
    public void handleConfirmDialog(ConfirmDialogEvent event) {
        Platform.runLater(() -> ConfirmDialog.show(
                event.getMessage(),
                event.getOnConfirm(),
                event.getOnCancel(),
                contentPane,
                event.getMessSucAndFail()
        ));
    }

    @Subscribe
    public void handleToastEvent(ToastEvent event) {
        Platform.runLater(() -> ConfirmDialog.showToast(contentPane,event.getMessage(), event.getType()));
    }
    // Hủy đăng ký khi controller bị hủy
    public void shutdown() {
        EventBusManager.unregister(this);
    }
    /**
     * Khởi tạo dữ liệu cho MainController sau khi đăng nhập thành công
     * @param taiKhoan Thông tin tài khoản đã đăng nhập
     */
    public void initData(TaiKhoan taiKhoan) {
        // Lưu thông tin người dùng vào SessionManager (đã được thực hiện trong LoginController)

        // Cập nhật giao diện với thông tin người dùng
        if (taiKhoan != null && taiKhoan.getNhanVien() != null) {
            userNameLabel.setText(taiKhoan.getNhanVien().getTenNhanVien());

            // Cập nhật tên loại nhân viên
            if (taiKhoan.getNhanVien().getLoaiNhanVien() != null) {
                String tenLoaiNhanVien = taiKhoan.getNhanVien().getLoaiNhanVien().getTenLoaiNhanVien();
                userRoleLabel.setText(tenLoaiNhanVien);
                System.out.println("Đã cập nhật tên loại nhân viên: " + tenLoaiNhanVien);
            } else {
                userRoleLabel.setText("Chưa xác định");
            }

            // Cập nhật avatar nếu có
            // TODO: Implement avatar loading if needed

            // Cập nhật quyền truy cập dựa trên loại nhân viên
            updateAccessPermissions(taiKhoan);
        }

        System.out.println("Đã khởi tạo dữ liệu cho người dùng: " +
                (taiKhoan != null ? taiKhoan.getNhanVien().getTenNhanVien() : "null"));
    }

    /**
     * Cập nhật quyền truy cập dựa trên loại nhân viên
     * @param taiKhoan Thông tin tài khoản đã đăng nhập
     */
    private void updateAccessPermissions(TaiKhoan taiKhoan) {
        if (taiKhoan != null && taiKhoan.getNhanVien() != null && taiKhoan.getNhanVien().getLoaiNhanVien() != null) {
            String loaiNhanVien = taiKhoan.getNhanVien().getLoaiNhanVien().getTenLoaiNhanVien();

            // Lấy mã loại nhân viên để so sánh chính xác
            String maLoaiNhanVien = taiKhoan.getNhanVien().getLoaiNhanVien().getMaLoaiNhanVien();

            // In ra thông tin chi tiết về loại nhân viên
            System.out.println("Mã loại nhân viên: " + maLoaiNhanVien);
            System.out.println("Tên loại nhân viên: " + loaiNhanVien);

            // Mã loại nhân viên theo database:
            // LNV01: Nhân viên quản lý
            // LNV02: Nhân viên lễ tân
            // LNV03: Nhân viên buồng phòng
            boolean isManager = false;

            // Kiểm tra dựa trên mã loại nhân viên (chính xác hơn)
            if (maLoaiNhanVien != null) {
                // Chỉ LNV01 (Nhân viên quản lý) mới có quyền quản lý
                isManager = maLoaiNhanVien.equals("LNV01");
            }

            // Nếu không có mã loại nhân viên hoặc kiểm tra mã không thành công, thử kiểm tra bằng tên
            if (!isManager && loaiNhanVien != null) {
                // Chuẩn hóa chuỗi để tránh các vấn đề về ký tự đặc biệt
                String normalizedName = loaiNhanVien.toLowerCase()
                                                   .replace(" ", "")
                                                   .replace("-", "")
                                                   .replace("_", "");

                // Kiểm tra xem tên có chứa "nhân viên quản lý" hay không
                isManager = normalizedName.contains("nhânviênquảnlý") ||
                           normalizedName.contains("nhanvienquanly") ||
                           normalizedName.contains("quảnlý") ||
                           normalizedName.contains("quanly");

                System.out.println("Tên chuẩn hóa: '" + normalizedName + "', Kết quả kiểm tra: " + isManager);
            }

            // In ra kết quả cuối cùng
            System.out.println("Kết quả kiểm tra là quản lý: " + isManager);
            System.out.println("Phân quyền: " + (isManager ? "Có quyền quản lý" : "Không có quyền quản lý"));

            // Cập nhật trạng thái các nút quản lý
            // Nếu là quản lý: có quyền truy cập tất cả chức năng (không bị vô hiệu hóa)
            // Nếu không phải quản lý: không có quyền truy cập các chức năng quản lý (bị vô hiệu hóa)

            // Quản lý nhân viên - chỉ quản lý mới có quyền
            staffManagementButton.setVisible(isManager);
            staffManagementButton.setManaged(isManager);
            staffManagementButton.setDisable(!isManager);

            // Quản lý dịch vụ - chỉ quản lý mới có quyền
            serviceManagementButton.setVisible(isManager);
            serviceManagementButton.setManaged(isManager);
            serviceManagementButton.setDisable(!isManager);

            // Quản lý khách hàng - chỉ quản lý mới có quyền
            customerManagementButton.setVisible(isManager);
            customerManagementButton.setManaged(isManager);
            customerManagementButton.setDisable(!isManager);

            // Quản lý phòng - chỉ quản lý mới có quyền
            roomManagementButton.setVisible(isManager);
            roomManagementButton.setManaged(isManager);
            roomManagementButton.setDisable(!isManager);

            // Quản lý kho - chỉ quản lý mới có quyền
            inventoryManagementButton.setVisible(isManager);
            inventoryManagementButton.setManaged(isManager);
            inventoryManagementButton.setDisable(!isManager);

            // Quản lý khuyến mãi - chỉ quản lý mới có quyền
            discountManagementButton.setVisible(isManager);
            discountManagementButton.setManaged(isManager);
            discountManagementButton.setDisable(!isManager);

            // Báo cáo - chỉ quản lý mới có quyền
            reportsButton.setVisible(isManager);
            reportsButton.setManaged(isManager);
            reportsButton.setDisable(!isManager);

            // Các quyền khác có thể được cấu hình tương tự
        }
    }

    /**
     * Xử lý đăng xuất khi người dùng nhấn nút đăng xuất
     */
    @FXML
    public void logout() {
        try {
            // Hiển thị hộp thoại xác nhận
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Xác nhận đăng xuất");
            confirmAlert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
            confirmAlert.setContentText("Tất cả các thay đổi chưa lưu sẽ bị mất.");

            // Nếu người dùng xác nhận đăng xuất
            if (confirmAlert.showAndWait().get().getButtonData().isDefaultButton()) {
                // Xóa thông tin phiên đăng nhập
                SessionManager.getInstance().clearSession();

                // Tải giao diện đăng nhập
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                Parent loginRoot = loader.load();
                Scene loginScene = new Scene(loginRoot);

                // Lấy stage hiện tại
                Stage currentStage = (Stage) menuPane.getScene().getWindow();

                // Tạo stage mới cho màn hình đăng nhập
                Stage loginStage = new Stage();
                loginStage.setScene(loginScene);
                loginStage.setTitle("Đăng nhập - Quản lý khách sạn");

                // Đặt kích thước cố định cho màn hình đăng nhập
                loginStage.setWidth(800);
                loginStage.setHeight(600);
                loginStage.setResizable(false);
                loginStage.centerOnScreen();

                // Hiển thị màn hình đăng nhập
                loginStage.show();

                // Đóng màn hình hiện tại
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

            // Hiển thị thông báo lỗi
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText("Không thể đăng xuất");
            errorAlert.setContentText("Có lỗi xảy ra khi đăng xuất: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
