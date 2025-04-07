package GUI.Model;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.swing.FontIcon;
import util.GoogleDriveImageViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class ItemTrangThai extends JPanel {
    private  String title;
    public enum TYPE{
        DON_DAT_MOI,
        PHONG_SAP_DEN,
        PHONG_SAP_DI,
        PHONG_TRONG,
    }
    public Color[] color = {Color.decode("#28A745"), Color.decode("#FFC107"), Color.decode("#FD7E14"), Color.decode("#17A2B8")};
    public Font font = new Font("Arial", Font.PLAIN, 15);
    public ItemTrangThai(int current, int week, int yesterday, TYPE type) {
        Color borderColor = this.color[type.ordinal()];

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        // Upper panel (image + text)
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setOpaque(false);

        // Left part: Image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        try {
            JLabel imageLabel = new JLabel(new ImageIcon(getIconByType(type)));
            imagePanel.add(imageLabel);
        } catch (GeneralSecurityException | IOException e) {
            JLabel imageLabel = new JLabel(new ImageIcon(GoogleDriveImageViewer.getPlaceholderImage()));
            imagePanel.add(imageLabel);
            throw new RuntimeException(e);
        }

//        imagePanel.setPreferredSize(new Dimension(50, 50));
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(borderColor, 2)
        ));
        upperPanel.add(imagePanel, BorderLayout.WEST);

        // Right part: Text content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel countLabel = new JLabel(current + "");
        countLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel weekLabel = new JLabel("Tuần này "+week);
        weekLabel.setFont(font);

        textPanel.add(titleLabel);
        textPanel.add(countLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(weekLabel);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

        upperPanel.add(textPanel, BorderLayout.CENTER);

        // Lower panel (text + icon)
        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.setOpaque(false);
        lowerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel comparisonLabel = new JLabel("So với hôm qua");
        comparisonLabel.setFont(font);
        lowerPanel.add(comparisonLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        double percentage =0.0;
        if (yesterday != 0)
            percentage = (double) (current - yesterday) / yesterday * 100;
        JLabel percentageLabel = new JLabel();
        JLabel icon = new JLabel();
        if (percentage > 0) {
            percentageLabel.setText("+" + String.format("%.2f", percentage) + "%");
            percentageLabel.setForeground(Color.GREEN);
            icon.setIcon( FontIcon.of(AntDesignIconsOutlined.RISE, 20, Color.GREEN));
        } else if (percentage < 0) {
            percentageLabel.setText(String.format("%.2f", percentage) + "%");
            percentageLabel.setForeground(Color.RED);
            icon.setIcon( FontIcon.of(AntDesignIconsOutlined.FALL, 20, Color.RED));
        } else {
            percentageLabel.setText("0.00%");
            percentageLabel.setForeground(Color.BLACK);
        }
        percentageLabel.setFont(font);
        rightPanel.add(percentageLabel);
        rightPanel.add(icon);

        lowerPanel.add(rightPanel, BorderLayout.EAST);

        // Add panels to main panel
        add(upperPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(borderColor, 2), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }
    public Image getIconByType(TYPE type) throws GeneralSecurityException, IOException {
        return switch (type) {
            case DON_DAT_MOI -> {
                title = "Đơn đặt mới";
                yield GoogleDriveImageViewer.getImageByFileName("completed-task.png");
            }
            case PHONG_SAP_DEN -> {
                title = "Phòng sắp đến";
                yield GoogleDriveImageViewer.getImageByFileName("icons8-check-in-64.png");
            }
            case PHONG_SAP_DI -> {
                title = "Phòng sắp đi";
                yield GoogleDriveImageViewer.getImageByFileName("icons8-check-out-64.png");
            }
            case PHONG_TRONG -> {
                title = "Phòng trống";
                yield GoogleDriveImageViewer.getImageByFileName("bed.png");
            }
        };
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Order Summary Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.add(new ItemTrangThai(1, 2, 3, TYPE.DON_DAT_MOI));
        frame.setVisible(true);
    }
}
