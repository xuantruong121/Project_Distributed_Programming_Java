package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.LoaiNhanVien;
import iuh.fit.qlksfxapp.Entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import iuh.fit.qlksfxapp.DAO.NhanVienDAO;
import iuh.fit.qlksfxapp.DAO.LoaiNhanVienDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.util.StringConverter;

public class QuanLyNhanVienController {
    // Form fields
    @FXML
    private TextField maNhanVienField;
    @FXML
    private TextField tenNhanVienField;
    @FXML
    private DatePicker ngaySinhPicker;
    @FXML
    private RadioButton namRadioButton;
    @FXML
    private RadioButton nuRadioButton;
    @FXML
    private TextField soDienThoaiField;
    @FXML
    private TextField canCuocCongDanField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField diaChiField;
    @FXML
    private ComboBox<LoaiNhanVien> loaiNhanVienComboBox;
    @FXML
    private ComboBox<String> trangThaiComboBox;

    // Search fields
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> searchTypeComboBox;

    // Buttons
    @FXML
    private Button addButton;

    // TableView
    @FXML
    private TableView<NhanVien> staffTableView;
    @FXML
    private TableColumn<NhanVien, String> maNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> tenNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, LocalDate> ngaySinhColumn;
    @FXML
    private TableColumn<NhanVien, Boolean> gioiTinhColumn;
    @FXML
    private TableColumn<NhanVien, String> soDienThoaiColumn;
    @FXML
    private TableColumn<NhanVien, String> canCuocCongDanColumn;
    @FXML
    private TableColumn<NhanVien, String> emailColumn;
    @FXML
    private TableColumn<NhanVien, String> diaChiColumn;
    @FXML
    private TableColumn<NhanVien, LoaiNhanVien> loaiNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> trangThaiColumn;

    // Dữ liệu cho danh sách nhân viên
    private final ObservableList<NhanVien> nhanVienList = FXCollections.observableArrayList();
    private final ObservableList<LoaiNhanVien> loaiNhanVienList = FXCollections.observableArrayList();

    // DAO objects
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final LoaiNhanVienDAO loaiNhanVienDAO = new LoaiNhanVienDAO();

