package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import iuh.fit.qlksfxapp.service.RMIService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.rmi.RemoteException;

/**
 * Ví dụ về controller đăng nhập sử dụng RMIService
 * Đây chỉ là ví dụ, bạn cần điều chỉnh để phù hợp với ứng dụng của mình
 */
public class LoginExampleController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            TaiKhoan taiKhoan = RMIService.getInstance().getTaiKhoanDAO().authenticate(username, password);

            if (taiKhoan != null) {
                // Đăng nhập thành công
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Đăng nhập thành công");
                alert.setHeaderText(null);
                alert.setContentText("Chào mừng " + taiKhoan.getNhanVien().getTenNhanVien());
                alert.showAndWait();

                // Chuyển đến màn hình chính
                // mainApp.showMainView();
            } else {
                // Đăng nhập thất bại
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Đăng nhập thất bại");
                alert.setHeaderText(null);
                alert.setContentText("Tên đăng nhập hoặc mật khẩu không đúng!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể kết nối đến server: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
