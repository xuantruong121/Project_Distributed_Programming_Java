//package GUI;
//
//import GUI.Model.Menu_Model;
//import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
//import org.kordamp.ikonli.swing.FontIcon;
//import util.GoogleDriveImageViewer;
//import util.ImageResizer;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//
//
//
//public class Menu extends JPanel {
//    private final JPanel centerPanel;
//    private JPanel logoPanel;
//    private final int sizeOfIcon = 20;
//    private JPanel listMenu;
//    private final ArrayList<Menu_Item> listMenuItem = new ArrayList<>();
//    private int menuWidth = 300; // Độ rộng ban đầu của menu
//    private int minWidth = 50;   // Độ rộng nhỏ nhất cho phép
//    private int maxWidth = 500;  // Độ rộng lớn nhất cho phép
//    private boolean isResizing = false; // Cờ để xác định xem có đang resize hay không
//    private static final int RESIZE_MARGIN = 5;
//    public Menu(JPanel centerPanel) {
//        this.centerPanel = centerPanel;
//        initComponents();
//    }
//    private void initComponents(){
//        logoPanel = new JPanel();
//        listMenu =  new JPanel();
//        JScrollPane listScrollPane = new JScrollPane(listMenu);
//        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        listScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
//        listScrollPane.getVerticalScrollBar().setUnitIncrement(16);
//        setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        add(logoPanel, c);
//
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        add(listScrollPane, c);
//
//        initLogo();
//        initListMenu();
//    }
//    private void initListMenu() {
//        Menu_Model[] trangChu = new Menu_Model[]{
//                new Menu_Model(FontIcon.of(AntDesignIconsOutlined.HOME, sizeOfIcon).toImageIcon(), "Trang chủ", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
//        };
//        Menu_Model[] quanLyDatPhong = new Menu_Model[]{
//                new Menu_Model(FontIcon.of(AntDesignIconsOutlined.SOLUTION, sizeOfIcon).toImageIcon(), "Quản lý đặt phòng", Menu_Model.MenuType.TITLE),
//                new Menu_Model(null, "Đơn đặt phòng", Menu_Model.MenuType.MENU),
//                new Menu_Model(null, "Bản đồ phòng", Menu_Model.MenuType.MENU),
//                new Menu_Model(null, "Thông tin loại phòng", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
//        };
//        Menu_Model[] quanLyThongKe = new Menu_Model[]{
//                new Menu_Model(FontIcon.of(AntDesignIconsOutlined.AREA_CHART, sizeOfIcon).toImageIcon(), "Thống kê", Menu_Model.MenuType.TITLE),
//                new Menu_Model(null, "Doanh thu", Menu_Model.MenuType.MENU),
//                new Menu_Model(null, "Kho", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"Khác", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
//        };
//        Menu_Model[] quanLyKho = new Menu_Model[]{
//                new Menu_Model(null, "Quản lý kho", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
//        };
//        Menu_Model[] quanLyKhuyenMai = new Menu_Model[]{
//                new Menu_Model(null, "Quản lý khuyến mãi", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
//        };
//        Menu_Model[] quanLyDonBaoCao = new Menu_Model[]{
//                new Menu_Model(null, "Quản lý đơn báo cáo", Menu_Model.MenuType.MENU),
//                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
//        };
////        Menu_Model[] quanLyThongKe = new Menu_Model[]{
////                new Menu_Model(FontIcon.of(AntDesignIconsOutlined.CALENDAR, sizeOfIcon).toImageIcon(), "Thống kê", Menu_Model.MenuType.TITLE),
////                new Menu_Model(FontIcon.of(AntDesignIconsOutlined.CALENDAR, sizeOfIcon).toImageIcon(), "Thống kê theo biểu đồ", Menu_Model.MenuType.MENU),
////                new Menu_Model(FontIcon.of(AntDesignIconsOutlined.PLUS_CIRCLE, sizeOfIcon).toImageIcon(), "Thống kê theo file", Menu_Model.MenuType.MENU),
////                new Menu_Model(null,"", Menu_Model.MenuType.EMPTY)
////        };
//
//        listMenuItem.add(new Menu_Item(trangChu,centerPanel));
//        listMenuItem.add(new Menu_Item(quanLyDatPhong,centerPanel));
//        listMenuItem.add(new Menu_Item(quanLyThongKe,centerPanel));
//        listMenuItem.add(new Menu_Item(quanLyKho,centerPanel));
//        listMenuItem.add(new Menu_Item(quanLyKhuyenMai,centerPanel));
//        listMenuItem.add(new Menu_Item(quanLyDonBaoCao,centerPanel));
//
////        Menu_Item item1 = new Menu_Item(quanLyDatPhong,centerPanel);
////        item1.getMenu_ItemByName("Danh sách đơn đặt phòng").setBackground(Color.RED);
////        Menu_Item item2 = new Menu_Item(quanLyPhong,centerPanel);
////        Menu_Item item3 = new Menu_Item(quanLyKhachHang,centerPanel);
////        Menu_Item item4 = new Menu_Item(quanLyDichVu,centerPanel);
////        Menu_Item item5 = new Menu_Item(quanLyNhanVien,centerPanel);
////        Menu_Item item6 = new Menu_Item(quanLyThongKe,centerPanel);
//
//        listMenu.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.HORIZONTAL;
//        for (int i = 0; i < listMenuItem.size(); i++) {
//            c.gridy=i;
//            c.weightx=1;
//            listMenu.add(listMenuItem.get(i),c);
//        }
//        c.gridy=listMenuItem.size();
//        c.weighty=1;
//        listMenu.add(new JPanel(),c);
//    }
//    private void initLogo(){
//        try {
//            Image img = ImageResizer.resizeImage( GoogleDriveImageViewer.getImageByFileName("logo.png"),200,120);
//            JLabel logoLabel = new JLabel();
//            logoLabel.setIcon(new ImageIcon(img));
//            logoPanel.add(logoLabel);
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public void addMouseMotionListener(JPanel menuPanel){
//        // Thêm xử lý sự kiện thu nhỏ bằng chuộ
//        MouseAdapter resizeHandler = new MouseAdapter() {
//            private Point initialPoint;
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                // Nếu chuột di chuyển vào mép trái của menu (khoảng cách RESIZE_MARGIN)
//                if (e.getX() >= menuPanel.getWidth() - RESIZE_MARGIN) {
//                    menuPanel.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)); // Đặt con trỏ chuột phù hợp cho cạnh trái
//                } else {
//                    menuPanel.setCursor(Cursor.getDefaultCursor()); // Đặt lại con trỏ mặc định
//                }
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.getX() >= menuPanel.getWidth() - RESIZE_MARGIN) {
//                    isResizing = true; // Bắt đầu resize
//                    initialPoint = e.getPoint();
//                }
//            }
//
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                if (isResizing) {
//                    // Tính toán độ rộng mới của menu
//                    int newWidth =  e.getXOnScreen();
//
//                    // Giới hạn độ rộng trong khoảng minWidth và maxWidth
//                    if (newWidth >= minWidth && newWidth <= maxWidth) {
//                        menuWidth = newWidth;
//                        menuPanel.setPreferredSize(new Dimension(menuWidth, getHeight()));
//                        menuPanel.revalidate(); // Cập nhật lại kích thước
//                    }
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                isResizing = false; // Kết thúc resize
//            }
//        };
//        addMouseListener(resizeHandler);
//        addMouseMotionListener(resizeHandler);
//    }
//    public JPanel getMenuItembyName(String name){
//        for (Menu_Item item: listMenuItem){
//            JPanel tmp= item.getMenu_ItemByName(name);
//            if( tmp!=null ){
//                return tmp;
//            }
//        }
//        return null;
//    }
//}
//class Menu_Item extends JPanel {
//    private final Dimension  fixSizeForIcon = new Dimension(80,40);
//    private JPanel centerPanel = null;
//    private boolean isExpanded=false;
////    private final Color backgroundColor = Color.decode("#101924");
//    private final Color hoverColor = Color.decode("#7F8DFF");
//    private  final Font  font= new Font("Arial", Font.PLAIN, 15);
//    private final ArrayList<JPanel> listMenuItem = new ArrayList<JPanel>();
//    public Menu_Item(Menu_Model data) {
//        if(data == null){
//            return;
//        }
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        JPanel firstPanel =initMenuItem(data);
//        addMouseListenerToFirstPanel(firstPanel,null);
//        this.add(firstPanel);
//        listMenuItem.add(firstPanel);
////        this.setBackground(backgroundColor);
//    }
//    public Menu_Item(Menu_Model[] data,JPanel centerPanel) {
//        if(data == null){
//            return;
//        }
//        this.centerPanel = centerPanel;
//        if (data.length == 1) {
//            new Menu_Item(data[0]);
//        }
//        else {
//            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            JPanel firstPanel = initMenuItem(data[0]);
//            Menu_Model[] subArray = Arrays.copyOfRange(data, 1, data.length);
//            JPanel subPanel= initSubMenu(subArray);
//            subPanel.setName("subPanel");
//            addMouseListenerToFirstPanel(firstPanel,subPanel);
//            this.add(firstPanel);
//            this.add(subPanel);
////            this.setBackground(backgroundColor);
//
//        }
//
//    }
//    private JPanel initSubMenu(Menu_Model[] data){
//        JPanel subMenu = new JPanel();
//        subMenu.setLayout(new BoxLayout(subMenu, BoxLayout.Y_AXIS));
//        for (Menu_Model item : data) {
//            JPanel itemPanel = initMenuItem(item);
//            subMenu.add(itemPanel);
//            listMenuItem.add(itemPanel);
//        }
////        subMenu.setBackground(subMenuColor);
//        subMenu.setVisible(false);
//        subMenu.setOpaque(true);
//        return subMenu;
//    }
//    private JPanel initMenuItem(Menu_Model data){
//        JPanel item = new JPanel();
//        JLabel lbIcon = new JLabel();
//        JLabel lbName = new JLabel();
//        if (data.getType() == Menu_Model.MenuType.MENU) {
//            lbIcon.setPreferredSize(fixSizeForIcon);
//            lbIcon.setIcon(data.getIcon());
//            lbName.setText(data.getName());
//            item.setPreferredSize(new Dimension(220,35));
//        } else if (data.getType() == Menu_Model.MenuType.TITLE) {
//            lbName.setText(data.getName());
//            lbIcon.setIcon(data.getIcon());
//            item.setPreferredSize(new Dimension(220,45));
//        } else {
//            item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.BLACK ));
//            return item;
//        }
//        GridBagLayout layout = new GridBagLayout();
//        item.setLayout(layout);
//        GridBagConstraints c = new GridBagConstraints();
//        c.insets = new Insets(0, 5, 0, 5);
//        c.gridx=0;
//        c.gridy=0;
//        c.weightx=0;
//        c.anchor=GridBagConstraints.WEST;
//        item.add(lbIcon,c);
//
//        c.gridx=1;
//        c.gridy=0;
//        c.weightx=0.1;
//        item.add(lbName,c);
//
//        item.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        var backGroundColor=  item.getBackground();
//        item.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if(data.getType() == Menu_Model.MenuType.MENU){
//                    if(data.getName().equals("Danh sách đơn đặt phòng")){
////                        try {
////                            JOptionPane.showMessageDialog(null,"Danh sách đơn đặt phòng");
////                            changeContentInCenterPanel(DanhSachDonDatPhong.class,new Class[]{JPanel.class},new Object[]{ centerPanel},"DanhSachDonDatPhong");
////                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
////                                 IllegalAccessException ex) {
////                            throw new RuntimeException(ex);
////                        }
//                    }
//                    else if(data.getName().equals("Chi tiết đơn đặt phòng")){
////                        try {
////                            changeContentInCenterPanel(ChiTietDonDatPhongGUI.class,new Class[]{JPanel.class},new Object[]{ centerPanel},"ChiTietDonDatPhongGUI");
////                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
////                                 IllegalAccessException ex) {
////                            throw new RuntimeException(ex);
////                        }
//                    }
//                    else if(data.getName().equals("Danh sách phòng")){
////                        try {
////                            changeContentInCenterPanel(QuanLyPhong.class,new Class[]{JPanel.class},new Object[]{ centerPanel},"QuanLyPhong");
////                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
////                                 IllegalAccessException ex) {
////                            throw new RuntimeException(ex);
////                        }
//                    }
//
//
//                }
//            }
//            @Override
//            public void mouseEntered(MouseEvent evt) {
//                item.setBackground(hoverColor);
//            }
//            @Override
//            public void mouseExited(MouseEvent evt) {
//                item.setBackground(backGroundColor);
//            }
//        });
//        return item;
//    }
//    private void addMouseListenerToFirstPanel(JPanel panel, JPanel subMenu){
//        if(subMenu == null){
//            return;
//        }
//        panel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                isExpanded = !isExpanded;
//                subMenu.setVisible(isExpanded);
//                revalidate();
//                repaint();
//            }
//        });
//    }
//    private <T> void changeContentInCenterPanel (Class<T> tenClass,Class<?>[] parameterTypes, Object[] initargs, String name) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        if(centerPanel==null)
//            return;
//        for (Component cmp : centerPanel.getComponents()) {
//                if (tenClass.isInstance(cmp)) {
//                    ((CardLayout) centerPanel.getLayout()).show(centerPanel, name);
//                    return;
//                }
//        }
//
//        T ds = tenClass.getDeclaredConstructor(parameterTypes).newInstance(initargs);
//        centerPanel.add((Component) ds, name);
//        ((CardLayout) centerPanel.getLayout()).show(centerPanel, name);
//    }
//    public JPanel getMenu_ItemByName(String name){
//        for (Component c: listMenuItem ){
//            if(c instanceof JPanel panel){
//                for(Component c1: panel.getComponents()){
//                    if(c1 instanceof JLabel lb){
//                        if(lb.getText().equals(name)){
//                            return panel;
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//}
////public <T> T createInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
////    return clazz.newInstance();
////}
////public class GenericFactory<T> {
////    private Supplier<T> supplier;
////
////    public GenericFactory(Supplier<T> supplier) {
////        this.supplier = supplier;
////    }
////
////    public T createInstance() {
////        return supplier.get();
////    }
////}
