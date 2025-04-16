package iuh.fit.qlksfxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tải SplashScreen trước
        Parent splashRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SplashScreen.fxml")));
        Scene splashScene = new Scene(splashRoot);

        primaryStage.setTitle("Đang khởi động...");
        primaryStage.setScene(splashScene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();

        // SplashScreenController sẽ tự động chuyển sang giao diện chính sau khi hoàn tất
    }

    public static void main(String[] args) {
        launch();
    }
}
