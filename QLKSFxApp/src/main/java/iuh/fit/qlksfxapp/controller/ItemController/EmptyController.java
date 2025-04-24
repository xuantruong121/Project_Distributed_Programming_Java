package iuh.fit.qlksfxapp.controller.ItemController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class EmptyController {
    @FXML private Label messageLabel;
    @FXML private ImageView imageEmpty;
    public void setMessage(String message) {
        messageLabel.setText(message);
    }
    public void setImage(Image image) {
        // Có thể thêm logic để thay đổi image nếu cần
        imageEmpty.setImage(image);
    }
}
