package nhap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThuNhoMenu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ResizableMenuExample::new);
    }
}
 class ResizableMenuExample extends JFrame {
     private JPanel menuPanel;
     private int menuWidth = 300; // Độ rộng ban đầu của menu
     private int minWidth = 50;   // Độ rộng nhỏ nhất cho phép
     private int maxWidth = 500;  // Độ rộng lớn nhất cho phép
     private boolean isResizing = false; // Cờ để xác định xem có đang resize hay không
     private static final int RESIZE_MARGIN = 5;
    public ResizableMenuExample() {
        setTitle("Resizable Menu Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo menu bên phải
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(new JLabel("Menu Item 1"));
        menuPanel.add(new JLabel("Menu Item 2 so long to text reponsive"));
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setPreferredSize(new Dimension(menuWidth, getHeight()));

        // Thêm xử lý sự kiện thu nhỏ bằng chuột
        MouseAdapter resizeHandler = new MouseAdapter() {
            private Point initialPoint;

            @Override
            public void mouseMoved(MouseEvent e) {
                // Nếu chuột di chuyển vào mép trái của menu (khoảng cách RESIZE_MARGIN)
                if (e.getX() >= menuPanel.getWidth() - RESIZE_MARGIN) {
                    menuPanel.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)); // Đặt con trỏ chuột phù hợp cho cạnh trái
                } else {
                    menuPanel.setCursor(Cursor.getDefaultCursor()); // Đặt lại con trỏ mặc định
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() >= menuPanel.getWidth() - RESIZE_MARGIN) {
                    isResizing = true; // Bắt đầu resize
                    initialPoint = e.getPoint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isResizing) {
                    // Tính toán độ rộng mới của menu
                    int newWidth =  e.getXOnScreen();

                    // Giới hạn độ rộng trong khoảng minWidth và maxWidth
                    if (newWidth >= minWidth && newWidth <= maxWidth) {
                        menuWidth = newWidth;
                        menuPanel.setPreferredSize(new Dimension(menuWidth, getHeight()));
                        menuPanel.revalidate(); // Cập nhật lại kích thước
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isResizing = false; // Kết thúc resize
            }
        };

        // Thêm sự kiện chuột vào menu
        menuPanel.addMouseListener(resizeHandler);
        menuPanel.addMouseMotionListener(resizeHandler);

        // Thêm menu vào JFrame
        add(menuPanel, BorderLayout.WEST);

        setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(ResizableMenuExample::new);
//    }
}
