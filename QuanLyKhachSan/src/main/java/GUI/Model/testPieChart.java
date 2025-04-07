//package GUI.Model;
//
//import javaswingdev.chart.ModelPieChart;
//import javaswingdev.chart.PieChart;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class testPieChart {
//    public static void main(String[] args) {
//        JPanel pieChartPn= new JPanel();
//        pieChartPn.setBackground(Color.WHITE);
//        PieChart pieChart = new PieChart();
//        pieChart.setFont(new Font("Arial",Font.BOLD,15));
//        pieChart.setChartType(PieChart.PeiChartType.DONUT_CHART);
//        pieChart.addData(new ModelPieChart("Doanh thu", 999, Color.decode("#76F050")));
//        pieChart.addData(new ModelPieChart("Tiền còn thiếu",99, Color.decode("#F05050")));
//
//
//
//        GroupLayout layout = new GroupLayout(pieChartPn);
//        pieChartPn.setLayout(layout);
//        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(pieChart, -1, 300, 32767).addContainerGap()));
//        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(pieChart, -1, 300, 32767).addContainerGap()));
//
//        JPanel legendPanel = new JPanel();
//        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.X_AXIS)); // Vertical layout
//        legendPanel.setBackground(Color.WHITE);
//        // Add legend items
//        addLegendItem(legendPanel, Color.decode("#76F050"), "Doanh thu");
//        addLegendItem(legendPanel, Color.decode("#F05050"), "Tiền còn thiếu");
//
//        JFrame frame = new JFrame();
//        frame.setLayout(new BorderLayout());
//        frame.add(pieChartPn, BorderLayout.CENTER);
//        frame.add(legendPanel, BorderLayout.SOUTH);
//        frame.setSize(500,500);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//    }
//    private static void addLegendItem(JPanel legendPanel, Color color, String labelText) {
//        // Create a panel for each legend item
//        JPanel legendItemPanel = new JPanel();
//        legendItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//
//        // Create a color box to represent the color
//        JPanel colorBox = new JPanel();
//        colorBox.setBackground(color);
//        colorBox.setPreferredSize(new Dimension(20, 20)); // Size of the color box
//        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional border
//
//        // Create a label for the text
//        JLabel label = new JLabel(labelText);
//
//        // Add the color box and label to the legend item panel
//        legendItemPanel.add(colorBox);
//        legendItemPanel.add(label);
//
//        // Add the legend item panel to the legend panel
//        legendPanel.add(legendItemPanel);
//    }
//
//}
