package GUI;

import DAO.GeneralDAO;
import Entity.Enum.TrangThaiPhong;
import Entity.LoaiPhong;
import GUI.Model.SvgPanel;
import com.toedter.calendar.JDateChooser;
import org.checkerframework.checker.units.qual.N;
import util.ColorAndFont;
import util.FormatUtil;
import util.GoogleDriveImageViewer;
import util.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ThongTinLoaiPhong extends JPanel {
    private LoaiPhong selectedLoaiPhong ;
    private String statusLoaiPhong;
    private static final List<Color> TYPE_OF_ROOM_COLOR = new ArrayList<>(Arrays.asList(
            Color.decode("#8cd8fa"),// superior
            Color.decode("#ede5cd"),// standard
            Color.decode("#ffd658")// deluxe
    ));
   private GeneralDAO generalDAO = new GeneralDAO();
    private JLabel availabilityLabel;
    private JLabel typeLabel;
    private Image mainImage;
    private JLabel giuongLabel;
    private JLabel dienTichLabel;
    private JLabel soTreEmLabel;
    private JLabel soNguoiLonLabel;
    private JLabel thumb1Label;
    private JLabel thumb2Label;
    private JLabel lastImage;
    private JTextArea descArea;
    private JLabel mainImageLabel;


    public ThongTinLoaiPhong() {
        setLayout(new BorderLayout());
//        initSearchPanel();
        initCenterPanel();
    }
    private void initSearchPanel(){
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        JDateChooser ngayDen = new JDateChooser();
        ngayDen.setDateFormatString("dd/MM/yyyy");
        JDateChooser ngayDi = new JDateChooser();
        ngayDi.setDateFormatString("dd/MM/yyyy");
        JPanel NgayDenPanel = compoundLabelAndObject("Ngày đến", ngayDen);
        JPanel NgayDiPanel = compoundLabelAndObject("Ngày đi", ngayDi);
        JPanel SoNguoiLonPanel = compoundLabelAndObject("Số người lớn", new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)));
        JPanel SoTreEmPanel = compoundLabelAndObject("Số trẻ em", new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)));

        Set<LoaiPhong> loaiPhongSet = new HashSet<>(generalDAO.findAll(LoaiPhong.class));
        JComboBox<String> loaiPhongComboBox = new JComboBox<>(loaiPhongSet.stream().map(LoaiPhong::getTenLoaiPhong).toList().toArray(new String[0]));
        JPanel LoaiPhongPanel = compoundLabelAndObject("Loại phòng", loaiPhongComboBox);

        JComboBox<String> viTriComboBox = new JComboBox<>(new String[]{"Tầng 1", "Tầng 2","Tầng 3","Tầng 4"});
        JPanel ViTriPanel = compoundLabelAndObject("Vị trí", viTriComboBox);

        JPanel tenDoanPanel = compoundLabelAndObject("Tên đoàn", new JTextField(20));

        JButton searchButton = initSearchButon();

        JPanel legendTrangThaiPhongPanel = new JPanel();
        legendTrangThaiPhongPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Sắp xếp ngang, cách nhau 10px
        legendTrangThaiPhongPanel.add(createCustomPanel("1", TrangThaiPhong.TRONG.s, TrangThaiPhong.TRONG));
        legendTrangThaiPhongPanel.add(createCustomPanel("2", TrangThaiPhong.DANG_SU_DUNG.s, TrangThaiPhong.DANG_SU_DUNG));
        legendTrangThaiPhongPanel.add(createCustomPanel("3", "Dọn dẹp", TrangThaiPhong.DANG_DON_DEP));
        legendTrangThaiPhongPanel.add(createCustomPanel("4", TrangThaiPhong.DAT_TRUOC.s, TrangThaiPhong.DAT_TRUOC));
        legendTrangThaiPhongPanel.add(createCustomPanel("5", "Sửa chữa", TrangThaiPhong.DANG_SUA_CHUA));
        legendTrangThaiPhongPanel.add(createCustomPanel("6", TrangThaiPhong.KHONG_SU_DUNG.s, TrangThaiPhong.KHONG_SU_DUNG));
        legendTrangThaiPhongPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        legendTrangThaiPhongPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        searchPanel.add(NgayDenPanel, c);
        c.gridx=1;
        searchPanel.add(SoNguoiLonPanel, c);
        c.gridx=2;
        searchPanel.add(LoaiPhongPanel, c);
        c.gridx=3;
        searchPanel.add(ViTriPanel, c);
        c.gridx=4;
        searchPanel.add(tenDoanPanel, c);
        c.gridx=5;
        searchPanel.add(searchButton, c);
        c.gridy=1;
        c.gridx=0;
        searchPanel.add(NgayDiPanel, c);
        c.gridx=1;
        searchPanel.add(SoTreEmPanel, c);
        c.gridx=2;
        c.gridwidth=GridBagConstraints.REMAINDER;
        searchPanel.add(legendTrangThaiPhongPanel, c);

        add(searchPanel, BorderLayout.NORTH);
    }
    private void initCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        List<LoaiPhong> loaiPhongList = generalDAO.findAll(LoaiPhong.class);
        for (LoaiPhong loaiPhong : loaiPhongList) {
            loaiPhong.setMoTa("Phòng " + loaiPhong.getTenLoaiPhong() + " với diện tích " + loaiPhong.getDienTich() + " m², có sức chứa tối đa " + loaiPhong.getSoLuongNguoiLon() + " người lớn và " + loaiPhong.getSoLuongTreEm() + " trẻ em.");
            JPanel roomCard = createRoomCard("Trống 10/12", loaiPhong);
            roomCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            roomCard.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    selectedLoaiPhong = loaiPhong;
                    statusLoaiPhong = "Trống 10/12";
                    availabilityLabel.setText(statusLoaiPhong);
                    typeLabel.setText(selectedLoaiPhong.getTenLoaiPhong());
                    if(selectedLoaiPhong.getTenLoaiPhong().equals("Standard")) {
                        typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(1));
                    }else if(selectedLoaiPhong.getTenLoaiPhong().equals("Superior")) {
                        typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(0));
                    }else if(selectedLoaiPhong.getTenLoaiPhong().equals("Deluxe")) {
                        typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(2));
                    }else {
                        typeLabel.setBackground(Color.LIGHT_GRAY);
                    }
                    typeLabel.setBorder(new RoundedBorder(20));

                    mainImage = new ImageIcon("src/main/resources/pic/typeOfRoom/" + selectedLoaiPhong.getTenLoaiPhong() + ".jpg").getImage();
                    mainImageLabel.setIcon(new ImageIcon(mainImage.getScaledInstance(500, 300, Image.SCALE_SMOOTH)));
                    Image a= new ImageIcon("src/main/resources/pic/typeOfRoom/" + selectedLoaiPhong.getTenLoaiPhong() + "1.jpg").getImage();
                    thumb1Label.setIcon( new ImageIcon(a.getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
                    Image b= new ImageIcon("src/main/resources/pic/typeOfRoom/" + selectedLoaiPhong.getTenLoaiPhong() + "2.jpg").getImage();
                    thumb2Label.setIcon( new ImageIcon(b.getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
                    Image c= new ImageIcon("src/main/resources/pic/typeOfRoom/" + selectedLoaiPhong.getTenLoaiPhong() + "3.jpg").getImage();
                    lastImage.setIcon(new ImageIcon(c.getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
                    giuongLabel.setText("1 giường");
                    dienTichLabel.setText(selectedLoaiPhong.getDienTich() + " m²");
                    soTreEmLabel.setText(selectedLoaiPhong.getSoLuongTreEm() + " trẻ em");
                    soNguoiLonLabel.setText(selectedLoaiPhong.getSoLuongNguoiLon() + " người lớn");
                    descArea.setText(selectedLoaiPhong.getMoTa());
                }
            });
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE; // Tự động tăng y cho mỗi phòng
            c.insets = new Insets(10, 10, 10, 10);
            c.weightx = 1.0; // Tăng chiều rộng
            centerPanel.add(roomCard,c);
        }
        selectedLoaiPhong= loaiPhongList.get(0);
        statusLoaiPhong= "Trống 10/12";
        JPanel rightPanel = createRoomDetailInfo();
        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        mainPanel.add(centerPanel);
        mainPanel.add(rightPanel);
        mainPanel.setBackground(Color.WHITE);
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }
    private<T> JPanel compoundLabelAndObject(String labelText, T object){
        if(object instanceof Component component){
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            JLabel label = new JLabel(labelText);
//            label.setFont(ColorAndFont.TEXT_FONT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            label.setPreferredSize(new Dimension(100, 30));
            label.setMinimumSize(new Dimension(100, 30));
            panel.add(label, BorderLayout.WEST);
            component.setPreferredSize(new Dimension(150, 30));
            component.setMinimumSize(new Dimension(150, 30));
            panel.add(component, BorderLayout.CENTER);
            return panel;
        }
        return null;
    }
    private JButton initSearchButon(){
        JButton searchButton = new JButton();
        try {
            Image imageResize= GoogleDriveImageViewer.getImageByFileName("icons8-search-48.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            searchButton.setIcon(new ImageIcon(imageResize));
            searchButton.setBorder(new RoundedBorder(15));
        } catch (GeneralSecurityException | IOException e) {
            searchButton.setText("Tìm kiếm");
            throw new RuntimeException(e);
        }
        searchButton.setBackground(ColorAndFont.TEXT_COLOR_PURPLE);
//        searchButton.addActionListener(e -> {});
        return searchButton;
    }
    /**
     * Tạo một JPanel như hình đã mô tả
     *
     * @param number Số hiển thị trong khung vuông bên trái.
     * @param text   Văn bản hiển thị bên phải.
     * @return JPanel được tạo với bố cục đúng yêu cầu.
     */
    public static JPanel createCustomPanel(String number, String text, TrangThaiPhong trangThaiPhong) {
        // Tạo JPanel chính
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 0)); // Sắp xếp ngang, cách nhau 10px
        panel.setBackground(Color.WHITE); // Màu nền của toàn bộ JPanel

        // Phần số (trong khung vuông)
        JLabel numberLabel = new JLabel(number, SwingConstants.CENTER);
        numberLabel.setPreferredSize(new Dimension(30, 30));
        numberLabel.setOpaque(true);
        numberLabel.setBackground(ColorAndFont.COLOR_TRANG_THAI_PHONG[trangThaiPhong.ordinal()]); // Màu xanh nhạt
        numberLabel.setForeground(Color.WHITE); // Màu chữ trắng
        numberLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Font chữ to, đậm

        // Bo tròn khung vuông
        numberLabel.setBorder(BorderFactory.createLineBorder(ColorAndFont.BORDER_COLOR_TRANG_THAI_PHONG[trangThaiPhong.ordinal()], 2, true));

        // Phần văn bản
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setForeground(Color.GRAY); // Màu chữ xám

        // Thêm các thành phần vào panel
        panel.add(numberLabel);
        panel.add(textLabel);
        return panel;
    }
    public static JPanel createRoomCard(String emptyStatus,LoaiPhong loaiPhong) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(850, 250));

        // 📷 Left: Room image
        JLabel imageLabel = new JLabel();
        Image tmp= new ImageIcon("src/main/resources/pic/typeOfRoom/"+loaiPhong.getTenLoaiPhong()+".jpg").getImage();
        imageLabel.setIcon(new ImageIcon(tmp.getScaledInstance(300, 250, Image.SCALE_SMOOTH)));
        card.add(imageLabel, BorderLayout.WEST);

        // 📄 Right: Room details
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(Color.WHITE);

        // 🔝 Top info: availability + room type
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel availabilityLabel = new JLabel(emptyStatus);
        availabilityLabel.setFont(new Font("Arial", Font.BOLD, 20));


        JLabel typeLabel = new JLabel(loaiPhong.getTenLoaiPhong(), SwingConstants.CENTER);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        typeLabel.setOpaque(true);
        if(loaiPhong.getTenLoaiPhong().equals("Standard")) {
            typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(1));
        }else if(loaiPhong.getTenLoaiPhong().equals("Superior")) {
            typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(0));
        }else if(loaiPhong.getTenLoaiPhong().equals("Deluxe")) {
            typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(2));
        }else {
            typeLabel.setBackground(Color.LIGHT_GRAY);
        }
        typeLabel.setBorder(new RoundedBorder(20));
        topPanel.add(availabilityLabel, BorderLayout.WEST);
        topPanel.add(typeLabel, BorderLayout.EAST);

        // 🔢 Room Specs: bed, area, kids, adults
        JPanel specPanel = new JPanel(new GridLayout(1,8, 10, 0));
