package iuh.fit.qlksfxapp.util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StyleUtil {
    public static final List<String> TYPE_OF_ROOM_COLOR = new ArrayList<>(Arrays.asList(
            "#ede5cd",// standard
            "#8cd8fa",// superior
            "#ffd658"// deluxe
    ));

    // Hàm áp dụng style cho bất kỳ Node nào (Label, Button, v.v.)
    public static void applyStyleToNode(Node node, String backgroundColor, String borderColor, double borderRadius, double borderWidth, double fontSize) {
        node.setStyle(
                "-fx-background-color: " + backgroundColor + "; " +
                        "-fx-text-fill: black; " +  // Màu chu
                        "-fx-font-weight: bold;" + // Chữ đậm
                        "-fx-border-radius: " + borderRadius + "; " +  // Bo góc
                        "-fx-background-radius: " + borderRadius + "; " +  // Thêm dòng này
                        "-fx-border-color: " + borderColor + "; " +  // Màu border
                        "-fx-border-width: " + borderWidth + "; " +  // Độ dày của border
                        "-fx-font-size: " + fontSize + "px; " +  // Kích thước font
                        "-fx-padding: 8 12 8 12;" + // Khoảng đệm
                        "-fx-background-insets: " + borderWidth + ";" // Đẩy background vào trong để tránh border chồng lên
        );
    }

//    public static void main(String[] args) {
//        // Tạo Label và Button
//        Label label = new Label("Standard Room");
//        Button button = new Button("Book Now");
//
//        // Áp dụng style cho Label
//        applyStyleToNode(label, "#269EB6", "#1A7F94", 15, 2, 16);
//
//        // Áp dụng style cho Button
//        applyStyleToNode(button, "#FF6347", "#FF4500", 10, 3, 14);
//
//        // Sau khi áp dụng style, bạn có thể thêm chúng vào layout, ví dụ như VBox hoặc HBox
//    }
}
