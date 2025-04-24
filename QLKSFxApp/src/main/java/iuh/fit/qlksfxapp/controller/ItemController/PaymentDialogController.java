package iuh.fit.qlksfxapp.controller.ItemController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.text.NumberFormat;
import java.util.Locale;

public class PaymentDialogController {
    @FXML
    private Label lblAmountDue;

    @FXML
    private TextField txtPaymentAmount;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    private double amountDue;
    private boolean paymentConfirmed = false;

    public void initialize() {
        // Format số cho TextField để chỉ nhập số
        txtPaymentAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtPaymentAmount.setText(oldValue);
            }
        });
    }

    public void setAmountDue(double amount) {
        this.amountDue = amount;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        lblAmountDue.setText(currencyFormat.format(amount));
    }

    @FXML
    private void handleConfirm() {
        try {
            double paymentAmount = Double.parseDouble(txtPaymentAmount.getText().trim());
            if (paymentAmount >= amountDue) {
                paymentConfirmed = true;
                Stage stage = (Stage) btnConfirm.getScene().getWindow();
                stage.close();
            } else {
                // Hiển thị thông báo nếu số tiền không đủ
                txtPaymentAmount.setStyle("-fx-border-color: red;");
                btnConfirm.setDisable(true);

                // Timer để xóa style sau 2 giây
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> {
                            txtPaymentAmount.setStyle("");
                            btnConfirm.setDisable(false);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (NumberFormatException e) {
            txtPaymentAmount.setStyle("-fx-border-color: red;");
        }
    }

    @FXML
    private void handleCancel() {
        paymentConfirmed = false;
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }

    public double getPaymentAmount() {
        try {
            return Double.parseDouble(txtPaymentAmount.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}