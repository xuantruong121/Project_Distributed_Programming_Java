package GUI;
import GUI.Model.CompletedItem;
import GUI.Model.ItemTrangThai;
import GUI.Model.TaskItem;
import com.formdev.flatlaf.FlatLightLaf;
import javaswingdev.chart.ModelPieChart;
import javaswingdev.chart.PieChart;
import util.ColorAndFont;
import util.barChart.Chart;
import util.barChart.ModelChart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class TrangChuGUI extends JPanel {
    private JTable table;

    public TrangChuGUI() {
        setBackground(ColorAndFont.BACKGROUND_COLOR);
        setLayout(new GridBagLayout());
        initItemTrangThai();
        initLoaiPhongDuocDatPieChart();
        initDoanhThuBarChart();
        initTopDichVu();
        initTableDonDatPhongGanDay();
        initTaskList();
    }
    // y=0 x=0
    private void initItemTrangThai() {
        ItemTrangThai[] listItem= new ItemTrangThai[]{
                new ItemTrangThai(1,2,3,ItemTrangThai.TYPE.DON_DAT_MOI),
                new ItemTrangThai(1,2,3,ItemTrangThai.TYPE.PHONG_SAP_DEN),
                new ItemTrangThai(1,2,3,ItemTrangThai.TYPE.PHONG_SAP_DI),
                new ItemTrangThai(1,2,3,ItemTrangThai.TYPE.PHONG_TRONG),
        };
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,10,0,10);
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.gridy=0;
        for (ItemTrangThai item : listItem) {
            gbc.gridx++;
            mainPanel.add(item,gbc);
        }
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth=GridBagConstraints.REMAINDER;
        add(mainPanel, c);
    }
    // y=1 x=2
    private void initLoaiPhongDuocDatPieChart() {
        JPanel pieChartPn= new JPanel();
        pieChartPn.setBackground(Color.WHITE);
        PieChart pieChart = new PieChart();
        pieChart.setFont(ColorAndFont.TILE_FONT);
        pieChart.setChartType(PieChart.PeiChartType.DONUT_CHART);
        pieChart.addData(new ModelPieChart("Standard", 999, Color.decode("#D2B48C")));
        pieChart.addData(new ModelPieChart("Deluxe",99, Color.decode("#FFC107")));
        pieChart.addData(new ModelPieChart("Superior",150, Color.decode("#03A9F4")));

        GroupLayout layout = new GroupLayout(pieChartPn);
        pieChartPn.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(pieChart, -1, 300, 32767).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(pieChart, -1, 300, 32767).addContainerGap()));

        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.X_AXIS)); // Vertical layout
        // Add legend items
        addLegendItem(legendPanel, Color.decode("#D2B48C"), "Standard");
        addLegendItem(legendPanel, Color.decode("#FFC107"), "Deluxe");
        addLegendItem(legendPanel, Color.decode("#03A9F4"), "Superior");

        JLabel title = new JLabel("Loại phòng được đặt trong tuần");
        title.setFont(ColorAndFont.TILE_FONT);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(title,BorderLayout.NORTH);
        mainPanel.add(pieChartPn, BorderLayout.CENTER);
        mainPanel.add(legendPanel, BorderLayout.SOUTH);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy=1;
        c.gridx=0;
        c.weightx=0.4;
        c.weighty=0;
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.BOTH;
        add(mainPanel,c);
    }
    private void addLegendItem(JPanel legendPanel, Color color, String labelText) {
        // Create a panel for each legend item
        JPanel legendItemPanel = new JPanel();
        legendItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        legendItemPanel.setBackground(Color.WHITE);
        // Create a color box to represent the color
        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20)); // Size of the color box
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional border
        // Create a label for the text
        JLabel label = new JLabel(labelText);

        // Add the color box and label to the legend item panel
        legendItemPanel.add(colorBox);
        legendItemPanel.add(label);

        // Add the legend item panel to the legend panel
        legendPanel.add(legendItemPanel);
    }
    private void initDoanhThuBarChart() {
        Chart chart = new Chart();
        chart.addLegend("Legend 1", Color.decode("#560BAD"));
        chart.addData(new ModelChart("Thứ 2", new double[]{10000000}));
        chart.addData(new ModelChart("Thứ 3", new double[]{1000000}));
        chart.addData(new ModelChart("Thứ 4", new double[]{0}));
        chart.addData(new ModelChart("Thứ 5", new double[]{0}));
        chart.addData(new ModelChart("Thứ 6", new double[]{0}));
        chart.addData(new ModelChart("Thứ 7", new double[]{0}));
        chart.addData(new ModelChart("Chủ Nhật", new double[]{20000000}));
        chart.start();
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> {
            chart.clear();
            chart.addData(new ModelChart("Thứ 2", new double[]{0}));
            chart.addData(new ModelChart("Thứ 3", new double[]{0}));
            chart.addData(new ModelChart("Thứ 4", new double[]{20000000}));
            chart.addData(new ModelChart("Thứ 5", new double[]{0}));
            chart.addData(new ModelChart("Thứ 6", new double[]{10000000}));
            chart.addData(new ModelChart("Thứ 7", new double[]{10000000}));
            chart.addData(new ModelChart("Chủ Nhật", new double[]{0}));
            chart.start();
        });
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.add(refresh);

        JLabel title = new JLabel("Doanh thu trong tuần");
        title.setFont(ColorAndFont.TILE_FONT);
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(title,BorderLayout.CENTER);
        northPanel.add(refreshPanel, BorderLayout.EAST);
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(northPanel,BorderLayout.NORTH);
        mainPanel.add(chart, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.gridy=1;
        c.gridx=1;
        c.weightx=0.4;
        c.weighty=0;
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.BOTH;
        add(mainPanel,c);
    }
    private void initTopDichVu() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel title  =new JLabel("Top dịch vụ được sử dụng trong tuần");
        title.setFont(ColorAndFont.TEXT_FONT);
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy=0;
        gbc.gridx=0;
        gbc.weightx=1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);
        content.add(new CompletedItem("Dich vu 1", 78),gbc);
        gbc.gridy++;
        content.add(new CompletedItem("Dich vu 2", 65),gbc);
        gbc.gridy++;
        content.add(new CompletedItem("Dich vu 3", 50),gbc);
        gbc.gridy++;
        content.add(new CompletedItem("Dich vu 4", 40),gbc);
        gbc.gridy++;
        gbc.weighty= 1;
        gbc.fill = GridBagConstraints.BOTH;
        content.add(new JPanel(),gbc);

        mainPanel.add(title,BorderLayout.NORTH);
        mainPanel.add(content,BorderLayout.CENTER);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy=1;
        c.gridx=2;
        c.weightx=0;
        c.weighty=0;
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.BOTH;
        add(mainPanel,c);
    }
    private void initTableDonDatPhongGanDay(){
        // don dat phong gan day
        JPanel north = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        north.add(new JLabel("Đơn đặt phòng "),c);
        c.gridx = 1;
        c.weightx = 0;
        JButton viewALL;
        north.add(viewALL = new JButton("Xem tất cả"),c);
//        viewALL.addActionListener(e->{
//            Main1 parent=  (Main1) SwingUtilities.getWindowAncestor(this);
//                if(nvdn.getLoaiNhanVien().getMaLoai().equalsIgnoreCase("L002")){
//                                parent.selectedMenu(3,"TraCuuPhieuDatPhong");
//
//                }
//                else {
//                                parent.selectedMenu(5,"TraCuuPhieuDatPhong");
//
//                }
//           // show dialog
//            //-------------------------------------------------------------------------------------------------------------------------------------------
//        });

        // center
        String[] columns = new String[]{"Mã phiếu","Tên khách hàng", "Ngày đặt", "Ngày nhận", "Ngày trả","Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(null,columns);
        table = new JTable(model);
        ArrayList<ArrayList<String>> data= new ArrayList<>();
//        ArrayList<ArrayList<String>> data = PhieuDatPhongDAO.get5DonDatPhongGanNhat_Truong();
//        data.add(new ArrayList<>(Arrays.asList(new String[]{"1","Nguyen Van A","2021-10-10","2021-10-11","2021-10-12","Đã nhận"})));
//        data.add(new ArrayList<>(Arrays.asList(new String[]{"2","Nguyen Van B","2021-10-10","2021-10-11","2021-10-12","Đã nhận"})));
//        data.add(new ArrayList<>(Arrays.asList(new String[]{"3","Nguyen Van C","2021-10-10","2021-10-11","2021-10-12","Đã nhận"})));
//        data.add(new ArrayList<>(Arrays.asList(new String[]{"4","Nguyen Van D","2021-10-10","2021-10-11","2021-10-12","Đã nhận"})));
//        data.add(new ArrayList<>(Arrays.asList(new String[]{"5","Nguyen Van E","2021-10-10","2021-10-11","2021-10-12","Đã nhận"})));
        for(int i=0;i<5 ;i++){
            if(data.size()<=i)
                break;
            ArrayList<String> row = data.get(i);
            model.addRow(row.toArray());
        }
        JPanel center = new JPanel(new BorderLayout());
        center.add(new JScrollPane(table),BorderLayout.CENTER);

        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(north,BorderLayout.NORTH);
        mainPanel.add(center,BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy=2;
        gbc.gridx=0;
        gbc.weightx=0.6;
        gbc.weighty=1;
        gbc.gridwidth=2;
        gbc.insets= new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;
        add(mainPanel,gbc);
    }
    private void initTaskList(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Danh sách công việc");
        title.setFont(ColorAndFont.TEXT_FONT);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.add(new TaskItem(new Date(),"Task 1","Details 1",true));
        content.add(new TaskItem(new Date(),"Task 2","Details 2",false));
        content.add(new TaskItem(new Date(),"Task 3","Details 3",true));

        mainPanel.add(title,BorderLayout.NORTH);
        mainPanel.add(content,BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy=2;
        gbc.gridx=2;
        gbc.weightx=0;
        gbc.weighty=0;
        gbc.gridwidth=2;
        gbc.insets= new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;
        add(mainPanel,gbc);
    }
//    public  void loadDataSoLuongPhongTheoTrangThai(){
//        Map<String,Integer> mapSoLuongPhong= PhongDAO.getSoLuongPhongTheoTrangThai_Truong();
//        for(Map.Entry<String,Integer> entry: mapSoLuongPhong.entrySet()){
//            for(itemSoLuongPhong item: listItem){
//                if(item==null)
//                    return;
//                if(itemSoLuongPhong.Type.getValue( item.getType()).equals(entry.getKey())){
//                    item.setSoLuong(entry.getValue());
//                    break;
//                }
//            }
//        }
//    }
//    public  void loadDataSoLuongPhongTheoLoai(){
//        DecimalFormat df = new DecimalFormat("#%");
//        ArrayList<ArrayList<String>> data = PhongDAO.getSoLuongPhongTheoLoaiPhong_Truong();
//        Map<String,ArrayList<Integer>> mapData = new HashMap<>();// arraylist 0 la tong so luong, 1 la so luong dang su dung
//        for(ArrayList<String> item : data){
//            //0 la loai phong, 1 la so luong, 2 trang thai
//            if(!mapData.containsKey(item.getFirst())){
//                mapData.put(item.getFirst(),new ArrayList<>(Arrays.asList(new Integer[]{0,0})));
//            }
//            if(item.get(2).equals("Đang sử dụng"))
//                mapData.get(item.getFirst()).set(1,mapData.get(item.getFirst()).get(1)+Integer.parseInt(item.get(1)));
//            mapData.get(item.getFirst()).set(0,mapData.get(item.getFirst()).get(0)+Integer.parseInt(item.get(1)));
//            Double percent = (double) mapData.get(item.getFirst()).get(1) / mapData.get(item.getFirst()).get(0);
//            // percent
//            mapSoLuongPhongTheoLoai.get(item.getFirst()).getFirst().setText(df.format(percent));
//            //total
//            mapSoLuongPhongTheoLoai.get(item.getFirst()).get(1).setText(mapData.get(item.getFirst()).get(0).toString());
//            //value
//            mapSoLuongPhongTheoLoai.get(item.getFirst()).get(2).setText(mapData.get(item.getFirst()).get(1).toString());
//            this.setSize((int)this.getSize().getWidth()+1,(int)this.getSize().getHeight()+1);
//            this.setSize((int)this.getSize().getWidth()-1,(int)this.getSize().getHeight()-1);
//        }
//    }
//    public void loadDataDoanhThuDuKien(){
//        Double soTienDuKien = PhieuDatPhongDAO.getDoanhThuDuKienTheoThoiGian_Truong(LocalDateTime.now(),LocalDateTime.now());
//        listDoanhThuDuKien.getFirst().setText(FormatThings.formatMoney(soTienDuKien));
//        Double soTienThucTe = PhieuDatPhongDAO.getDoanhThuTheoThoiGian_Truong(LocalDateTime.now(),LocalDateTime.now());
//        listDoanhThuDuKien.get(1).setText(FormatThings.formatMoney(soTienThucTe));
//
//        int tiLeHoanThanh = (int) (soTienThucTe/(soTienDuKien+1) *100);
//        String dau = tiLeHoanThanh>10000?"> ":" ";
//        listDoanhThuDuKien.get(2).setText("<html><div style='text-align: center;'>Tỉ lệ<br>hoàn thành "+dau +  Math.min(tiLeHoanThanh,10000) + " %"+"</div></html>");
//        if(Math.round(soTienThucTe/soTienDuKien *100) <25 )
//            listDoanhThuDuKien.getLast().setIcon(new ImageIcon(getClass().getResource("../src/icon/sad.png")));
//        else if (Math.round(soTienThucTe/soTienDuKien *100) <50)
//            listDoanhThuDuKien.getLast().setIcon(new ImageIcon(getClass().getResource("../src/icon/confused.png")));
//        else if (Math.round(soTienThucTe/soTienDuKien *100) <75)
//            listDoanhThuDuKien.getLast().setIcon(new ImageIcon(getClass().getResource("../src/icon/happy.png")));
//        else
//            listDoanhThuDuKien.getLast().setIcon(new ImageIcon(getClass().getResource("../src/icon/love.png")));{
//        }
//    }
//    public void loadDonDatPhongGanDay(){
//        ArrayList<ArrayList<String>> data = PhieuDatPhongDAO.getDonDatPhongTheoThoiGian_Truong(LocalDateTime.now(),LocalDateTime.now());
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//        for(int i=0;i<5 ;i++){
//            if(data.size()<=i)
//                break;
//            ArrayList<String> row = data.get(i);
//            model.addRow(row.toArray());
//        }
//    }
//    public void loadKPI(){
//        Double tongDoanhThu = PhieuDatPhongDAO.getDoanhThuTheoThoiGian_Truong(LocalDateTime.now().withDayOfMonth(1),LocalDateTime.now());
//        int endOfMonth = LocalDateTime.now().withDayOfMonth(1).plusMonths(1).minusDays(1).getDayOfMonth();
//        if((mucTieuTongDoanhThu- tongDoanhThu)/(endOfMonth - LocalDateTime.now().getDayOfMonth())==0)
//            mucTieuHomNay.setText("Vượt mục tiêu "+ FormatThings.formatMoney((tongDoanhThu-mucTieuTongDoanhThu)/100000*100000));
//        else
//            mucTieuHomNay = new JLabel(FormatThings.formatMoney(Math.ceil((mucTieuTongDoanhThu- tongDoanhThu)/(endOfMonth - LocalDateTime.now().getDayOfMonth())/100000)*100000));
//        pieChart.clearData();
//        pieChart.addData(new ModelPieChart("Doanh thu", tongDoanhThu, Color.decode("#76F050")));
//        pieChart.addData(new ModelPieChart("Tiền còn thiếu", mucTieuTongDoanhThu-tongDoanhThu, Color.decode("#F05050")));
//    }
//    public void loadDoanhThuTrongTuan(){
//        LocalDateTime today = LocalDateTime.now();
//        Double  doanhThu = PhieuDatPhongDAO.getDoanhThuTheoThoiGian_Truong(today.with(DayOfWeek.MONDAY),today.with(DayOfWeek.SUNDAY));
//        doanhThuTheoTuan.setText(FormatThings.formatMoney(doanhThu));
//
//        Double doanhThuTuanTruoc = PhieuDatPhongDAO.getDoanhThuTheoThoiGian_Truong(today.with(DayOfWeek.MONDAY).minusWeeks(1),today.with(DayOfWeek.SUNDAY).minusWeeks(1));
//        Double tangTruong = (doanhThu - doanhThuTuanTruoc)/doanhThuTuanTruoc;
//        DecimalFormat df = new DecimalFormat("#% ");
//        phanTramTangTruong.setText(df.format(tangTruong));
//        if(tangTruong >= 0){
//            phanTramTangTruong.setIcon(FontIcon.of(BoxiconsRegular.TRENDING_UP,20));
//            phanTramTangTruong.setBackground(Color.GREEN);
//        }
//
//        else if(tangTruong < 0) {
//            phanTramTangTruong.setIcon((FontIcon.of(BoxiconsRegular.TRENDING_DOWN,20)));
//            phanTramTangTruong.setBackground(Color.RED);
//        }
//        doanhThuSoVoiTuanTruoc.setText(" so với tuần trước ( "+FormatThings.formatMoney(doanhThuTuanTruoc)+" )");
//    }
//    public void loadKhachHangMoi(){
//        ArrayList<KhachHang> ds=  KhachHangDAO.getDanhSachKhachHang_Truong();
//        for (int i = 0; i < 5; i++) {
//            ArrayList<JLabel> newKhachHang = new ArrayList<>();
//            String ten = ds.get(ds.size()-i-1).getHoTen();
//            String[] w = ten.split(" ");
//            String firstLetter = w[w.length - 1].substring(0, 1);
//            // anh dai dien
//            listKhachHangMoi.get(i).getFirst().setText(firstLetter);
//            // ten khach hang
//            listKhachHangMoi.get(i).get(1).setText(ten);
//            // email
//            listKhachHangMoi.get(i).get(2).setText(ds.get(ds.size() -i-1).getEmail());
//        }
//    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JScrollPane scrollPane = new JScrollPane(new TrangChuGUI());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}

