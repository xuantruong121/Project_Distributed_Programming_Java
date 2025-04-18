package iuh.fit.qlksfxapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenController {

    @FXML
    private ImageView loadingImageView;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressBar progressBar;

    private Timeline timeline;

    @FXML
    private void initialize() {
        // Kiểm tra và tải hình ảnh loading.gif
        try {
            InputStream inputStream = getClass().getResourceAsStream("/images/loading.gif");
            if (inputStream != null) {
                Image loadingImage = new Image(inputStream);
                loadingImageView.setImage(loadingImage);
            } else {
                System.err.println("Không thể tải hình ảnh loading.gif");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tải hình ảnh loading.gif: " + e.getMessage());
        }

        // Tạo timeline để cập nhật thanh tiến trình
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0))
        );
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(4), new KeyValue(progressBar.progressProperty(), 1))
        );

        // Khi timeline kết thúc, mở giao diện chính
        timeline.setOnFinished(event -> {
            // Đảm bảo rằng tất cả các công việc khởi tạo đã hoàn tất
            Platform.runLater(() -> {
                statusLabel.setText("Hoàn tất! Đang mở ứng dụng...");

                // Chờ thêm 500ms trước khi mở giao diện chính
                Timeline delayTimeline = new Timeline(new KeyFrame(Duration.millis(500), e -> openMainWindow()));
                delayTimeline.play();
            });
        });

        // Bắt đầu timeline
        timeline.play();

        // Cập nhật trạng thái
        updateStatus();

        // Ghi log
        System.out.println("SplashScreen đã được khởi tạo");
    }

    private void updateStatus() {
        // Cập nhật trạng thái dựa trên quá trình khởi động thực tế
        Thread thread = new Thread(() -> {
            try {
                // Bắt đầu với thông báo khởi động
                Platform.runLater(() -> statusLabel.setText("Đang khởi động..."));
                Thread.sleep(500);

                // Loading dashboard content
                Platform.runLater(() -> statusLabel.setText("Đang tải nội dung dashboard..."));
                Thread.sleep(800);

                // Hibernate initialization
                Platform.runLater(() -> statusLabel.setText("Đang khởi tạo Hibernate..."));
                Thread.sleep(1000);

                // Second-level cache
                Platform.runLater(() -> statusLabel.setText("Đang cấu hình cache..."));
                Thread.sleep(800);

                // Kết nối cơ sở dữ liệu
                Platform.runLater(() -> statusLabel.setText("Đang kết nối cơ sở dữ liệu..."));
                Thread.sleep(800);

                // Hoàn tất
                Platform.runLater(() -> statusLabel.setText("Đang chuẩn bị giao diện..."));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void openMainWindow() {
        try {
            // Tải giao diện chính
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

            // Tạo scene mới
            Scene scene = new Scene(root);

            // Lấy stage hiện tại
            Stage stage = (Stage) progressBar.getScene().getWindow();

            // Đặt scene mới cho stage
            stage.setScene(scene);
            stage.setTitle("Phần mềm Quản lý Khách sạn");

            // Cho phép thay đổi kích thước và đặt maximized
            stage.setResizable(true);
            stage.setMaximized(true);

            // Thêm phím tắt Alt+Enter để chuyển đổi giữa maximized và normal
            scene.setOnKeyPressed(event -> {
                if (event.isAltDown() && event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                    stage.setMaximized(!stage.isMaximized());
                }
            });

            // Hiển thị stage
            stage.show();

            // Ghi log
            System.out.println("Giao diện chính đã được tải và hiển thị ở chế độ maximized");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi mở giao diện chính: " + e.getMessage());
        }
    }
}