    @FXML
    private void initialize() {
        // Khởi tạo ComboBox
        trangThaiComboBox.setItems(FXCollections.observableArrayList("Đang làm việc", "Nghỉ việc", "Tạm nghỉ"));
        searchTypeComboBox.setItems(FXCollections.observableArrayList("Mã NV", "Họ tên", "CCCD",
                "Số điện thoại", "Loại NV"));
        searchTypeComboBox.setValue("Họ tên");

        // Load danh sách loại nhân viên từ database
        loadLoaiNhanVien();

        // Khởi tạo TableView
        setupTableColumns();

        // Load dữ liệu nhân viên từ database
        loadNhanVienData();

        // Thiết lập sự kiện khi chọn một dòng trong TableView
        staffTableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayStaffDetails(newSelection);
                    }
                });

        // Set giá trị mặc định
        clearForm();
    }

    private void loadLoaiNhanVien() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng LoaiNhanVienDAO để lấy danh sách loại nhân viên
                    List<LoaiNhanVien> result = loaiNhanVienDAO.findAll(LoaiNhanVien.class);

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        loaiNhanVienList.clear();
                        loaiNhanVienList.addAll(result);
                        loaiNhanVienComboBox.setItems(loaiNhanVienList);

                        // Tùy chỉnh cách hiển thị tên loại nhân viên trong ComboBox
                        loaiNhanVienComboBox.setConverter(new StringConverter<>() {
                            @Override
                            public String toString(LoaiNhanVien loaiNV) {
                                if (loaiNV == null) {
                                    return null;
                                }
                                return loaiNV.getTenLoaiNhanVien();
                            }

                            @Override
                            public LoaiNhanVien fromString(String string) {
                                if (string == null || string.isEmpty() || loaiNhanVienList.isEmpty()) {
                                    return null;
                                }
                                // Tìm loại nhân viên có tên tương ứng
                                for (LoaiNhanVien loaiNV : loaiNhanVienList) {
                                    if (loaiNV.getTenLoaiNhanVien().equals(string)) {
                                        return loaiNV;
                                    }
                                }
                                return null;
                            }
                        });

                        // Thiết lập cell factory để hiển thị tên loại nhân viên trong dropdown
                        loaiNhanVienComboBox.setCellFactory(param -> new ListCell<>() {
                            @Override
                            protected void updateItem(LoaiNhanVien item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                } else {
                                    setText(item.getTenLoaiNhanVien());
                                }
                            }
                        });

                        if (!loaiNhanVienList.isEmpty()) {
                            loaiNhanVienComboBox.setValue(loaiNhanVienList.getFirst());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Lỗi",
                            "Không thể tải danh sách loại nhân viên: "
                                    + e.getMessage()));
                }
            });

            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách loại nhân viên: "
                    + e.getMessage());
        }
    }

    private void loadNhanVienData() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng NhanVienDAO để lấy danh sách nhân viên
                    List<NhanVien> result = nhanVienDAO.getAllNhanVien();

                    // Đảm bảo tất cả các trường dữ liệu được tải đầy đủ
                    for (NhanVien nv : result) {
                        if (nv.getLoaiNhanVien() != null) {
                            // Truy cập để đảm bảo dữ liệu được tải
                            nv.getLoaiNhanVien().getTenLoaiNhanVien();
                        }
                        // Đảm bảo trạng thái được tải
                        if (nv.getTrangThai() == null) {
                            nv.setTrangThai("");
                        }
                    }

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        nhanVienList.clear();
                        nhanVienList.addAll(result);
                        staffTableView.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Lỗi",
                            "Không thể tải danh sách nhân viên: "
                                    + e.getMessage()));
                }
            });

            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách nhân viên: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        tenNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));

        // Hiển thị "Nam" hoặc "Nữ" thay vì true/false
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
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

        soDienThoaiColumn.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        canCuocCongDanColumn.setCellValueFactory(new PropertyValueFactory<>("canCuocCongDan"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        // Hiển thị tên loại nhân viên thay vì đối tượng LoaiNhanVien
        loaiNhanVienColumn.setCellValueFactory(cellData -> {
            NhanVien nv = cellData.getValue();
            if (nv != null) {
                LoaiNhanVien loaiNV = nv.getLoaiNhanVien();
                return new javafx.beans.property.SimpleObjectProperty<>(loaiNV);
            }
            return new javafx.beans.property.SimpleObjectProperty<>(null);
        });
        loaiNhanVienColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LoaiNhanVien item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTenLoaiNhanVien());
                }
            }
        });

        // Hiển thị trạng thái nhân viên
        trangThaiColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        trangThaiColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        staffTableView.setItems(nhanVienList);
    }

    private void displayStaffDetails(NhanVien nhanVien) {
        maNhanVienField.setText(nhanVien.getMaNhanVien());
        tenNhanVienField.setText(nhanVien.getTenNhanVien());
        ngaySinhPicker.setValue(nhanVien.getNgaySinh());

        if (nhanVien.isGioiTinh()) {
            namRadioButton.setSelected(true);
        } else {
            nuRadioButton.setSelected(true);
        }

        soDienThoaiField.setText(nhanVien.getSoDienThoai());
        canCuocCongDanField.setText(nhanVien.getCanCuocCongDan());
        emailField.setText(nhanVien.getEmail());
        diaChiField.setText(nhanVien.getDiaChi());
        // Đặt giá trị cho ComboBox loại nhân viên
        LoaiNhanVien loaiNV = nhanVien.getLoaiNhanVien();
        if (loaiNV != null) {
            // Tìm loại nhân viên tương ứng trong danh sách
            for (LoaiNhanVien lnv : loaiNhanVienList) {
                if (lnv.getMaLoaiNhanVien().equals(loaiNV.getMaLoaiNhanVien())) {
                    loaiNhanVienComboBox.setValue(lnv);
                    break;
                }
            }
        }
        trangThaiComboBox.setValue(nhanVien.getTrangThai());
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        String searchType = searchTypeComboBox.getValue();

        if (keyword.isEmpty()) {
            staffTableView.setItems(nhanVienList);
            return;
        }

        ObservableList<NhanVien> filteredList = FXCollections.observableArrayList();

        for (NhanVien nv : nhanVienList) {
            boolean match = switch (searchType) {
                case "Mã NV" -> nv.getMaNhanVien().toLowerCase().contains(keyword);
                case "Họ tên" -> nv.getTenNhanVien().toLowerCase().contains(keyword);
                case "CCCD" -> nv.getCanCuocCongDan().toLowerCase().contains(keyword);
                case "Số điện thoại" -> nv.getSoDienThoai().contains(keyword);
                case "Loại NV" -> nv.getLoaiNhanVien().getTenLoaiNhanVien().toLowerCase().contains(keyword);
                default -> false;
            };

            if (match) {
                filteredList.add(nv);
            }
        }

        staffTableView.setItems(filteredList);
    }

    @FXML
    private void handleRefresh() {
        clearForm();
        loadNhanVienData();
        searchField.clear();
        staffTableView.setItems(nhanVienList);
    }

    @FXML
    private void handleAdd() {
        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tạo đối tượng nhân viên mới
            NhanVien newStaff = new NhanVien();
            setStaffInfo(newStaff);

            // Lưu vào database sử dụng NhanVienDAO
            // Sử dụng DAO để lưu nhân viên mới
            nhanVienDAO.addOb(newStaff);

            // Cập nhật danh sách và làm mới form
            loadNhanVienData();
            clearForm();

            // Hiển thị thông báo
            showAlert(Alert.AlertType.INFORMATION, "Thêm nhân viên", "Thêm nhân viên thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm nhân viên: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        NhanVien selectedStaff = staffTableView.getSelectionModel().getSelectedItem();

        if (selectedStaff == null) {
            showAlert(Alert.AlertType.WARNING, "Cập nhật nhân viên", "Vui lòng chọn nhân viên cần cập nhật!");
            return;
        }

        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tìm nhân viên trong database sử dụng NhanVienDAO
            NhanVien staffToUpdate = nhanVienDAO.findByMaNhanVien(selectedStaff.getMaNhanVien());

            if (staffToUpdate != null) {
                // Cập nhật thông tin
                setStaffInfo(staffToUpdate);

                // Sử dụng DAO để cập nhật nhân viên
                nhanVienDAO.updateOb(staffToUpdate);

                // Cập nhật danh sách
                loadNhanVienData();

                // Hiển thị thông báo
                showAlert(Alert.AlertType.INFORMATION, "Cập nhật nhân viên", "Cập nhật nhân viên thành công!");
            } else {
                throw new Exception("Không tìm thấy nhân viên với mã " + selectedStaff.getMaNhanVien());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật nhân viên: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        NhanVien selectedStaff = staffTableView.getSelectionModel().getSelectedItem();

        if (selectedStaff == null) {
            showAlert(Alert.AlertType.WARNING, "Xóa nhân viên", "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xác nhận xóa");
        confirmDialog.setHeaderText("Xóa nhân viên");
        confirmDialog.setContentText("Bạn có chắc chắn muốn xóa nhân viên " + selectedStaff.getTenNhanVien() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Tìm nhân viên trong database sử dụng NhanVienDAO
                NhanVien staffToDelete = nhanVienDAO.findByMaNhanVien(selectedStaff.getMaNhanVien());

                if (staffToDelete != null) {
                    // Sử dụng DAO để xóa nhân viên
                    nhanVienDAO.deleteOb(staffToDelete);

                    // Cập nhật danh sách và làm mới form
                    loadNhanVienData();
                    clearForm();

                    // Hiển thị thông báo
                    showAlert(Alert.AlertType.INFORMATION, "Xóa nhân viên", "Xóa nhân viên thành công!");
                } else {
                    throw new Exception("Không tìm thấy nhân viên với mã " + selectedStaff.getMaNhanVien());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa nhân viên: " + e.getMessage());
            }
        }
    }

    /**
     * Thiết lập thông tin nhân viên từ các trường nhập liệu
     *
     * @param staff Đối tượng nhân viên cần thiết lập thông tin
     */
    private void setStaffInfo(NhanVien staff) {
        staff.setTenNhanVien(tenNhanVienField.getText());
        staff.setNgaySinh(ngaySinhPicker.getValue());
        staff.setGioiTinh(namRadioButton.isSelected());
        staff.setSoDienThoai(soDienThoaiField.getText());
        staff.setCanCuocCongDan(canCuocCongDanField.getText());
        staff.setEmail(emailField.getText());
        staff.setDiaChi(diaChiField.getText());
        staff.setLoaiNhanVien(loaiNhanVienComboBox.getValue());
        staff.setTrangThai(trangThaiComboBox.getValue());
    }

    /**
     * Kiểm tra dữ liệu nhập
     *
     * @return false nếu dữ liệu không hợp lệ, true nếu hợp lệ
     */
    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        // Kiểm tra tên nhân viên
        if (tenNhanVienField.getText().trim().isEmpty()) {
            errorMessage.append("- Tên nhân viên không được để trống\n");
        }

        // Kiểm tra ngày sinh
        if (ngaySinhPicker.getValue() == null) {
            errorMessage.append("- Ngày sinh không được để trống\n");
        } else {
            LocalDate currentDate = LocalDate.now();
            if (ngaySinhPicker.getValue().isAfter(currentDate)) {
                errorMessage.append("- Ngày sinh không hợp lệ (sau ngày hiện tại)\n");
            }

            // Kiểm tra tuổi tối thiểu (18 tuổi)
            LocalDate minAgeDate = currentDate.minusYears(18);
            if (ngaySinhPicker.getValue().isAfter(minAgeDate)) {
                errorMessage.append("- Nhân viên phải đủ 18 tuổi\n");
            }
        }

        // Kiểm tra số điện thoại
        String phoneNumber = soDienThoaiField.getText().trim();
        if (phoneNumber.isEmpty()) {
            errorMessage.append("- Số điện thoại không được để trống\n");
        } else if (!phoneNumber.matches("\\d{10}")) {
            errorMessage.append("- Số điện thoại phải có 10 chữ số\n");
        }

        // Kiểm tra căn cước công dân
        String cccd = canCuocCongDanField.getText().trim();
        if (cccd.isEmpty()) {
            errorMessage.append("- CCCD không được để trống\n");
        } else if (!cccd.matches("\\d{12}")) {
            errorMessage.append("- CCCD phải có 12 chữ số\n");
        }

        // Kiểm tra email
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            errorMessage.append("- Email không được để trống\n");
        } else if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage.append("- Email không hợp lệ\n");
        }

        // Kiểm tra địa chỉ
        if (diaChiField.getText().trim().isEmpty()) {
            errorMessage.append("- Địa chỉ không được để trống\n");
        }

        // Kiểm tra loại nhân viên
        if (loaiNhanVienComboBox.getValue() == null) {
            errorMessage.append("- Vui lòng chọn loại nhân viên\n");
        }

        // Kiểm tra trạng thái
        if (trangThaiComboBox.getValue() == null) {
            errorMessage.append("- Vui lòng chọn trạng thái\n");
        }

        // Hiển thị thông báo lỗi nếu có
        if (!errorMessage.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi dữ liệu", "Vui lòng sửa các lỗi sau:\n"
                    + errorMessage);
            return false;
        }

        return true;
    }

    private void clearForm() {
        maNhanVienField.clear();
        tenNhanVienField.clear();
        ngaySinhPicker.setValue(null);
        namRadioButton.setSelected(true);
        soDienThoaiField.clear();
        canCuocCongDanField.clear();
        emailField.clear();
        diaChiField.clear();

        if (!loaiNhanVienList.isEmpty()) {
            loaiNhanVienComboBox.setValue(loaiNhanVienList.getFirst());
        } else {
            loaiNhanVienComboBox.setValue(null);
        }

        trangThaiComboBox.setValue("Đang làm việc");

        // Cho phép thêm mới
        addButton.setDisable(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
