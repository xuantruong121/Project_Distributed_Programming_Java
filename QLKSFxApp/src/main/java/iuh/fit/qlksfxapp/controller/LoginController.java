package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.DAO.TaiKhoanDAO;
import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import iuh.fit.qlksfxapp.service.RMIService;
import iuh.fit.qlksfxapp.util.PasswordHasher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        // Đặt sự kiện cho nút đăng nhập
        loginButton.setOnAction(event -> login());

        // Đặt sự kiện Enter cho các trường nhập liệu
        passwordField.setOnAction(event -> login());
        usernameField.setOnAction(event -> passwordField.requestFocus());

        // Xóa thông báo lỗi khi người dùng bắt đầu nhập
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> statusLabel.setText(""));
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> statusLabel.setText(""));
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Kiểm tra trường trống
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Vui lòng nhập đầy đủ thông tin đăng nhập");
            return;
        }

        // Hiển thị thông báo đang xử lý
        statusLabel.setText("Đang xử lý...");
        loginButton.setDisable(true);

        // Thực hiện đăng nhập trong một luồng riêng biệt
        new Thread(() -> {
            try {
                // Kiểm tra xem RMIService đã được khởi tạo thành công chưa
                RMIService rmiService = RMIService.getInstance();
                TaiKhoanDAO taiKhoanDAO = rmiService.getTaiKhoanDAO();

                if (taiKhoanDAO == null) {
                    throw new Exception("Không thể kết nối đến RMI Server. Vui lòng kiểm tra xem RMI Server đã được khởi động chưa.");
                }

                // Kiểm tra xem đang sử dụng triển khai cục bộ hay không
                boolean isUsingLocalImplementation = taiKhoanDAO instanceof iuh.fit.qlksfxapp.DAO.Impl.LocalTaiKhoanDAOImpl;
                if (isUsingLocalImplementation) {
                    Platform.runLater(() -> {
                        statusLabel.setText("Chế độ offline: Sử dụng tài khoản mẫu");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Chế độ offline");
                        alert.setHeaderText("Không thể kết nối đến RMI Server");
                        alert.setContentText("Hệ thống đang chạy ở chế độ offline. Bạn có thể đăng nhập bằng tài khoản mẫu:\n\nTên đăng nhập: admin\nMật khẩu: Admin@123\n\nhoặc\n\nTên đăng nhập: user\nMật khẩu: User@123");
                        alert.showAndWait();
                    });
                }

                // Gọi phương thức authenticate từ RMIService
                TaiKhoan taiKhoan = taiKhoanDAO.authenticate(username, password);

                Platform.runLater(() -> {
                    if (taiKhoan != null) {
                        // Đăng nhập thành công
                        statusLabel.setText("");
                        openMainWindow(taiKhoan);
                    } else {
                        // Đăng nhập thất bại
                        statusLabel.setText("Tên đăng nhập hoặc mật khẩu không đúng");
                        loginButton.setDisable(false);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Lỗi kết nối đến server");
                    loginButton.setDisable(false);
                    e.printStackTrace();
                    showErrorAlert("Lỗi kết nối", "Không thể kết nối đến server: " + e.getMessage());
                });
            }
        }).start();
    }

    private void openMainWindow(TaiKhoan taiKhoan) {
        try {
            // Lưu thông tin người dùng đã đăng nhập
            SessionManager.getInstance().setCurrentUser(taiKhoan);

            // Tải giao diện chính
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

            // Lấy controller của giao diện chính
            MainController mainController = loader.getController();
            mainController.initData(taiKhoan);
            // Tạo scene mới
            Scene scene = new Scene(root);

            // Lấy stage hiện tại và đóng nó sau khi mở stage mới
            Stage oldStage = (Stage) loginButton.getScene().getWindow();

            // Tạo stage mới
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Quản lý khách sạn - " + taiKhoan.getNhanVien().getTenNhanVien());

            // Cấu hình stage mới
            newStage.setResizable(false);
            newStage.setMinWidth(1024);
            newStage.setMinHeight(768);

            // Đặt vị trí cửa sổ ở giữa màn hình
            newStage.centerOnScreen();

            // Hiển thị stage mới
            newStage.show();

            // Tối đa hóa cửa sổ sau khi đã hiển thị
            newStage.setMaximized(true);

            // Đóng stage cũ
            oldStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi", "Không thể mở giao diện chính: " + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}