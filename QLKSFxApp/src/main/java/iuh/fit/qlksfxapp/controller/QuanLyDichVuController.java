package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.DichVu;
import iuh.fit.qlksfxapp.Entity.LoaiDichVu;
import iuh.fit.qlksfxapp.DAO.DichVuDAO;
import iuh.fit.qlksfxapp.DAO.LoaiDichVuDAO;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class QuanLyDichVuController {

    // TableView và các cột
    @FXML
    private TableView<DichVu> serviceTableView;
    @FXML
    private TableColumn<DichVu, String> maDichVuColumn;
    @FXML
    private TableColumn<DichVu, String> tenDichVuColumn;
    @FXML
    private TableColumn<DichVu, String> donViTinhColumn;
    @FXML
    private TableColumn<DichVu, Double> giaDichVuColumn;
    @FXML
    private TableColumn<DichVu, String> hinhAnhColumn;
    @FXML
    private TableColumn<DichVu, String> moTaColumn;
    @FXML
    private TableColumn<DichVu, Boolean> trangThaiColumn;
    @FXML
    private TableColumn<DichVu, LoaiDichVu> loaiDichVuColumn;

    // Các trường nhập liệu
    @FXML
    private TextField maDichVuField;
    @FXML
    private TextField tenDichVuField;
    @FXML
    private TextField donViTinhField;
    @FXML
    private TextField giaDichVuField;
    @FXML
    private TextArea moTaField;
    @FXML
    private ComboBox<String> trangThaiComboBox;
    @FXML
    private ComboBox<LoaiDichVu> loaiDichVuComboBox;
    @FXML
    private ImageView hinhAnhImageView;
    @FXML
    private Button chonAnhButton;

    // Các nút chức năng
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button refreshButton;

    // Tìm kiếm
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> searchTypeComboBox;
    @FXML
    private Button searchButton;

    // Dữ liệu cho danh sách dịch vụ
    private final ObservableList<DichVu> dichVuList = FXCollections.observableArrayList();
    private final ObservableList<LoaiDichVu> loaiDichVuList = FXCollections.observableArrayList();

    // DAO objects
    private final DichVuDAO dichVuDAO = new DichVuDAO();
    private final LoaiDichVuDAO loaiDichVuDAO = new LoaiDichVuDAO();
    private final GeneralDAO generalDAO = new GeneralDAO();

    // Biến lưu đường dẫn hình ảnh
    private String selectedImagePath = null;

    @FXML
    private void initialize() {
        // Khởi tạo ComboBox
        trangThaiComboBox.setItems(FXCollections.observableArrayList("Đang hoạt động", "Ngừng cung cấp"));
        searchTypeComboBox.setItems(FXCollections.observableArrayList("Mã DV", "Tên dịch vụ", "Loại DV"));
        searchTypeComboBox.setValue("Tên dịch vụ");

        // Load danh sách loại dịch vụ từ database
        loadLoaiDichVu();

        // Khởi tạo TableView
        setupTableColumns();

        // Load dữ liệu dịch vụ từ database
        loadDichVuData();

        // Thiết lập sự kiện khi chọn một dòng trong TableView
        serviceTableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayServiceDetails(newSelection);
                    }
                });

        // Thiết lập sự kiện cho các nút
        setupButtonActions();

        // Set giá trị mặc định
        clearForm();
    }

    private void setupButtonActions() {
        // Nút chọn ảnh
        chonAnhButton.setOnAction(event -> handleChooseImage());

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

    private void loadLoaiDichVu() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng GeneralDAO để lấy danh sách loại dịch vụ
                    List<LoaiDichVu> result = generalDAO.findAll(LoaiDichVu.class);

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        loaiDichVuList.clear();
                        loaiDichVuList.addAll(result);
                        loaiDichVuComboBox.setItems(loaiDichVuList);

                        // Tùy chỉnh cách hiển thị tên loại dịch vụ trong ComboBox
                        loaiDichVuComboBox.setConverter(new StringConverter<>() {
                            @Override
                            public String toString(LoaiDichVu loaiDV) {
                                if (loaiDV == null) {
                                    return null;
                                }
                                return loaiDV.getTenLoaiDichVu();
                            }

                            @Override
                            public LoaiDichVu fromString(String string) {
                                if (string == null || string.isEmpty() || loaiDichVuList.isEmpty()) {
                                    return null;
                                }
                                // Tìm loại dịch vụ có tên tương ứng
                                for (LoaiDichVu loaiDV : loaiDichVuList) {
                                    if (loaiDV.getTenLoaiDichVu().equals(string)) {
                                        return loaiDV;
                                    }
                                }
                                return null;
                            }
                        });
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() ->
                            showAlert(Alert.AlertType.ERROR, "Lỗi",
                                    "Không thể tải danh sách loại dịch vụ: " + e.getMessage())
                    );
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách loại dịch vụ: "
                    + e.getMessage());
        }
    }

    private void setupTableColumns() {
        maDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("maDichVu"));
        tenDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        donViTinhColumn.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        giaDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("giaDichVu"));
        hinhAnhColumn.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
        moTaColumn.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        trangThaiColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        loaiDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("loaiDichVu"));

        // Tùy chỉnh hiển thị cho cột trạng thái
        trangThaiColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Đang hoạt động" : "Ngừng cung cấp");
                }
            }
        });

        // Tùy chỉnh hiển thị cho cột loại dịch vụ
        loaiDichVuColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LoaiDichVu item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTenLoaiDichVu());
                }
            }
        });

        // Thiết lập TableView
        serviceTableView.setItems(dichVuList);
    }

    private void loadDichVuData() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng GeneralDAO để lấy danh sách dịch vụ
                    List<DichVu> result = generalDAO.findAll(DichVu.class);

                    // Đảm bảo tất cả các trường dữ liệu được tải đầy đủ
                    for (DichVu dv : result) {
                        if (dv.getLoaiDichVu() != null) {
                            // Truy cập để đảm bảo dữ liệu được tải
                            dv.getLoaiDichVu().getTenLoaiDichVu();
                        }
                    }

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        dichVuList.clear();
                        dichVuList.addAll(result);
                        serviceTableView.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() ->
                            showAlert(Alert.AlertType.ERROR, "Lỗi",
                                    "Không thể tải danh sách dịch vụ: " + e.getMessage())
                    );
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách dịch vụ: "
                    + e.getMessage());
        }
    }

    private void displayServiceDetails(DichVu dichVu) {
        maDichVuField.setText(dichVu.getMaDichVu());
        tenDichVuField.setText(dichVu.getTenDichVu());
        donViTinhField.setText(dichVu.getDonViTinh());
        giaDichVuField.setText(String.valueOf(dichVu.getGiaDichVu()));
        moTaField.setText(dichVu.getMoTa());
        trangThaiComboBox.setValue(dichVu.isTrangThai() ? "Đang hoạt động" : "Ngừng cung cấp");
        loaiDichVuComboBox.setValue(dichVu.getLoaiDichVu());

        // Lấy số từ mã dịch vụ (DV01, DV02, ...)
        String maDV = dichVu.getMaDichVu();
        if (maDV != null && maDV.startsWith("DV") && maDV.length() >= 4) {
            try {
                int soThuTu = Integer.parseInt(maDV.substring(2));
                if (soThuTu >= 1 && soThuTu <= 8) {
                    // Nếu mã dịch vụ từ DV01 đến DV08, sử dụng hình ảnh tương ứng
                    // Định dạng đường dẫn: /images/DV01.jpg, /images/DV02.jpg, ...
                    String imagePath = "/images/DV" + String.format("%02d", soThuTu) + ".jpg";
                    try {
                        // Kiểm tra xem resource có tồn tại không trước khi tạo Image
                        java.io.InputStream inputStream = getClass().getResourceAsStream(imagePath);
                        if (inputStream != null) {
                            Image image = new Image(inputStream);
                            hinhAnhImageView.setImage(image);
                            selectedImagePath = imagePath;
                            return; // Thoát khỏi phương thức nếu đã tìm thấy hình ảnh
                        } else {
                            // Nếu không tìm thấy resource, sử dụng placeHolder
                            java.io.InputStream placeholderStream = getClass().getResourceAsStream("/images/placeHolder.jpg");
                            if (placeholderStream != null) {
                                hinhAnhImageView.setImage(new Image(placeholderStream));
                            } else {
                                // Nếu cả placeHolder cũng không tìm thấy, đặt image là null
                                hinhAnhImageView.setImage(null);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Nếu có lỗi, thử sử dụng placeHolder
                        try {
                            java.io.InputStream placeholderStream = getClass().getResourceAsStream("/images/placeHolder.jpg");
                            if (placeholderStream != null) {
                                hinhAnhImageView.setImage(new Image(placeholderStream));
                            } else {
                                hinhAnhImageView.setImage(null);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            hinhAnhImageView.setImage(null);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                // Không làm gì, tiếp tục với các trường hợp khác
            }
        }

        // Nếu không phải DV01-DV08, kiểm tra hình ảnh từ đường dẫn
        if (dichVu.getHinhAnh() != null && !dichVu.getHinhAnh().isEmpty()) {
            try {
                File imageFile = new File(dichVu.getHinhAnh());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    hinhAnhImageView.setImage(image);
                    selectedImagePath = dichVu.getHinhAnh();
                } else {
                    // Nếu file không tồn tại, hiển thị ảnh placeHolder
                    java.io.InputStream placeholderStream = getClass().getResourceAsStream("/images/placeHolder.jpg");
                    if (placeholderStream != null) {
                        hinhAnhImageView.setImage(new Image(placeholderStream));
                    } else {
                        hinhAnhImageView.setImage(null);
                    }
                    selectedImagePath = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Nếu có lỗi, hiển thị ảnh placeHolder
                try {
                    java.io.InputStream placeholderStream = getClass().getResourceAsStream("/images/placeHolder.jpg");
                    if (placeholderStream != null) {
                        hinhAnhImageView.setImage(new Image(placeholderStream));
                    } else {
                        hinhAnhImageView.setImage(null);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    hinhAnhImageView.setImage(null);
                }
                selectedImagePath = null;
            }
        } else {
            // Nếu không có hình ảnh, hiển thị ảnh placeHolder
            try {
                java.io.InputStream placeholderStream = getClass().getResourceAsStream("/images/placeHolder.jpg");
                if (placeholderStream != null) {
                    hinhAnhImageView.setImage(new Image(placeholderStream));
                } else {
                    hinhAnhImageView.setImage(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                hinhAnhImageView.setImage(null);
            }
            selectedImagePath = null;
        }
    }

    private void clearForm() {
        maDichVuField.clear();
        tenDichVuField.clear();
        donViTinhField.clear();
        giaDichVuField.clear();
        moTaField.clear();
        trangThaiComboBox.setValue("Đang hoạt động");
        loaiDichVuComboBox.setValue(null);
        try {
            java.io.InputStream placeholderStream = getClass().getResourceAsStream("/images/placeHolder.jpg");
            if (placeholderStream != null) {
                hinhAnhImageView.setImage(new Image(placeholderStream));
            } else {
                hinhAnhImageView.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hinhAnhImageView.setImage(null);
        }
        selectedImagePath = null;

        // Bỏ chọn dòng trong TableView
        serviceTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        String searchType = searchTypeComboBox.getValue();

        if (keyword.isEmpty()) {
            serviceTableView.setItems(dichVuList);
            return;
        }

        ObservableList<DichVu> filteredList = FXCollections.observableArrayList();

        for (DichVu dv : dichVuList) {
            boolean match = switch (searchType) {
                case "Mã DV" -> dv.getMaDichVu().toLowerCase().contains(keyword);
                case "Tên dịch vụ" -> dv.getTenDichVu().toLowerCase().contains(keyword);
                case "Loại DV" -> dv.getLoaiDichVu().getTenLoaiDichVu().toLowerCase().contains(keyword);
                default -> false;
            };

            if (match) {
                filteredList.add(dv);
            }
        }

        serviceTableView.setItems(filteredList);
    }

    @FXML
    private void handleRefresh() {
        clearForm();
        loadDichVuData();
        searchField.clear();
        serviceTableView.setItems(dichVuList);
    }

    @FXML
    private void handleAdd() {
        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tạo đối tượng dịch vụ mới
            DichVu newService = new DichVu();
            setServiceInfo(newService);

            // Lưu vào database sử dụng GeneralDAO
            generalDAO.addOb(newService);

            // Cập nhật danh sách và làm mới form
            loadDichVuData();
            clearForm();

            // Hiển thị thông báo
            showAlert(Alert.AlertType.INFORMATION, "Thêm dịch vụ", "Thêm dịch vụ thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm dịch vụ: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        // Kiểm tra xem đã chọn dịch vụ chưa
        DichVu selectedService = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn dịch vụ cần cập nhật!");
            return;
        }

        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Cập nhật thông tin dịch vụ
            setServiceInfo(selectedService);

            // Lưu vào database sử dụng GeneralDAO
            generalDAO.updateOb(selectedService);

            // Cập nhật danh sách và làm mới form
            loadDichVuData();
            clearForm();

            // Hiển thị thông báo
            showAlert(Alert.AlertType.INFORMATION, "Cập nhật dịch vụ", "Cập nhật dịch vụ thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật dịch vụ: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        // Kiểm tra xem đã chọn dịch vụ chưa
        DichVu selectedService = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn dịch vụ cần xóa!");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xác nhận xóa");
        confirmDialog.setHeaderText("Xóa dịch vụ");
        confirmDialog.setContentText("Bạn có chắc chắn muốn xóa dịch vụ này không?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Xóa dịch vụ khỏi database
                generalDAO.deleteOb(selectedService);

                // Cập nhật danh sách và làm mới form
                loadDichVuData();
                clearForm();

                // Hiển thị thông báo
                showAlert(Alert.AlertType.INFORMATION, "Xóa dịch vụ", "Xóa dịch vụ thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa dịch vụ: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(chonAnhButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                hinhAnhImageView.setImage(image);
                selectedImagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải hình ảnh: " + e.getMessage());
            }
        }
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        // Kiểm tra tên dịch vụ
        if (tenDichVuField.getText().trim().isEmpty()) {
            errorMessage.append("Tên dịch vụ không được để trống!\n");
        }

        // Kiểm tra đơn vị tính
        if (donViTinhField.getText().trim().isEmpty()) {
            errorMessage.append("Đơn vị tính không được để trống!\n");
        }

        // Kiểm tra giá dịch vụ
        try {
            double giaDichVu = Double.parseDouble(giaDichVuField.getText().trim());
            if (giaDichVu < 0) {
                errorMessage.append("Giá dịch vụ không được âm!\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Giá dịch vụ phải là số!\n");
        }

        // Kiểm tra loại dịch vụ
        if (loaiDichVuComboBox.getValue() == null) {
            errorMessage.append("Vui lòng chọn loại dịch vụ!\n");
        }

        // Hiển thị thông báo lỗi nếu có
        if (!errorMessage.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void setServiceInfo(DichVu dichVu) {
        // Không cập nhật mã dịch vụ nếu đã có (khi cập nhật)
        // Mã dịch vụ sẽ được tạo tự động bởi phương thức prePersist trong Entity

        dichVu.setTenDichVu(tenDichVuField.getText().trim());
        dichVu.setDonViTinh(donViTinhField.getText().trim());
        dichVu.setGiaDichVu(Double.parseDouble(giaDichVuField.getText().trim()));
        dichVu.setMoTa(moTaField.getText().trim());
        dichVu.setTrangThai(trangThaiComboBox.getValue().equals("Đang hoạt động"));
        dichVu.setLoaiDichVu(loaiDichVuComboBox.getValue());
        dichVu.setHinhAnh(selectedImagePath);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
