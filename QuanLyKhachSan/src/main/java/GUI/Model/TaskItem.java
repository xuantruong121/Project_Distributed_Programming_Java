package GUI.Model;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.swing.FontIcon;
import util.GoogleDriveImageViewer;
import util.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

public class TaskItem extends  JPanel{
    private Date date;
    private String taskType;
    private String details;
    private boolean isCompleted;
    public TaskItem(Date date, String taskType, String details, boolean isCompleted) {
        this.date = date;
        this.taskType = taskType;
        this.details = details;
        this.isCompleted = isCompleted;
        // Panel chứa toàn bộ task
        setLayout(new BorderLayout(10, 10)); // Khoảng cách giữa các thành phần
//        taskPanel.setBackground(new Color(100, 110, 120)); // Màu nền của task
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding cho task

        // Icon trạng thái ở bên trái
        JLabel statusIcon = new JLabel();
        if (isCompleted) {
            try {
                Image resizedIcon = ImageResizer.resizeImage(GoogleDriveImageViewer.getImageByFileName("checked.png"),30,30);
                statusIcon.setIcon(new ImageIcon(resizedIcon)); // Đổi đường dẫn theo hình của bạn
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Image resizedIcon = ImageResizer.resizeImage(GoogleDriveImageViewer.getImageByFileName("unchecked.png"),30,30);
                statusIcon.setIcon(new ImageIcon(resizedIcon)); // Đổi đường dẫn theo hình của bạn
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            };
        }
        add(statusIcon, BorderLayout.WEST);

        // Panel bên phải chứa thông tin task
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(100, 110, 120)); // Đồng bộ màu với task
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding cho infoPanel
        // Phần phía trên: ngày và loại task
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(100, 110, 120));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Padding cho headerPanel
        JLabel dateLabel = new JLabel(date.toString());
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton taskTypeButton = new JButton(taskType);
        taskTypeButton.setFont(new Font("Arial", Font.BOLD, 12));
        taskTypeButton.setBackground(Color.WHITE);
        taskTypeButton.setForeground(Color.BLACK);
        taskTypeButton.setFocusPainted(false);
        taskTypeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Làm nút trông gọn gàng hơn

        headerPanel.add(dateLabel, BorderLayout.WEST);
        headerPanel.add(taskTypeButton, BorderLayout.EAST);

        // Phần thông tin chi tiết ở giữa
        JLabel detailsLabel = new JLabel("<html>" + details + "</html>");
        detailsLabel.setForeground(Color.WHITE);
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsLabel.setVerticalAlignment(SwingConstants.TOP);

        // Thêm header và thông tin chi tiết vào infoPanel
        infoPanel.add(headerPanel, BorderLayout.NORTH);
        infoPanel.add(detailsLabel, BorderLayout.CENTER);

        // Thêm infoPanel vào taskPanel
        add(infoPanel, BorderLayout.CENTER);
//        return taskPanel;
    }
//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setTitle("Task List Example");
//        frame.setSize(400, 600);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JPanel contentPane = new JPanel();
//        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//        contentPane.setBackground(Color.WHITE);
//
//        // Add sample tasks
//        contentPane.add(createTaskPanel("01/01/2025", "Dọn phòng", "Phòng STA-1001 đã rời đi vào lúc 12:02 hãy dọn dẹp phòng này để đón khách mới vào 14:00", true));
//        contentPane.add(createTaskPanel("01/01/2025", "Dọn phòng", "Phòng STA-1002 đã rời đi vào lúc 12:02 hãy dọn dẹp phòng này để đón khách mới vào 14:00", false));
//        frame.setContentPane(contentPane);
//        frame.setVisible(true);
//    }
}
