package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.Impl.ChiTietDonDatPhongDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class RevenueChartController {
    @FXML private ComboBox<String> timeRangeComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private BarChart<String, Number> revenueBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    private boolean isChangeComboBox = false;
    private ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    public void initialize() {
        chiTietDonDatPhongDAO= new ChiTietDonDatPhongDAOImpl();
        // Thiết lập các tùy chọn cho ComboBox
        ObservableList<String> timeRanges = FXCollections.observableArrayList(
                "Không","Tuần nay", "Tháng nay", "Năm nay"
        );
        timeRangeComboBox.setItems(timeRanges);
        timeRangeComboBox.getSelectionModel().selectFirst();
        timeRangeComboBox.setOnAction(event -> {
            isChangeComboBox = true;
            updateChartForSelectedRange();
        });
        // Thiết lập ngày mặc định
        startDatePicker.setValue(LocalDate.now().minusDays(7));
        endDatePicker.setValue(LocalDate.now());

        // Tải dữ liệu mặc định
        updateChartForSelectedRange();
    }

    @FXML
    private void handleViewButton() {
        updateChartForSelectedRange();
    }

    private void updateChartForSelectedRange() {
        revenueBarChart.getData().clear();

        String selectedRange = timeRangeComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (isChangeComboBox && selectedRange != null) {
            switch (selectedRange) {
                case "Tuần nay":
                    showWeeklyRevenue();
                    break;
                case "Tháng nay":
                    showMonthlyRevenueByWeeks();
                    break;
                case "Năm nay":
                    showYearlyRevenue();
                    break;
                default:
                    break;
            }
        } else if (startDate != null && endDate != null) {
            showCustomDateRangeRevenue(startDate, endDate);
        }
        isChangeComboBox=false;
    }

    private void showWeeklyRevenue() {
        xAxis.setLabel("Ngày trong tuần");
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            String dayName = date.getDayOfWeek().toString();
            double revenue = getRevenueForDate(date); // Hàm lấy doanh thu từ CSDL

            series.getData().add(new XYChart.Data<>(dayName, revenue));
        }

        revenueBarChart.getData().add(series);
    }

    private void showMonthlyRevenueByWeeks() {
        xAxis.setLabel("Tuần trong tháng");
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        LocalDate today = LocalDate.now();
        int weeksInMonth = (int) Math.ceil(today.lengthOfMonth() / 7.0);

        for (int week = 1; week <= weeksInMonth; week++) {
            double revenue = getRevenueForWeek(today.getMonthValue(), today.getYear(), week);
            series.getData().add(new XYChart.Data<>("Tuần " + week, revenue));
        }

        revenueBarChart.getData().add(series);
    }

    private void showYearlyRevenue() {
        xAxis.setLabel("Tháng trong năm");
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Month month : Month.values()) {
            double revenue = getRevenueForMonth(month.getValue(), LocalDate.now().getYear());
            series.getData().add(new XYChart.Data<>(month.toString(), revenue));
        }

        revenueBarChart.getData().add(series);
    }

    private void showCustomDateRangeRevenue(LocalDate startDate, LocalDate endDate) {
        xAxis.setLabel("Ngày từ " + startDate + " đến " + endDate);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);

        if (daysBetween <= 7) {
            // Hiển thị theo ngày nếu khoảng thời gian ngắn
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                double revenue = getRevenueForDate(date);
                series.getData().add(new XYChart.Data<>(date.toString(), revenue));
            }
        } else if (daysBetween <= 31) {
            // Hiển thị theo tuần nếu khoảng thời gian trung bình
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int startWeek = startDate.get(weekFields.weekOfWeekBasedYear());
            int endWeek = endDate.get(weekFields.weekOfWeekBasedYear());

            for (int week = startWeek; week <= endWeek; week++) {
                double revenue = getRevenueForWeekRange(startDate, endDate, week);
                series.getData().add(new XYChart.Data<>("Tuần " + week, revenue));
            }
        } else {
            // Hiển thị theo tháng nếu khoảng thời gian dài
            for (LocalDate date = startDate; !date.isAfter(endDate);
                 date = date.withDayOfMonth(1).plusMonths(1)) {
                double revenue = getRevenueForMonth(date.getMonthValue(), date.getYear());
                series.getData().add(new XYChart.Data<>(
                        date.getMonth().toString() + " " + date.getYear(), revenue));
            }
        }

        revenueBarChart.getData().add(series);
    }

    // Các hàm giả lập lấy dữ liệu từ CSDL
    private double getRevenueForDate(LocalDate date) {
        // Thực tế sẽ query từ CSDL
        return chiTietDonDatPhongDAO.getTongTienByNgay(date, date);
    }

    private double getRevenueForWeek(int month, int year, int week) {
        // Thực tế sẽ query từ CSDL
        LocalDate startDate = LocalDate.of(year, month, 1)
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), week);
        LocalDate endDate = startDate.plusDays(6);
        return chiTietDonDatPhongDAO.getTongTienByNgay(startDate, endDate);
    }

    private double getRevenueForMonth(int month, int year) {
        // Thực tế sẽ query từ CSDL
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return chiTietDonDatPhongDAO.getTongTienByNgay(startDate, endDate);
    }

    private double getRevenueForWeekRange(LocalDate startDate, LocalDate endDate, int week) {
        // Thực tế sẽ query từ CSDL
        LocalDate startOfWeek = startDate.with(WeekFields.of(Locale.getDefault()).weekOfMonth(), week);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        if (endOfWeek.isAfter(endDate)) {
            endOfWeek = endDate;
        }
        return chiTietDonDatPhongDAO.getTongTienByNgay(startOfWeek, endOfWeek);
    }
}