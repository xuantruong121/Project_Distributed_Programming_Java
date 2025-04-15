package nhap;

import util.LoadingUtil;

import javax.swing.*;
import java.awt.*;

class TestLoading {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demo");
            JButton button = new JButton("Tải dữ liệu");
            button.setBackground(Color.RED);
            button.addActionListener(e -> {
                button.setEnabled(false);  // Disable button

                LoadingUtil.runWithLoading(() -> {
                try {
                    // Giả lập công việc tải dữ liệu
                    Thread.sleep(10000);
                    System.out.println("Load xong dữ liệu!");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        button.setEnabled(true);  // Enable button
                    });
                }
            }, frame);
            });

            frame.add(button, BorderLayout.CENTER);
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
