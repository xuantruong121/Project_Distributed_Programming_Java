package GUI;

import lombok.Getter;
import lombok.Setter;
import util.ColorAndFont;
import util.GoogleDriveImageViewer;
import util.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
@Getter
@Setter
public class Header extends JPanel {
    private BufferedImage bellImage=null;
    private boolean hasNewNotification = true; // Flag for new notifications
    public Header() throws GeneralSecurityException, IOException {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        // Center: Greeting label
        JLabel greetingLabel = new JLabel("Chúc Mai Đức Trường có một ngày tuyệt vời!");
        greetingLabel.setFont(ColorAndFont.TEXT_FONT);
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(greetingLabel, BorderLayout.CENTER);

        // Right: Panel for Image, Title, Name, and Notification Button
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        // Circular Image Placeholder
        JPanel imagePanel = new JPanel();
        try {
            BufferedImage resizeImage = ImageResizer.resizeImage(GoogleDriveImageViewer.getImageByFileName("man.png"), 50, 50);
            JLabel imageLabel = new JLabel(new ImageIcon(resizeImage));
            imagePanel.add(imageLabel);
        } catch (IOException | GeneralSecurityException e) {
            JLabel imageLabel = new JLabel(new ImageIcon(GoogleDriveImageViewer.getPlaceholderImage()));
            imagePanel.add(imageLabel);
            throw new RuntimeException(e);
        }
        imagePanel.setBackground(Color.WHITE);
        // Title and Name
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Nhân viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setForeground(ColorAndFont.TEXT_COLOR_PURPLE);

        JLabel nameLabel = new JLabel("Mai Đức Trường");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        textPanel.add(titleLabel);
        textPanel.add(nameLabel);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

        // Profile panel (Image + Text)
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.setOpaque(false);
        profilePanel.add(imagePanel, BorderLayout.WEST);
        profilePanel.add(textPanel, BorderLayout.CENTER);
        profilePanel.setBorder( BorderFactory.createEmptyBorder(0, 10, 0, 50));
        // Create the popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem viewProfileItem = new JMenuItem("View Profile");
        JMenuItem exitItem = new JMenuItem("Exit");

        popupMenu.add(viewProfileItem);
        popupMenu.add(exitItem);

        // Add action listeners for menu items
        viewProfileItem.addActionListener(e -> JOptionPane.showMessageDialog(profilePanel, "View Profile clicked!"));

        exitItem.addActionListener(e -> System.exit(0));

        profilePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) { // Left-click
                    Dimension preferredSize = new Dimension(profilePanel.getWidth()-profilePanel.getBorder().getBorderInsets(profilePanel).right-imagePanel.getWidth(), viewProfileItem.getPreferredSize().height);
                    viewProfileItem.setPreferredSize(preferredSize);
                    exitItem.setPreferredSize(preferredSize);
                    int x =imagePanel.getWidth()+10;//center (profilePanel.getWidth() - popupMenu.getPreferredSize().width) / 2;
                    int y = profilePanel.getHeight();
                    popupMenu.show(profilePanel, x, y);
                }
            }
        });
        // Add profile panel to the right panel
        rightPanel.add(profilePanel, BorderLayout.CENTER);

        // Notification button with custom painting for the red dot
        BufferedImage notificationImage = ImageResizer.resizeImage(new ImageIcon(Objects.requireNonNull(GoogleDriveImageViewer.getImageByFileName("notification.png"))).getImage(), 50, 50);
        JButton notificationButton = new JButton(new ImageIcon(notificationImage));

        notificationButton.setBackground(Color.WHITE);
        notificationButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        rightPanel.add(notificationButton, BorderLayout.EAST);
        notificationButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hasNewNotification = false;
                try {
                    if(bellImage==null)
                        bellImage = ImageResizer.resizeImage(new ImageIcon(Objects.requireNonNull(GoogleDriveImageViewer.getImageByFileName("bell.png"))).getImage(), 50, 50);
                    notificationButton.setIcon(new ImageIcon(bellImage));
                } catch (IOException | GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
                notificationButton.repaint();
            }
        });

        // Add the right panel to the main panel
        add(rightPanel, BorderLayout.EAST);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Header Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 150);
        frame.setLayout(new BorderLayout());
        frame.add(new JLabel("Main Content", SwingConstants.CENTER), BorderLayout.CENTER);
        try {
            frame.add(new Header(), BorderLayout.NORTH);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        frame.setVisible(true);
    }
}
