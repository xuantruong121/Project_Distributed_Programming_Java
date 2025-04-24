package iuh.fit.qlksfxapp;

import iuh.fit.qlksfxapp.service.RMIService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Khởi tạo RMIService
            initializeRMIService();

            // Tải SplashScreen trước
            Parent splashRoot = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/fxml/SplashScreen.fxml")));
            Scene splashScene = new Scene(splashRoot);

            primaryStage.setTitle("Đang khởi động...");
            primaryStage.setScene(splashScene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

            // SplashScreenController sẽ tự động chuyển sang giao diện chính sau khi hoàn tất
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khởi động", "Không thể khởi động ứng dụng: " + e.getMessage());
        }
    }
    private void initializeRMIService() {
        try {
            // Khởi tạo RMIService
            RMIService.getInstance();
            System.out.println("RMIService initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to initialize RMIService: " + e.getMessage());
            // Không hiển thị alert ở đây vì JavaFX chưa được khởi tạo hoàn toàn
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        // Đặt thuộc tính bảo mật cho RMI
        String projectDir = System.getProperty("user.dir");
        System.setProperty("java.security.policy", projectDir + "/rmi.policy");

        launch();
    }
}
