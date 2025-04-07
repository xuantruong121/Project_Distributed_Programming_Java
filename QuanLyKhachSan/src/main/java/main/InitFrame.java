package main;

import GUI.Header;
import GUI.Menu;
import GUI.TrangChuGUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class InitFrame extends JFrame {
    private JPanel southPanel=new JPanel();;
    private JPanel centerPanel = new JPanel(new CardLayout());;
    private JPanel northPanel=new JPanel();;
    private JPanel westPanel;
    private final Integer minWidthOfWest= this.getWidth()/10;

    public InitFrame() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        intitComponents();
    }
    private void intitComponents() {
        setLayout(new BorderLayout());
        initWestPanel();
        initCenterPanel();
        try {
            initNorthPanel();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void initWestPanel(){
        westPanel= new Menu(centerPanel);
        ((Menu)westPanel).addMouseMotionListener(westPanel);
//        JPanel a= ((Menu) westPanel).getMenuItembyName("Danh sách đơn đặt phòng");
//        a.setBackground(Color.RED);
////        SwingUtilities.invokeLater(() -> {
////            MouseEvent event = new MouseEvent(
////                    a,
////                    MouseEvent.MOUSE_CLICKED,
////                    System.currentTimeMillis(),
////                    0, // No modifiers
////                    50, // X coordinate
////                    50, // Y coordinate
////                    1, // Click count
////                    false // Not a popup trigger
////            );
////            a.dispatchEvent(event);
////        });
        westPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(westPanel, BorderLayout.WEST);
    }
    private void initNorthPanel() throws GeneralSecurityException, IOException {

//
        add(northPanel, BorderLayout.NORTH);
        northPanel.add(new Header(),"Header");
    }
    private void initSouthPanel(){
//        southPanel =
        add(southPanel, BorderLayout.SOUTH);
    }
    private void initCenterPanel(){
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.add(new TrangChuGUI(),"TrangChu");
//        centerPanel.add(new DanhSachDonDatPhong(centerPanel),"DanhSachDonDatPhong");
//        ((CardLayout)centerPanel.getLayout()).show(centerPanel,"DanhSachDonDatPhong");
//        centerPanel.add(new DashBoard() ,"DashBoard");
    }
}
