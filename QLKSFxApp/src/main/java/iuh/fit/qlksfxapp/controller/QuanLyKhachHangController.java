package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.KhachHang;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class QuanLyKhachHangController {

    // TableView và các cột
    @FXML
    private TableView<KhachHang> customerTableView;
    @FXML
    private TableColumn<KhachHang, String> maKhachHangColumn;
    @FXML
    private TableColumn<KhachHang, String> tenKhachHangColumn;
    @FXML
    private TableColumn<KhachHang, String> soDienThoaiColumn;
    @FXML
    private TableColumn<KhachHang, String> canCuocCongDanColumn;
    @FXML
    private TableColumn<KhachHang, String> emailColumn;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngaySinhColumn;
    @FXML
    private TableColumn<KhachHang, Boolean> gioiTinhColumn;
    @FXML
    private TableColumn<KhachHang, Integer> diemTichLuyColumn;
    @FXML
    private TableColumn<KhachHang, String> quocTichColumn;

    // Các trường nhập liệu
    @FXML
    private TextField maKhachHangField;
    @FXML
    private TextField tenKhachHangField;
    @FXML
    private TextField soDienThoaiField;
    @FXML
    private TextField canCuocCongDanField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker ngaySinhPicker;
    @FXML
    private RadioButton namRadioButton;
    @FXML
    private RadioButton nuRadioButton;
    @FXML
    private ToggleGroup gioiTinhGroup;
    @FXML
    private TextField diemTichLuyField;
    @FXML
    private TextField quocTichField;

    // Các nút chức năng
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;

    // Tìm kiếm
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> searchTypeComboBox;
    @FXML
    private Button searchButton;

    // Dữ liệu cho danh sách khách hàng
    private final ObservableList<KhachHang> khachHangList = FXCollections.observableArrayList();

    // DAO object
    private final GeneralDAO generalDAO = new GeneralDAO();

    @FXML
    private void initialize() {
        // Khởi tạo ComboBox tìm kiếm
        searchTypeComboBox.setItems(FXCollections.observableArrayList("Mã KH", "Tên khách hàng", "Số điện thoại", "CCCD"));
        searchTypeComboBox.setValue("Tên khách hàng");

        // Khởi tạo DatePicker với định dạng ngày Việt Nam
        setupDatePicker();

        // Khởi tạo TableView
        setupTableColumns();

        // Load dữ liệu khách hàng từ database
        loadKhachHangData();

        // Thiết lập sự kiện khi chọn một dòng trong TableView
        customerTableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayCustomerDetails(newSelection);
                    }
                });

        // Thiết lập sự kiện cho các nút
        setupButtonActions();

        // Set giá trị mặc định
        clearForm();
    }

    private void setupDatePicker() {
        // Định dạng ngày tháng kiểu Việt Nam (dd/MM/yyyy)
        StringConverter<LocalDate> converter = new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        ngaySinhPicker.setConverter(converter);
        ngaySinhPicker.setPromptText("dd/MM/yyyy");
    }

    private void setupButtonActions() {
        // Nút tìm kiếm
        searchButton.setOnAction(event -> handleSearch());

        // Nút làm mới
        refreshButton.setOnAction(event -> handleRefresh());

        // Nút thêm
        addButton.setOnAction(event -> handleAdd());

        // Nút cập nhật
        updateButton.setOnAction(event -> handleUpdate());

        // Nút xóa
        deleteButton.setOnAction(event -> handleDelete());

        // Thêm sự kiện Enter cho ô tìm kiếm
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                handleSearch();
            }
        });
    }

    private void setupTableColumns() {
        maKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        tenKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        soDienThoaiColumn.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        canCuocCongDanColumn.setCellValueFactory(new PropertyValueFactory<>("canCuocCongDan"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        diemTichLuyColumn.setCellValueFactory(new PropertyValueFactory<>("diemTichLuy"));
        quocTichColumn.setCellValueFactory(new PropertyValueFactory<>("quocTich"));

        // Tùy chỉnh hiển thị cho cột giới tính
        gioiTinhColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Nam" : "Nữ");
                }
            }
        });

        // Tùy chỉnh hiển thị cho cột ngày sinh
        ngaySinhColumn.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        // Thiết lập TableView
        customerTableView.setItems(khachHangList);
    }

    private void loadKhachHangData() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng GeneralDAO để lấy danh sách khách hàng
                    List<KhachHang> result = generalDAO.findAll(KhachHang.class);

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        khachHangList.clear();
                        khachHangList.addAll(result);
                        customerTableView.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách khách hàng: " + e.getMessage());
                    });
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách khách hàng: " + e.getMessage());
        }
    }

    private void displayCustomerDetails(KhachHang khachHang) {
        maKhachHangField.setText(khachHang.getMaKhachHang());
        tenKhachHangField.setText(khachHang.getTenKhachHang());
        soDienThoaiField.setText(khachHang.getSoDienThoai());
        canCuocCongDanField.setText(khachHang.getCanCuocCongDan());
        emailField.setText(khachHang.getEmail());
        ngaySinhPicker.setValue(khachHang.getNgaySinh());

        if (khachHang.isGioiTinh()) {
            namRadioButton.setSelected(true);
        } else {
            nuRadioButton.setSelected(true);
        }

        diemTichLuyField.setText(String.valueOf(khachHang.getDiemTichLuy()));
        quocTichField.setText(khachHang.getQuocTich());
    }

    private void clearForm() {
        maKhachHangField.clear();
        tenKhachHangField.clear();
        soDienThoaiField.clear();
        canCuocCongDanField.clear();
        emailField.clear();
        ngaySinhPicker.setValue(null);
        namRadioButton.setSelected(true);
        diemTichLuyField.setText("0");
        quocTichField.setText("Việt Nam");
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        String searchType = searchTypeComboBox.getValue();

        if (keyword.isEmpty()) {
            customerTableView.setItems(khachHangList);
            return;
        }

        ObservableList<KhachHang> filteredList = FXCollections.observableArrayList();

        for (KhachHang kh : khachHangList) {
            boolean match = switch (searchType) {
                case "Mã KH" -> kh.getMaKhachHang().toLowerCase().contains(keyword);
                case "Tên khách hàng" -> kh.getTenKhachHang().toLowerCase().contains(keyword);
                case "Số điện thoại" -> kh.getSoDienThoai().toLowerCase().contains(keyword);
                case "CCCD" -> kh.getCanCuocCongDan().toLowerCase().contains(keyword);
                default -> false;
            };

            if (match) {
                filteredList.add(kh);
            }
        }

        customerTableView.setItems(filteredList);
    }

    @FXML
    private void handleRefresh() {
        clearForm();
        loadKhachHangData();
        searchField.clear();
        customerTableView.setItems(khachHangList);
    }

    @FXML
    private void handleAdd() {
        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tạo đối tượng khách hàng mới
            KhachHang newCustomer = new KhachHang();
            setCustomerInfo(newCustomer);

            // Lưu vào database sử dụng GeneralDAO
            generalDAO.addOb(newCustomer);

            // Cập nhật danh sách và làm mới form
            loadKhachHangData();
            clearForm();

            // Hiển thị thông báo
            showAlert(Alert.AlertType.INFORMATION, "Thêm khách hàng", "Thêm khách hàng thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm khách hàng: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        KhachHang selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn khách hàng cần cập nhật!");
            return;
        }

        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tìm khách hàng trong database sử dụng GeneralDAO
            KhachHang customerToUpdate = generalDAO.findOb(KhachHang.class, selectedCustomer.getMaKhachHang());

            if (customerToUpdate != null) {
                // Cập nhật thông tin
                setCustomerInfo(customerToUpdate);

                // Sử dụng DAO để cập nhật khách hàng
                generalDAO.updateOb(customerToUpdate);

                // Cập nhật danh sách
                loadKhachHangData();

                // Hiển thị thông báo
                showAlert(Alert.AlertType.INFORMATION, "Cập nhật khách hàng", "Cập nhật khách hàng thành công!");
            } else {
                throw new Exception("Không tìm thấy khách hàng với mã " + selectedCustomer.getMaKhachHang());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật khách hàng: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        KhachHang selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn khách hàng cần xóa!");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xác nhận xóa");
        confirmDialog.setHeaderText("Xóa khách hàng");
        confirmDialog.setContentText("Bạn có chắc chắn muốn xóa khách hàng " + selectedCustomer.getTenKhachHang() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Tìm khách hàng trong database sử dụng GeneralDAO
                KhachHang customerToDelete = generalDAO.findOb(KhachHang.class, selectedCustomer.getMaKhachHang());

                if (customerToDelete != null) {
                    // Sử dụng DAO để xóa khách hàng
                    generalDAO.deleteOb(customerToDelete);

                    // Cập nhật danh sách và làm mới form
                    loadKhachHangData();
                    clearForm();

                    // Hiển thị thông báo
                    showAlert(Alert.AlertType.INFORMATION, "Xóa khách hàng", "Xóa khách hàng thành công!");
                } else {
                    throw new Exception("Không tìm thấy khách hàng với mã " + selectedCustomer.getMaKhachHang());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa khách hàng: " + e.getMessage());
            }
        }
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        // Kiểm tra tên khách hàng
        if (tenKhachHangField.getText().trim().isEmpty()) {
            errorMessage.append("Tên khách hàng không được để trống!\n");
        }

        // Kiểm tra số điện thoại
        String soDienThoai = soDienThoaiField.getText().trim();
        if (soDienThoai.isEmpty()) {
            errorMessage.append("Số điện thoại không được để trống!\n");
        } else if (!soDienThoai.matches("\\d{10}")) {
            errorMessage.append("Số điện thoại phải có 10 chữ số!\n");
        }

        // Kiểm tra căn cước công dân
        String cccd = canCuocCongDanField.getText().trim();
        if (cccd.isEmpty()) {
            errorMessage.append("Căn cước công dân không được để trống!\n");
        } else if (!cccd.matches("\\d{12}")) {
            errorMessage.append("Căn cước công dân phải có 12 chữ số!\n");
        }

        // Kiểm tra email
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            errorMessage.append("Email không được để trống!\n");
        } else if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage.append("Email không hợp lệ!\n");
        }

        // Kiểm tra ngày sinh
        if (ngaySinhPicker.getValue() == null) {
            errorMessage.append("Ngày sinh không được để trống!\n");
        } else if (ngaySinhPicker.getValue().isAfter(LocalDate.now())) {
            errorMessage.append("Ngày sinh không thể là ngày trong tương lai!\n");
        }

        // Kiểm tra điểm tích lũy
        try {
            int diemTichLuy = Integer.parseInt(diemTichLuyField.getText().trim());
            if (diemTichLuy < 0) {
                errorMessage.append("Điểm tích lũy không được âm!\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Điểm tích lũy phải là số nguyên!\n");
        }

        // Kiểm tra quốc tịch
        if (quocTichField.getText().trim().isEmpty()) {
            errorMessage.append("Quốc tịch không được để trống!\n");
        }

        if (!errorMessage.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void setCustomerInfo(KhachHang khachHang) {
        khachHang.setTenKhachHang(tenKhachHangField.getText().trim());
        khachHang.setSoDienThoai(soDienThoaiField.getText().trim());
        khachHang.setCanCuocCongDan(canCuocCongDanField.getText().trim());
        khachHang.setEmail(emailField.getText().trim());
        khachHang.setNgaySinh(ngaySinhPicker.getValue());
        khachHang.setGioiTinh(namRadioButton.isSelected());
        khachHang.setDiemTichLuy(Integer.parseInt(diemTichLuyField.getText().trim()));
        khachHang.setQuocTich(quocTichField.getText().trim());
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}