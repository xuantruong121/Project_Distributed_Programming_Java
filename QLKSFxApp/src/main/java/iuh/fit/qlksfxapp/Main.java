package iuh.fit.qlksfxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("QLKS Fx App");
        stage.setScene(scene);
        stage.setFullScreen(true); //Fullscreen mode
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}