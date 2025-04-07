package GUI;

import DAO.GeneralDAO;
import Entity.Enum.TrangThaiPhong;
import Entity.LoaiPhong;
import com.toedter.calendar.JDateChooser;
import org.checkerframework.checker.units.qual.N;
import util.ColorAndFont;
import util.GoogleDriveImageViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ThongTinLoaiPhong extends JPanel {
   private GeneralDAO generalDAO = new GeneralDAO();
    public ThongTinLoaiPhong() {
        setLayout(new BorderLayout());
        initSearchPanel();
        add(new JPanel(), BorderLayout.CENTER);
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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new ThongTinLoaiPhong());
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
