package iuh.fit.qlksfxapp.controller.EventBus;

import iuh.fit.qlksfxapp.controller.ItemController.EmptyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class EmptyViewManager {
    private static Node emptyView;
    private static EmptyController controller;

    public static Node getEmptyView() {
        if (emptyView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(EmptyViewManager.class.getResource("/fxml/util/Empty.fxml"));
                emptyView = loader.load();
                controller = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emptyView;
    }

    public static void setMessage(String message) {
        if (controller != null) {
            controller.setMessage(message);
        }
    }
}
