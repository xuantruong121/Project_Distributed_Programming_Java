package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.controller.EventBus.ToastEvent;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ToastController {

    @FXML private Label iconLabel;
    @FXML private Label messageLabel;
    @FXML private AnchorPane toastRoot;

    public void showToast(String message, ToastEvent.ToastType type) {
        Platform.runLater(() -> {
            messageLabel.setText(message);
            if (type == ToastEvent.ToastType.SUCCESS) {
                iconLabel.setText("✔"); // biểu tượng xanh
                iconLabel.setStyle("-fx-text-fill:#4CAF50; -fx-font-size: 20px;");
                toastRoot.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 8; -fx-border-width: 2;");
            } else {
                iconLabel.setText("✖"); // biểu tượng đỏ
                iconLabel.setStyle("-fx-text-fill:#f44336;  -fx-font-size: 20px;");
                toastRoot.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #f44336; -fx-border-radius: 8; -fx-border-width: 2;");
            }
            // Fade in
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastRoot);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            // Fade out sau vài giây
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setDelay(Duration.seconds(2.5));

            fadeIn.setOnFinished(event -> fadeOut.play());
            fadeIn.play();
        });
    }
}
