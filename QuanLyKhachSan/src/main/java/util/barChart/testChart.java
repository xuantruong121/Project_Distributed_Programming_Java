package util.barChart;

import javax.swing.*;
import java.awt.*;

public class testChart {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bar Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        Chart chart = new Chart();
        chart.addLegend("Legend 1", Color.RED);
//        chart.addLegend("Legend 2", Color.BLUE);
        chart.addData(new ModelChart("Data 1", new double[]{10}));
        chart.addData(new ModelChart("Data 2", new double[]{30}));
        chart.start();
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> {
            chart.clear();
            chart.addData(new ModelChart("Data 3", new double[]{50}));
            chart.addData(new ModelChart("Data 4", new double[]{70}));
            chart.start();
        });

        frame.add(chart, BorderLayout.CENTER);
        frame.add(refresh, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
