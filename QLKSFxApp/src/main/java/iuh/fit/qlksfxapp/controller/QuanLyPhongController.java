package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class QuanLyPhongController {

    // TableView và các cột
    @FXML
    private TableView<Phong> roomTableView;
    @FXML
    private TableColumn<Phong, String> maPhongColumn;
    @FXML
    private TableColumn<Phong, String> viTriColumn;
    @FXML
    private TableColumn<Phong, String> hinhAnhColumn;
    @FXML
    private TableColumn<Phong, String> moTaColumn;
    @FXML
    private TableColumn<Phong, TrangThaiPhong> trangThaiPhongColumn;
    @FXML
    private TableColumn<Phong, LoaiPhong> loaiPhongColumn;

    // Các trường nhập liệu
    @FXML
    private TextField maPhongField;
    @FXML
    private TextField viTriField;
    @FXML
    private TextArea moTaField;
    @FXML
    private ComboBox<TrangThaiPhong> trangThaiPhongComboBox;
    @FXML
    private ComboBox<LoaiPhong> loaiPhongComboBox;
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
    private Button refreshButton;

    // Tìm kiếm
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> searchTypeComboBox;
    @FXML
    private Button searchButton;

    // Biến lưu đường dẫn hình ảnh đã chọn
    private String selectedImagePath;

    // Dữ liệu cho danh sách phòng
    private final ObservableList<Phong> phongList = FXCollections.observableArrayList();
    private final ObservableList<LoaiPhong> loaiPhongList = FXCollections.observableArrayList();

    // DAO object
    private final GeneralDAO generalDAO = new GeneralDAO();

    @FXML
    private void initialize() {
        // Khởi tạo ComboBox tìm kiếm
        searchTypeComboBox.setItems(FXCollections.observableArrayList("Mã phòng", "Vị trí", "Loại phòng"));
        searchTypeComboBox.setValue("Vị trí");

        // Khởi tạo ComboBox trạng thái phòng
        trangThaiPhongComboBox.setItems(FXCollections.observableArrayList(TrangThaiPhong.values()));
        trangThaiPhongComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TrangThaiPhong item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.s);
                }
            }
        });
        trangThaiPhongComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TrangThaiPhong item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.s);
                }
            }
        });

        // Load danh sách loại phòng từ database
        loadLoaiPhong();

        // Khởi tạo TableView
        setupTableColumns();

        // Load dữ liệu phòng từ database
        loadPhongData();

        // Thiết lập sự kiện khi chọn một dòng trong TableView
        roomTableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayRoomDetails(newSelection);
                    }
                });

        // Thiết lập sự kiện cho các nút
        setupButtonActions();

        // Set giá trị mặc định
        clearForm();
    }

    private void setupButtonActions() {
        // Nút tìm kiếm
        searchButton.setOnAction(event -> handleSearch());

        // Nút làm mới
        refreshButton.setOnAction(event -> handleRefresh());

        // Nút chọn ảnh
        chonAnhButton.setOnAction(event -> handleChooseImage());

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
        maPhongColumn.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        viTriColumn.setCellValueFactory(new PropertyValueFactory<>("viTri"));
        hinhAnhColumn.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
        moTaColumn.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        trangThaiPhongColumn.setCellValueFactory(new PropertyValueFactory<>("trangThaiPhong"));
        loaiPhongColumn.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));

        // Tùy chỉnh hiển thị cho cột trạng thái phòng
        trangThaiPhongColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(TrangThaiPhong item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.s);
                }
            }
        });

        // Tùy chỉnh hiển thị cho cột loại phòng
        loaiPhongColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LoaiPhong item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTenLoaiPhong());
                }
            }
        });

        // Tùy chỉnh hiển thị cho cột hình ảnh
        hinhAnhColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.isEmpty()) {
                    setText("Không có");
                } else {
                    setText("Có");
                }
            }
        });

        // Thiết lập TableView
        roomTableView.setItems(phongList);
    }

    private void loadLoaiPhong() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng GeneralDAO để lấy danh sách loại phòng
                    List<LoaiPhong> result = generalDAO.findAll(LoaiPhong.class);

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        loaiPhongList.clear();
                        loaiPhongList.addAll(result);
                        loaiPhongComboBox.setItems(loaiPhongList);

                        // Thiết lập cell factory cho ComboBox loại phòng
                        loaiPhongComboBox.setCellFactory(param -> new ListCell<>() {
                            @Override
                            protected void updateItem(LoaiPhong item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                } else {
                                    setText(item.getTenLoaiPhong());
                                }
                            }
                        });

                        // Thiết lập button cell cho ComboBox loại phòng
                        loaiPhongComboBox.setButtonCell(new ListCell<>() {
                            @Override
                            protected void updateItem(LoaiPhong item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                } else {
                                    setText(item.getTenLoaiPhong());
                                }
                            }
                        });
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách loại phòng: " + e.getMessage());
                    });
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách loại phòng: " + e.getMessage());
        }
    }

    private void loadPhongData() {
        try {
            // Sử dụng thread riêng để tải dữ liệu
            Thread thread = new Thread(() -> {
                try {
                    // Sử dụng GeneralDAO để lấy danh sách phòng
                    List<Phong> result = generalDAO.findAll(Phong.class);

                    // Cập nhật UI trên thread chính
                    javafx.application.Platform.runLater(() -> {
                        phongList.clear();
                        phongList.addAll(result);
                        roomTableView.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách phòng: " + e.getMessage());
                    });
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách phòng: " + e.getMessage());
        }
    }

    private void displayRoomDetails(Phong phong) {
        maPhongField.setText(phong.getMaPhong());
        viTriField.setText(phong.getViTri());
        moTaField.setText(phong.getMoTa());
        trangThaiPhongComboBox.setValue(phong.getTrangThaiPhong());

        // Tìm và chọn loại phòng trong ComboBox
        for (LoaiPhong lp : loaiPhongList) {
            if (lp.getMaLoaiPhong().equals(phong.getLoaiPhong().getMaLoaiPhong())) {
                loaiPhongComboBox.setValue(lp);
                break;
            }
        }

        // Hiển thị hình ảnh dựa trên mã phòng
        String maPhong = phong.getMaPhong();
        try {
            // Tìm hình ảnh trong thư mục resources/images/room
            // Kiểm tra các định dạng hình ảnh phổ biến
            String[] extensions = {".jpg", ".jpeg", ".png", ".gif"};
            boolean imageFound = false;

            // Kiểm tra trong thư mục resources/images/room
            for (String ext : extensions) {
                String imagePath = "/images/room/" + maPhong + ext;
                try {
                    // Kiểm tra xem file có tồn tại trong resources không
                    if (getClass().getResource(imagePath) != null) {
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                        hinhAnhImageView.setImage(image);
                        selectedImagePath = imagePath;
                        imageFound = true;
                        System.out.println("Đã tìm thấy hình ảnh: " + imagePath);
                        break;
                    }
                } catch (Exception e) {
                    // Bỏ qua và tiếp tục kiểm tra định dạng khác
                }
            }

            // Nếu không tìm thấy hình ảnh trong resources, kiểm tra đường dẫn cũ (nếu có)
            if (!imageFound && phong.getHinhAnh() != null && !phong.getHinhAnh().isEmpty()) {
                File imageFile = new File(phong.getHinhAnh());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    hinhAnhImageView.setImage(image);
                    selectedImagePath = phong.getHinhAnh();
                    imageFound = true;
                }
            }

            // Nếu vẫn không tìm thấy hình ảnh, hiển thị ảnh placeHolder
            if (!imageFound) {
                hinhAnhImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeHolder.jpg"))));
                selectedImagePath = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, hiển thị ảnh placeHolder
            hinhAnhImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeHolder.jpg"))));
            selectedImagePath = null;
        }
    }

    private void clearForm() {
        maPhongField.clear();
        viTriField.clear();
        moTaField.clear();
        if (!trangThaiPhongComboBox.getItems().isEmpty()) {
            trangThaiPhongComboBox.setValue(TrangThaiPhong.TRONG);
        }
        if (!loaiPhongComboBox.getItems().isEmpty()) {
            loaiPhongComboBox.setValue(loaiPhongComboBox.getItems().get(0));
        }
        hinhAnhImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeHolder.jpg"))));
        selectedImagePath = null;
    }

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(chonAnhButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Tạo thư mục images/room nếu chưa tồn tại
                Path roomImagesDir = Paths.get("src/main/resources/images/room");
                if (!Files.exists(roomImagesDir)) {
                    Files.createDirectories(roomImagesDir);
                }

                // Lấy mã phòng từ trường nhập liệu
                String maPhong = maPhongField.getText().trim();
                if (maPhong.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập mã phòng trước khi chọn hình ảnh.");
                    return;
                }

                // Lấy phần mở rộng của file
                String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                // Tạo tên file mới dựa trên mã phòng
                String fileName = maPhong + extension;


                // Copy file hình ảnh vào thư mục resources/images/room
                Path roomTargetPath = roomImagesDir.resolve(fileName);
                Files.copy(selectedFile.toPath(), roomTargetPath, StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn hình ảnh và hiển thị
                // Sử dụng đường dẫn tương đối cho resources
                selectedImagePath = "/images/room/" + fileName;
                Image image = new Image(roomTargetPath.toUri().toString());
                hinhAnhImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể lưu hình ảnh: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        String searchType = searchTypeComboBox.getValue();

        if (keyword.isEmpty()) {
            roomTableView.setItems(phongList);
            return;
        }

        ObservableList<Phong> filteredList = FXCollections.observableArrayList();

        for (Phong p : phongList) {
            boolean match = switch (searchType) {
                case "Mã phòng" -> p.getMaPhong().toLowerCase().contains(keyword);
                case "Vị trí" -> p.getViTri().toLowerCase().contains(keyword);
                case "Loại phòng" -> p.getLoaiPhong().getTenLoaiPhong().toLowerCase().contains(keyword);
                default -> false;
            };

            if (match) {
                filteredList.add(p);
            }
        }

        roomTableView.setItems(filteredList);
    }

    @FXML
    private void handleRefresh() {
        clearForm();
        loadPhongData();
        searchField.clear();
        roomTableView.setItems(phongList);
    }

    @FXML
    private void handleAdd() {
        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tạo đối tượng phòng mới
            Phong newRoom = new Phong();
            setRoomInfo(newRoom);

            // Lưu vào database sử dụng GeneralDAO
            generalDAO.addOb(newRoom);

            // Cập nhật danh sách và làm mới form
            loadPhongData();
            clearForm();

            // Hiển thị thông báo
            showAlert(Alert.AlertType.INFORMATION, "Thêm phòng", "Thêm phòng thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm phòng: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Phong selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn phòng cần cập nhật!");
            return;
        }

        // Kiểm tra dữ liệu nhập
        if (!validateInput()) {
            return;
        }

        try {
            // Tìm phòng trong database sử dụng GeneralDAO
            Phong roomToUpdate = generalDAO.findOb(Phong.class, selectedRoom.getMaPhong());

            if (roomToUpdate != null) {
                // Cập nhật thông tin
                setRoomInfo(roomToUpdate);

                // Sử dụng DAO để cập nhật phòng
                generalDAO.updateOb(roomToUpdate);

                // Cập nhật danh sách
                loadPhongData();

                // Hiển thị thông báo
                showAlert(Alert.AlertType.INFORMATION, "Cập nhật phòng", "Cập nhật phòng thành công!");
            } else {
                throw new Exception("Không tìm thấy phòng với mã " + selectedRoom.getMaPhong());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật phòng: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Phong selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn phòng cần xóa!");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xác nhận xóa");
        confirmDialog.setHeaderText("Xóa phòng");
        confirmDialog.setContentText("Bạn có chắc chắn muốn xóa phòng " + selectedRoom.getMaPhong() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Tìm phòng trong database sử dụng GeneralDAO
                Phong roomToDelete = generalDAO.findOb(Phong.class, selectedRoom.getMaPhong());

                if (roomToDelete != null) {
                    // Sử dụng DAO để xóa phòng
                    generalDAO.deleteOb(roomToDelete);

                    // Cập nhật danh sách và làm mới form
                    loadPhongData();
                    clearForm();

                    // Hiển thị thông báo
                    showAlert(Alert.AlertType.INFORMATION, "Xóa phòng", "Xóa phòng thành công!");
                } else {
                    throw new Exception("Không tìm thấy phòng với mã " + selectedRoom.getMaPhong());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa phòng: " + e.getMessage());
            }
        }
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        // Kiểm tra vị trí
        if (viTriField.getText().trim().isEmpty()) {
            errorMessage.append("Vị trí không được để trống!\n");
        }

        // Kiểm tra trạng thái phòng
        if (trangThaiPhongComboBox.getValue() == null) {
            errorMessage.append("Trạng thái phòng không được để trống!\n");
        }

        // Kiểm tra loại phòng
        if (loaiPhongComboBox.getValue() == null) {
            errorMessage.append("Loại phòng không được để trống!\n");
        }

        if (!errorMessage.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void setRoomInfo(Phong phong) {
        String maPhong = maPhongField.getText().trim();
        phong.setMaPhong(maPhong);
        phong.setViTri(viTriField.getText().trim());
        phong.setMoTa(moTaField.getText().trim());
        phong.setTrangThaiPhong(trangThaiPhongComboBox.getValue());
        phong.setLoaiPhong(loaiPhongComboBox.getValue());

        // Nếu đã chọn hình ảnh mới, sử dụng đường dẫn đó
        // Nếu chưa chọn hình ảnh, tạo đường dẫn dựa trên mã phòng
        // Đường dẫn sẽ được tạo theo quy ước: /images/room/[mã phòng].jpg
        phong.setHinhAnh(Objects.requireNonNullElseGet(selectedImagePath, () -> "/images/room/" + maPhong + ".jpg"));
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}