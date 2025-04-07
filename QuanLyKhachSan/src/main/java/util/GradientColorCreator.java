package util;

import javax.swing.*;
import java.awt.Color;
import java.awt.GradientPaint;

public class GradientColorCreator {

    /**
     * Tạo GradientPaint với màu F5F5DC chiếm 60% và màu D2B48C chiếm 40%, theo chiều ngang.
     *
     * @param width  Chiều rộng của GradientPaint
     * @param height Chiều cao của GradientPaint
     * @param color1  Chiều màu 1
     * @param color2 Chiều màu 2
     * @return GradientPaint đã tạo.
     */
    public static GradientPaint createHorizontalGradient(int width,int height,Color color1, Color color2) {
        // Màu 1: #F5F5DC (Beige)
//        Color color1 = Color.decode("#F5F5DC");

        // Màu 2: #D2B48C (Tan)
//        Color color2 = Color.decode("#D2B48C");

        // Vị trí chuyển đổi màu (60% chiều rộng)
        int splitX = (int) (width * 0.6);

        // Tạo GradientPaint theo chiều ngang
        return new GradientPaint(0, 0, color1, splitX, 0, color2, true);
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng
        JFrame frame = new JFrame("Gradient Color Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JButton button = new JButton("Gradient Button"){
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (g instanceof java.awt.Graphics2D) {
                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
                    g2.setPaint(createHorizontalGradient(getWidth(),getHeight(),Color.decode("#F5F5DC"), Color.decode("#D2B48C")));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };
        button.setContentAreaFilled(false); // Prevent default button background
        JPanel panel = new JPanel();
        panel.add(button);
//        button.setOpaque(true);
//        button.setBackground(createHorizontalGradient(400,400,Color.decode("#F5F5DC"), Color.decode("#D2B48C")));
        frame.add( panel);
        frame.setVisible(true);

//        System.out.println("Gradient đã được tạo: " + gradient);
    }
}
