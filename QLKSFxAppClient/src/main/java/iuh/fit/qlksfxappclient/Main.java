package iuh.fit.qlksfxappclient;

import iuh.fit.qlksfxappclient.service.RMIService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Khởi tạo RMIService và kiểm tra kết nối
            if (!initializeRMIService()) {
                // Nếu kết nối thất bại, thoát ứng dụng
                System.exit(1);
                return;
            }

            // Tải FXML từ JAR file trong dependencies
            // Sử dụng đường dẫn chính xác đã tìm thấy trong JAR file
            URL fxmlUrl = findFXMLResource("SplashScreen.fxml");

            if (fxmlUrl == null) {
                throw new IOException("Không thể tìm thấy file SplashScreen.fxml trong classpath hoặc JAR file");
            }

            System.out.println("Loading FXML from: " + fxmlUrl);

            // Tải FXML trực tiếp từ JAR file
            System.out.println("Loading FXML from JAR: " + fxmlUrl);

            // Sử dụng ClassLoader của JAR file
            ClassLoader classLoader = Main.class.getClassLoader();

            // Tạo FXMLLoader với ClassLoader của JAR file
            FXMLLoader loader = new FXMLLoader();
            loader.setClassLoader(classLoader);
            loader.setLocation(fxmlUrl);

            // Thiết lập controller factory để tạo controller từ JAR file
            loader.setControllerFactory(controllerClass -> {
                try {
                    System.out.println("Creating controller: " + controllerClass.getName());
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    System.err.println("Error creating controller: " + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            });

            // In ra các controller được tìm thấy trong JAR
            System.out.println("Available controllers in JAR:");
            try {
                classLoader.loadClass("iuh.fit.qlksfxapp.controller.SplashScreenController");
                System.out.println("Found SplashScreenController in JAR");
            } catch (ClassNotFoundException e) {
                System.out.println("SplashScreenController not found in JAR: " + e.getMessage());
            }

            // Tải FXML
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("Quản lý khách sạn - Client");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khởi động", "Không thể khởi động ứng dụng: " + e.getMessage());
        }
    }

    /**
     * Tìm kiếm file FXML trong nhiều vị trí khác nhau
     * @param fxmlFileName Tên file FXML cần tìm
     * @return URL của file FXML hoặc null nếu không tìm thấy
     */
    private URL findFXMLResource(String fxmlFileName) {
        // Danh sách các đường dẫn có thể chứa file FXML
        String[] possiblePaths = {
            "fxml/" + fxmlFileName,
            "/fxml/" + fxmlFileName,
            "iuh/fit/qlksfxapp/fxml/" + fxmlFileName,
            "/iuh/fit/qlksfxapp/fxml/" + fxmlFileName,
            "iuh/fit/qlksfxappclient/fxml/" + fxmlFileName,
            "/iuh/fit/qlksfxappclient/fxml/" + fxmlFileName,
            fxmlFileName
        };

        // Thử từ nhiều ClassLoader khác nhau
        ClassLoader[] classLoaders = {
            Thread.currentThread().getContextClassLoader(),
            Main.class.getClassLoader(),
            ClassLoader.getSystemClassLoader()
        };

        URL fxmlUrl = null;

        // Thử từ từng ClassLoader và từng đường dẫn
        for (ClassLoader classLoader : classLoaders) {
            if (classLoader == null) continue;

            for (String path : possiblePaths) {
                fxmlUrl = classLoader.getResource(path);
                if (fxmlUrl != null) {
                    System.out.println("Found FXML at: " + path);
                    return fxmlUrl;
                }
            }
        }

        // Thử từ getResource của class
        for (String path : possiblePaths) {
            fxmlUrl = Main.class.getResource(path);
            if (fxmlUrl != null) {
                System.out.println("Found FXML using class.getResource at: " + path);
                return fxmlUrl;
            }
        }

        return null;
    }

    private boolean initializeRMIService() {
        try {
            // Đặt thuộc tính bảo mật cho RMI
            String projectDir = System.getProperty("user.dir");
            System.setProperty("java.security.policy", projectDir + "/rmi.policy");

            // Try to initialize RMI service
            try {
                RMIService service = RMIService.getInstance();
                System.out.println("RMIService initialized successfully");
                return true;
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.err.println("Failed to initialize RMIService: " + e.getMessage());

                // Show error and exit application
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi kết nối");
                alert.setHeaderText("Không thể kết nối đến máy chủ");
                alert.setContentText("Không thể kết nối đến máy chủ RMI. \n\n" +
                        "Vui lòng kiểm tra kết nối mạng và cấu hình máy chủ, sau đó khởi động lại ứng dụng.");
                alert.showAndWait();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error setting up RMI environment: " + e.getMessage());
            return false;
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
        launch(args);
    }
}