//        specPanel.setOpaque(false);
        File bedIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/bed.svg").getFile());
        File areaIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/area.svg").getFile());
        File kidsIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/kid.svg").getFile());
        File adultsIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/adult.svg").getFile());
        SvgPanel bedIcon = new SvgPanel();
        SvgPanel areaIcon = new SvgPanel();
        SvgPanel kidsIcon = new SvgPanel();
        SvgPanel adultsIcon = new SvgPanel();
        bedIcon.loadSvg(bedIconFile, 50, 50);
        areaIcon.loadSvg(areaIconFile, 50, 50);
        kidsIcon.loadSvg(kidsIconFile, 50, 50);
        adultsIcon.loadSvg(adultsIconFile, 50, 50);

        specPanel.add( bedIcon);
        specPanel.add( new JLabel( "1 giường"));
        specPanel.add(areaIcon);
        specPanel.add( new JLabel(loaiPhong.getDienTich() + " m²"));
        specPanel.add(kidsIcon);
        specPanel.add(new JLabel(loaiPhong.getSoLuongTreEm() + " trẻ em"));
        specPanel.add(adultsIcon);
        specPanel.add(new JLabel(loaiPhong.getSoLuongNguoiLon() +"Người lớn"));
        specPanel.setBackground(Color.WHITE);
        // 📝 Description
        JTextArea descArea = new JTextArea(loaiPhong.getMoTa());
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 13));
//        descArea.setForeground(Color.WHITE);
        // 💰 Price
        JLabel priceLabel = new JLabel( FormatUtil.formatMoney(loaiPhong.getGia()) + " VND/Ngày");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 1.0; // Tăng chiều rộng
        c.weighty = 0.3; // Tăng chiều cao
        centerPanel.add(specPanel, c);
        c.gridy = 1;
        c.weighty = 0.7; // Tăng chiều cao
        centerPanel.add(descArea,c);
        centerPanel.setBackground(Color.WHITE);
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(priceLabel, BorderLayout.EAST);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        card.add(rightPanel, BorderLayout.CENTER);
        return card;
    }
    public JPanel createRoomDetailInfo() {
        // Font tuỳ chỉnh
        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Thông tin chi tiết loại phòng", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        // Header
        // 🔝 Top info: availability + room type
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        availabilityLabel = new JLabel(statusLoaiPhong);
        availabilityLabel.setFont(new Font("Arial", Font.BOLD, 20));


         typeLabel = new JLabel(selectedLoaiPhong.getTenLoaiPhong(), SwingConstants.CENTER);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        typeLabel.setOpaque(true);
        if(selectedLoaiPhong.getTenLoaiPhong().equals("Standard")) {
            typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(1));
        }else if(selectedLoaiPhong.getTenLoaiPhong().equals("Superior")) {
            typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(0));
        }else if(selectedLoaiPhong.getTenLoaiPhong().equals("Deluxe")) {
            typeLabel.setBackground(TYPE_OF_ROOM_COLOR.get(2));
        }else {
            typeLabel.setBackground(Color.LIGHT_GRAY);
        }
        typeLabel.setBorder(new RoundedBorder(20));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(availabilityLabel, BorderLayout.WEST);
        topPanel.add(typeLabel, BorderLayout.EAST);


        // Ảnh chính
        mainImage = new ImageIcon("src/main/resources/pic/typeOfRoom/" + selectedLoaiPhong.getTenLoaiPhong() + ".jpg").getImage();
        mainImageLabel = new JLabel(new ImageIcon(mainImage)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (mainImage != null) {
                    int w = getWidth();
                    int h = getHeight();
                    g.drawImage(mainImage, 0, 0, w, h, this);
                }
            }
        };
        mainImageLabel.setPreferredSize(new Dimension(500, 300));

