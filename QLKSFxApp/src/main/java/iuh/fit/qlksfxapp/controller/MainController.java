package iuh.fit.qlksfxapp.controller;

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

import java.io.IOException;

import java.net.URL;

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
    private Button homeButton;
    @FXML
    private Button bookingManagementButton;
    @FXML
    private VBox bookingManagementSubMenu;
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

    private boolean isSidebarExpanded = true;
    private boolean isRoomManagementMenuOpen = false;
    private boolean isStatisticsMenuOpen = false;
    private Button expandButton; // Button to expand the sidebar when collapsed

    // Track the active menu button for highlighting
    private Button activeMenuButton;

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
        updateContent("Trang chủ");
        loadDashboardContent();
    }

    private void loadDashboardContent() {
        // Here you would implement loading the dashboard components
        // such as room statistics, booking information, etc.
        System.out.println("Loading dashboard content");
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
                URL resource = getClass().getResource(fxmlFile);
                System.out.println("Resource URL: " + resource);
                if (resource == null) {
                    throw new IOException("Cannot find resource: " + fxmlFile);
                }

                FXMLLoader loader = new FXMLLoader(resource);
                Parent newContent = loader.load();
                contentPane.getChildren().setAll(newContent); // Better than clear+add
                //
                AnchorPane.setTopAnchor(newContent, 0.0);
                AnchorPane.setBottomAnchor(newContent, 0.0);
                AnchorPane.setLeftAnchor(newContent, 0.0);
                AnchorPane.setRightAnchor(newContent, 0.0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Consider showing an alert to the user
        }
    }
}
