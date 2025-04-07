package GUI.Model;

import javax.swing.*;
import java.awt.*;

public class CompletedItem extends JPanel {
        // Custom rounded progress bar class
    private String label;
    private int completionValue;
        static class RoundedProgressBar extends JProgressBar {
            private int cornerRadius;

            public RoundedProgressBar(int cornerRadius) {
                this.cornerRadius = cornerRadius;
                setOpaque(false); // Make sure the background is transparent
                setBorder(BorderFactory.createEmptyBorder()); // Add padding
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint background with rounded corners
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

                // Paint progress bar with rounded corners
                int width = (int) (getWidth() * getPercentComplete());
                g2.setColor(getForeground());
                g2.fillRoundRect(0, 0, width, getHeight(), cornerRadius, cornerRadius);
            }
        }
    public CompletedItem(String label, int completionValue) {
            // Create a panel for the entire item
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            this.label=label;
            this.completionValue=completionValue;
            // Create the top label panel
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BorderLayout());
            labelPanel.setBackground(Color.WHITE);

            // Create label for the text and value
            JLabel titleLabel = new JLabel(label);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            titleLabel.setForeground(Color.GRAY);

            JLabel valueLabel = new JLabel(String.valueOf(completionValue));
            valueLabel.setFont(new Font("Arial", Font.BOLD, 14));
            valueLabel.setForeground(Color.GRAY);

            // Add title and value to the label panel
            labelPanel.add(titleLabel, BorderLayout.WEST);
            labelPanel.add(valueLabel, BorderLayout.EAST);

            // Create the rounded progress bar
            RoundedProgressBar progressBar = new RoundedProgressBar(15); // Radius of 15px for rounded corners
            progressBar.setValue(completionValue);
            progressBar.setStringPainted(false);
            progressBar.setPreferredSize(new Dimension(200, 15));
            progressBar.setForeground(new Color(0, 123, 181)); // Set bar color
            progressBar.setBackground(new Color(180, 220, 240)); // Set background color

            // Add label and progress bar to the main panel
            add(labelPanel, BorderLayout.NORTH);
            add(progressBar, BorderLayout.SOUTH);
        }
//        public static void main(String[] args) {
//            // Create a frame to test the completion panel
//            JFrame frame = new JFrame("Completion Panel Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(300, 150);
//            frame.setLayout(new FlowLayout());
//
//            // Add a sample completion panel
//            JPanel completionPanel = createCompletionPanel("#1 Massage", 45);
//            frame.add(completionPanel);
//
//            frame.setVisible(true);
//        }
}
