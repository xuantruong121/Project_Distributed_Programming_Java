//package iuh.fit.qlksfxapp.util;
//
//import iuh.fit.qlksfxapp.controller.EventBus.ToastEvent;
//import iuh.fit.qlksfxapp.controller.ItemController.ConfirmDialogController;
//import iuh.fit.qlksfxapp.controller.ItemController.DialogAddBookingController;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.function.Supplier;
//
//public class DialogAddBookingDetail {
////    public static void show(Supplier<Boolean> onConfirm, Runnable onCancel,
////                            AnchorPane toastParent, List<String> message2) {
////        try {
////            FXMLLoader loader = new FXMLLoader(DialogAddBookingDetail.class.getResource("/fxml/util/DialogAddBookingDetail.fxml"));
////            Parent root = loader.load();
////
////            DialogAddBookingController controller = loader.getController();
////
////            Stage dialogStage = new Stage();
////            controller.setDialogStage(dialogStage);
////
////            controller.setOnResult(result -> {
////                if (result && onConfirm != null) {
////                    boolean success = onConfirm.get();
////                    if (toastParent != null) {
////                        showToast(toastParent,
////                                success ? message2.get(0) : message2.get(1),
////                                success ? ToastEvent.ToastType.SUCCESS : ToastEvent.ToastType.ERROR
////                        );
////                    }
////                } else if (!result && onCancel != null) {
////                    onCancel.run();
////                }
////            });
////
////            dialogStage.initModality(Modality.APPLICATION_MODAL);
////            dialogStage.setTitle("Xác nhận");
////            dialogStage.setIconified(false);
////            dialogStage.setScene(new Scene(root));
////            dialogStage.setResizable(false);
////
////            // Căn giữa Dialog trên màn hình cha
////            if (toastParent != null && toastParent.getScene() != null) {
////                Stage parentStage = (Stage) toastParent.getScene().getWindow();
////                dialogStage.setOnShown(event -> {
////                    double centerX = parentStage.getX() + (parentStage.getWidth() - dialogStage.getWidth()) / 2;
////                    double centerY = parentStage.getY() + (parentStage.getHeight() - dialogStage.getHeight()) / 2;
////                    dialogStage.setX(centerX);
////                    dialogStage.setY(centerY);
////                });
////            }
////
////            dialogStage.showAndWait();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//
//}