// Set border and other properties
        mainImageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        // Ảnh phụ
        JPanel thumbnailsPanel = new JPanel(new GridLayout(1, 3, 5, 0));

//        thumbnailsPanel.setPreferredSize(new Dimension(700, 150));
        Image thumb1=new ImageIcon("src/main/resources/pic/typeOfRoom/"+selectedLoaiPhong.getTenLoaiPhong()+"1.jpg").getImage();
        thumb1Label = new JLabel(new ImageIcon(thumb1.getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
        thumbnailsPanel.add(thumb1Label);
        Image thumb2=new ImageIcon("src/main/resources/pic/typeOfRoom/"+selectedLoaiPhong.getTenLoaiPhong()+"2.jpg").getImage();
        thumb2Label = new JLabel(new ImageIcon(thumb2.getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
        thumbnailsPanel.add(thumb2Label);
//        mainImageLabel.setIcon(new ImageIcon(mainImage.getScaledInstance(300, 250, Image.SCALE_SMOOTH)));

        Image thumb3=new ImageIcon("src/main/resources/pic/typeOfRoom/"+selectedLoaiPhong.getTenLoaiPhong()+"3.jpg").getImage();
         lastImage = new JLabel(new ImageIcon(thumb3.getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
        thumbnailsPanel.add(lastImage);

        // 🔢 Room Specs: bed, area, kids, adults
        JPanel specPanel = new JPanel(new GridLayout(1,8, 10, 0));
//        specPanel.setOpaque(false);
        File bedIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/bed.svg").getFile());
        File areaIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/area.svg").getFile());
        File kidsIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/kid.svg").getFile());
        File adultsIconFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/adult.svg").getFile());
        SvgPanel bedIcon = new SvgPanel();
        bedIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        SvgPanel areaIcon = new SvgPanel();
        areaIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        SvgPanel kidsIcon = new SvgPanel();
        kidsIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        SvgPanel adultsIcon = new SvgPanel();
        adultsIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        bedIcon.loadSvg(bedIconFile, 30, 30);
        areaIcon.loadSvg(areaIconFile, 30, 30);
        kidsIcon.loadSvg(kidsIconFile, 30, 30);
        adultsIcon.loadSvg(adultsIconFile, 30, 30);

        specPanel.add( bedIcon);
        giuongLabel= new JLabel("1 giường");
        specPanel.add(giuongLabel);
        specPanel.add(areaIcon);
        dienTichLabel= new JLabel(selectedLoaiPhong.getDienTich() + " m²");
        specPanel.add( dienTichLabel);
        specPanel.add(kidsIcon);
        soTreEmLabel= new JLabel(selectedLoaiPhong.getSoLuongTreEm() + " trẻ em");
        specPanel.add( soTreEmLabel);
        specPanel.add(adultsIcon);
        soNguoiLonLabel= new JLabel(selectedLoaiPhong.getSoLuongNguoiLon() +"Người lớn");
        specPanel.add( soNguoiLonLabel);
        specPanel.setPreferredSize(new Dimension(500, 50));
        specPanel.setBackground(ColorAndFont.BACKGROUND_COLOR);


         descArea = new JTextArea(selectedLoaiPhong.getMoTa());
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 13));

        JLabel title2 = new JLabel("Nội thất");
        title2.setFont(new Font("Arial", Font.BOLD, 20));

        // 🔢 Room Specs: bed, area, kids, adults
        JPanel noiThatPanel = new JPanel(new GridLayout(1,8, 10, 0));
//        specPanel.setOpaque(false);
        File wifiFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/wifi.svg").getFile());
        File tuLanhFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/tuLanh.svg").getFile());
        File dieuHoaFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/dieuHoa.svg").getFile());
        File tiviFile = new File(ThongTinLoaiPhong.class.getResource("/pic/icon/tv.svg").getFile());
        SvgPanel wifiIcon = new SvgPanel();
        wifiIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        SvgPanel tuLanhIcon = new SvgPanel();
        tuLanhIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        SvgPanel dieuHoaIcon = new SvgPanel();
        dieuHoaIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        SvgPanel tiviIcon = new SvgPanel();
        tiviIcon.setBackground(ColorAndFont.BACKGROUND_COLOR);
        wifiIcon.loadSvg(wifiFile, 30, 30);
        tuLanhIcon.loadSvg(tuLanhFile, 30, 30);
        dieuHoaIcon.loadSvg(dieuHoaFile, 30, 30);
        tiviIcon.loadSvg(tiviFile, 30, 30);


        noiThatPanel.add( wifiIcon);
        noiThatPanel.add( new JLabel( "Wifi-5"));
        noiThatPanel.add(tuLanhIcon);
        noiThatPanel.add( new JLabel("Tủ lạnh"));
        noiThatPanel.add(dieuHoaIcon);
        noiThatPanel.add(new JLabel("Điều hòa"));
        noiThatPanel.add(tiviIcon);
        noiThatPanel.add(new JLabel("TV"));
        noiThatPanel.setPreferredSize(new Dimension(500, 50));
        noiThatPanel.setBackground(ColorAndFont.BACKGROUND_COLOR);
        // Panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ColorAndFont.BACKGROUND_COLOR);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST; // Căn lề trên
        // Thêm vào giao diện
        mainPanel.add(topPanel,c);
        c.gridy = 1;
        mainPanel.add(mainImageLabel, c);
        c.gridy = 2;
        mainPanel.add(thumbnailsPanel, c);
        c.gridy = 3;
        mainPanel.add(specPanel,c);
        c.gridy=4;
        mainPanel.add(descArea,c);
        c.gridy=5;
        mainPanel.add(title2,c);
        c.gridy=6;
        mainPanel.add(noiThatPanel,c);
        c.gridy=7;
        c.weightx = 1.0; // Tăng chiều rộng
        c.weighty = 1.0; // Tăng chiều cao
        mainPanel.add(new JPanel(),c);
        return  mainPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.add(new ThongTinLoaiPhong());
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

