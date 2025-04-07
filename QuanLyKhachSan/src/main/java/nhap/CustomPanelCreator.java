package nhap;

import javax.swing.*;
import java.awt.*;

public class CustomPanelCreator {

    /**
     * Tạo một JPanel như hình đã mô tả
     *
     * @param number Số hiển thị trong khung vuông bên trái.
     * @param text   Văn bản hiển thị bên phải.
     * @return JPanel được tạo với bố cục đúng yêu cầu.
     */
    public static JPanel createCustomPanel(String number, String text) {
        // Tạo JPanel chính
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Sắp xếp ngang, cách nhau 10px
        panel.setBackground(Color.WHITE); // Màu nền của toàn bộ JPanel

        // Phần số (trong khung vuông)
        JLabel numberLabel = new JLabel(number, SwingConstants.CENTER);
        numberLabel.setPreferredSize(new Dimension(30, 30));
        numberLabel.setOpaque(true);
        numberLabel.setBackground(new Color(0, 188, 212)); // Màu xanh nhạt
        numberLabel.setForeground(Color.WHITE); // Màu chữ trắng
        numberLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Font chữ to, đậm

        // Bo tròn khung vuông
        numberLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true));

        // Phần văn bản
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setForeground(Color.GRAY); // Màu chữ xám

        // Thêm các thành phần vào panel
        panel.add(numberLabel);
        panel.add(textLabel);

        return panel;
    }

    public static void main(String[] args) {
        // Tạo một frame để hiển thị panel
        JFrame frame = new JFrame("Custom Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());

        // Sử dụng hàm để tạo panel
        JPanel customPanel = createCustomPanel("2", "Trống");

        // Thêm panel vào frame
        frame.add(customPanel);
        frame.setVisible(true);
    }
}
