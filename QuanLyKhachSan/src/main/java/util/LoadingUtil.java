package util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class LoadingUtil {
    private static JDialog loadingDialog;

    public static void runWithLoading(Runnable task, JFrame frame) {
        Thread workerThread = new Thread(() -> {
            Timer timer = new Timer(2000, e -> showLoadingDialog(frame));
            timer.setRepeats(false);
            timer.start();
            try {
                task.run();  // chạy công việc chính
            } finally {
                timer.stop();
                hideLoadingDialog();
            }
        });
        workerThread.start();
    }
    private static void showLoadingDialog(JFrame frame) {
        SwingUtilities.invokeLater(() -> {
            loadingDialog = new LoadingDialog(frame);
            loadingDialog.setVisible(true);

        });
    }

    private static void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isVisible()) {
            SwingUtilities.invokeLater(() -> loadingDialog.dispose());
        }
    }
}

class SpinnerComponent extends JComponent {
    private int angle = 0;
    private final int DELAY = 50;
    private Timer timer;

    public SpinnerComponent() {
        timer = new Timer(DELAY, e -> {
            angle = (angle + 20) % 360;
            repaint();
        });
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 20;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Draw spinning arc
        g2d.setStroke(new BasicStroke(10));
        g2d.setColor(Color.BLUE);
        g2d.draw(new Arc2D.Double(x, y, size, size, angle, 150, Arc2D.OPEN));
    }
    public void stop() {
        timer.stop();
    }
}
class LoadingDialog extends JDialog {
    public LoadingDialog(JFrame parent) {
        super(parent, true); // Modal dialog
        // Remove title bar and close/minimize/maximize buttons
        setUndecorated(true);
        Container parentContentPane = parent.getContentPane();
        setSize(parentContentPane.getWidth(), parentContentPane.getHeight());

//        setSize(parent.getWidth(), parent.getHeight());
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 128)); // 128 = 50% of 255
        setOpacity(0.8f); // Set opacity to 80%
        // IMPORTANT: The content pane must be non-opaque for transparency to work
        JPanel contentPane = new JPanel(new BorderLayout());
        // Create spinner component with fixed size
        SpinnerComponent spinner = new SpinnerComponent();
        spinner.setPreferredSize(new Dimension(70, 70));

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.Y_AXIS));
        spinnerPanel.add(Box.createVerticalGlue());
        spinnerPanel.add(spinner);
        spinnerPanel.add(Box.createVerticalGlue());
        // Add components to main panel
        contentPane.add(spinnerPanel);

        JLabel text = new JLabel("Processing, please wait...", JLabel.CENTER);
        contentPane.add(text, BorderLayout.SOUTH);

        // Add main panel to dialog
        add(contentPane);
    }

    // Call this when loading is complete
    public void close() {
        setVisible(false);
        dispose();
    }
};