package GUI.Model;

import util.SvgImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SvgPanel extends JPanel {
    private BufferedImage svgImage;
    private float scale = 1.0f;

    public void loadSvg(File svgFile, float width, float height) {
        this.svgImage = SvgImageLoader.loadSvg(svgFile, width, height);
        repaint();
    }

    public void setScale(float scale) {
        this.scale = scale;
        repaint();
    }
    public SvgPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (svgImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Set rendering hints for better quality
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//            g2d.setBackground(Color.white);
            // Calculate position to center the image
            int x = (getWidth() - (int)(svgImage.getWidth() * scale)) / 2;
            int y = (getHeight() - (int)(svgImage.getHeight() * scale)) / 2;

            // Draw the scaled image
            g2d.drawImage(svgImage, x, y,
                    (int)(svgImage.getWidth() * scale),
                    (int)(svgImage.getHeight() * scale), null);

            g2d.dispose();
        }
    }
}