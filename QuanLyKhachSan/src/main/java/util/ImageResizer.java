package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageResizer {

    /**
     * Resizes an image to the specified width and height.
     *
     * @param originalImage  The input image to be resized.
     * @param width          The new width of the image.
     * @param height         The new height of the image.
     * @return               The resized image as a BufferedImage.
     */
    public static BufferedImage resizeImage(Image originalImage, int width, int height) {
        // Create a new BufferedImage with the desired dimensions
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Draw the original image resized to the new dimensions
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose(); // Dispose of the Graphics2D object to release resources

        return resizedImage; // Return the resized image
    }

    public static void main(String[] args) {
        try {
            // Example usage

            int newWidth = 100;
            int newHeight = 100;



            // Resize the image
            BufferedImage resizedImage = null;
            try {
                resizedImage = resizeImage(GoogleDriveImageViewer.getImageByFileName("man.png"), newWidth, newHeight);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
            JFrame frame = new JFrame();
            frame.add(new JLabel(new ImageIcon(resizedImage)));
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (IOException e) {
            System.err.println("Error resizing image: " + e.getMessage());
        }
    }
}
