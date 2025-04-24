package iuh.fit.qlksfxapp.controller.ItemController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class ConfirmDialogController {
    @FXML private Label messageLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    private Stage dialogStage;
    private Consumer<Boolean> resultHandler; // Xử lý kết quả
    public  void setMessage(String message) {
        messageLabel.setText(message);
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setOnResult(Consumer<Boolean> handler) {
        this.resultHandler = handler;
    }
    @FXML
    private void handleConfirm() {
        if (resultHandler != null) {
            resultHandler.accept(true); // Người dùng xác nhận
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        if (resultHandler != null) {
            resultHandler.accept(false); // Người dùng huỷ
        }
        dialogStage.close();
    }
}
