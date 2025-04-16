package iuh.fit.qlksfxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Main.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),
//                screenBounds.getWidth(),
//                screenBounds.getHeight());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Main.fxml")));
        Scene scene = new Scene(root);

        primaryStage.setTitle("QLKS Fx App");
        primaryStage.setScene(scene);

        // Set window to maximize but keep the frame/title bar
        primaryStage.setMaximized(true);

        // Add a keyboard shortcut for toggling between maximized and normal
        scene.setOnKeyPressed(event -> {
            if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
                primaryStage.setMaximized(!primaryStage.isMaximized());
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
