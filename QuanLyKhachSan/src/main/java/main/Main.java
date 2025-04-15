package main;

import GUI.Header;
import GUI.TrangChuGUI;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.*;

import raven.drawer.Drawer;
import util.raven.drawer.MyDrawerBuilder;
import util.raven.login.Login;
import raven.popup.GlassPanePopup;
import util.raven.tabbed.WindowsTabbed;
import raven.toast.Notifications;

/**
 *
 * @author RAVEN
 */
public class Main extends javax.swing.JFrame {

    public static main.Main main;
    private Login loginForm;
    private Header header;
    private javax.swing.JPanel body;
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        init();
    }
    private void init() {
        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        MyDrawerBuilder myDrawerBuilder = new MyDrawerBuilder();
        Drawer.getInstance().setDrawerBuilder(myDrawerBuilder);
        WindowsTabbed.getInstance().install(this, body);
        // applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        login();
    }

    public void login() {
        if (loginForm == null) {
            loginForm = new Login();
        }
        WindowsTabbed.getInstance().showTabbed(false);
        loginForm.applyComponentOrientation(getComponentOrientation());
        setContentPane(loginForm);
        revalidate();
        repaint();
    }

    public void showMainForm() {
        WindowsTabbed.getInstance().showTabbed(true);
        WindowsTabbed.getInstance().removeAllTabbed();
        body.removeAll();
        JScrollPane scrollPane = new JScrollPane(new TrangChuGUI());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        body.add(scrollPane, java.awt.BorderLayout.CENTER);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        try {
            header = new Header();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        body = new JPanel();
        body.setLayout(new java.awt.BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            main = new main.Main();
            main.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables

    // End of variables declaration//GEN-END:variables
}
