package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.NhanVien;
import iuh.fit.qlksfxapp.Entity.TaiKhoan;
import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import iuh.fit.qlksfxapp.model.WorkSchedule;

public class UserProfileController {
    @FXML
    private ImageView profileImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label staffIdLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label staffTypeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label idCardLabel;
    @FXML
    private Button changeImageButton;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button editProfileButton;

    @FXML
    private TableView<WorkSchedule> scheduleTableView;
    @FXML
    private TableColumn<WorkSchedule, LocalDate> dateColumn;
    @FXML
    private TableColumn<WorkSchedule, String> shiftColumn;
    @FXML
    private TableColumn<WorkSchedule, LocalTime> startTimeColumn;
    @FXML
    private TableColumn<WorkSchedule, LocalTime> endTimeColumn;
    @FXML
    private TableColumn<WorkSchedule, String> locationColumn;
    @FXML
    private TableColumn<WorkSchedule, String> statusColumn;
    @FXML
    private TableColumn<WorkSchedule, String> notesColumn;

    private NhanVien currentUser;
    private GeneralDAOImpl generalDAOImpl;
    private final ObservableList<WorkSchedule> scheduleList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        generalDAOImpl = new GeneralDAOImpl();

        // Trong thực tế, bạn sẽ lấy thông tin người dùng hiện tại từ session hoặc service
        // Ở đây tôi sẽ giả lập bằng cách lấy một nhân viên từ database
        // Trong ứng dụng thực tế, bạn sẽ cần triển khai một UserSession để lưu thông tin người dùng đăng nhập

        // Ví dụ: currentUser = UserSession.getInstance().getCurrentUser();
        // Hoặc truyền thông tin người dùng từ MainController

        // Thiết lập các cột cho bảng ca làm việc
        setupScheduleTable();

        // Tạo dữ liệu mẫu cho bảng ca làm việc
        loadSampleScheduleData();
    }

    private void setupScheduleTable() {
        // Thiết lập các cột cho bảng ca làm việc
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        // Định dạng hiển thị ngày tháng
        dateColumn.setCellFactory(column -> new javafx.scene.control.TableCell<WorkSchedule, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        // Định dạng hiển thị giờ
        startTimeColumn.setCellFactory(column ->
                new javafx.scene.control.TableCell<>() {
                    @Override
                    protected void updateItem(LocalTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.format(DateTimeFormatter.ofPattern("HH:mm")));
                        }
                    }
                });

        endTimeColumn.setCellFactory(column -> new javafx.scene.control.TableCell<WorkSchedule, LocalTime>() {
            @Override
            protected void updateItem(LocalTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("HH:mm")));
                }
            }
        });

        // Gắn danh sách dữ liệu vào bảng
        scheduleTableView.setItems(scheduleList);
    }

    private void loadSampleScheduleData() {
        // Tạo dữ liệu mẫu cho bảng ca làm việc
        scheduleList.add(new WorkSchedule(
                LocalDate.now(),
                "Sáng",
                LocalTime.of(7, 0),
                LocalTime.of(11, 30),
                "Lễ tân",
                "Hoàn thành",
                "Tiếp đón khách VIP"));

        scheduleList.add(new WorkSchedule(
                LocalDate.now().plusDays(1),
                "Chiều",
                LocalTime.of(13, 0),
                LocalTime.of(17, 30),
                "Phòng hội nghị",
                "Chưa bắt đầu",
                "Hỗ trợ sự kiện công ty ABC"));

        scheduleList.add(new WorkSchedule(
                LocalDate.now().plusDays(2),
                "Tối",
                LocalTime.of(18, 0),
                LocalTime.of(22, 0),
                "Nhà hàng",
                "Chưa bắt đầu",
                "Phục vụ tiệc cưới"));

        scheduleList.add(new WorkSchedule(
                LocalDate.now().plusDays(3),
                "Sáng",
                LocalTime.of(7, 0),
                LocalTime.of(11, 30),
                "Lễ tân",
                "Chưa bắt đầu",
                ""));

        scheduleList.add(new WorkSchedule(
                LocalDate.now().plusDays(4),
                "Nghỉ",
                null,
                null,
                "",
                "Nghỉ phép",
                "Nghỉ phép đã được duyệt"));
    }

    public void setUserData(NhanVien nhanVien) {
        this.currentUser = nhanVien;

        if (currentUser != null) {
            // Cập nhật thông tin hiển thị
            nameLabel.setText(currentUser.getTenNhanVien());
            fullNameLabel.setText(currentUser.getTenNhanVien());
            staffIdLabel.setText(currentUser.getMaNhanVien());
            phoneLabel.setText(currentUser.getSoDienThoai());
            emailLabel.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "Chưa cập nhật");
            addressLabel.setText(currentUser.getDiaChi());
            idCardLabel.setText(currentUser.getCanCuocCongDan());

            if (currentUser.getNgaySinh() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dobLabel.setText(currentUser.getNgaySinh().format(formatter));
            } else {
                dobLabel.setText("Chưa cập nhật");
            }

            genderLabel.setText(currentUser.isGioiTinh() ? "Nam" : "Nữ");

            if (currentUser.getLoaiNhanVien() != null) {
                staffTypeLabel.setText(currentUser.getLoaiNhanVien().getTenLoaiNhanVien());
                roleLabel.setText(currentUser.getLoaiNhanVien().getTenLoaiNhanVien());
            } else {
                staffTypeLabel.setText("Chưa phân loại");
                roleLabel.setText("Nhân viên");
            }

            statusLabel.setText(currentUser.getTrangThai());

            // Nếu có hình ảnh, bạn có thể cập nhật profileImageView
            if (currentUser.getHinhAnh() != null && !currentUser.getHinhAnh().isEmpty()) {
                try {
                    File imageFile = new File(currentUser.getHinhAnh());
                    if (imageFile.exists()) {
                        Image image = new Image(imageFile.toURI().toString());
                        profileImageView.setImage(image);
                    } else {
                        // Sử dụng hình ảnh mặc định nếu không tìm thấy file
                        Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeHolder.jpg")));
                        profileImageView.setImage(defaultImage);
                    }
                } catch (Exception e) {
                    // Sử dụng hình ảnh mặc định nếu có lỗi
                    Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeHolder.jpg")));
                    profileImageView.setImage(defaultImage);
                }
            } else {
                // Sử dụng hình ảnh mặc định nếu không có hình ảnh
                Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeHolder.jpg")));
                profileImageView.setImage(defaultImage);
            }
        }
    }

    @FXML
    private void handleChangeImage() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thay đổi hình ảnh", "Không tìm thấy thông tin người dùng.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) changeImageButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Tạo thư mục lưu trữ hình ảnh nếu chưa tồn tại
                Path imageDir = Paths.get("src/main/resources/images/users");
                if (!Files.exists(imageDir)) {
                    Files.createDirectories(imageDir);
                }

                // Tạo tên file mới dựa trên mã nhân viên
                String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                String newFileName = currentUser.getMaNhanVien() + fileExtension;
                Path targetPath = imageDir.resolve(newFileName);

                // Sao chép file vào thư mục đích
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn hình ảnh trong đối tượng nhân viên
                currentUser.setHinhAnh(targetPath.toString());

                // Cập nhật vào database
                generalDAOImpl.updateOb(currentUser);

                // Hiển thị hình ảnh mới
                Image newImage = new Image(targetPath.toUri().toString());
                profileImageView.setImage(newImage);

                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thay đổi hình ảnh", "Hình ảnh đã được cập nhật thành công.");

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thay đổi hình ảnh", "Có lỗi xảy ra: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleChangePassword() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể đổi mật khẩu", "Không tìm thấy thông tin người dùng.");
            return;
        }

        // Tạo dialog đổi mật khẩu
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Đổi mật khẩu");
        dialog.setHeaderText("Nhập mật khẩu mới cho tài khoản " + currentUser.getTenNhanVien());

        // Thêm các nút
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Tạo layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        PasswordField oldPassword = new PasswordField();
        oldPassword.setPromptText("Mật khẩu hiện tại");
        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("Mật khẩu mới");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Xác nhận mật khẩu mới");

        grid.add(new Label("Mật khẩu hiện tại:"), 0, 0);
        grid.add(oldPassword, 1, 0);
        grid.add(new Label("Mật khẩu mới:"), 0, 1);
        grid.add(newPassword, 1, 1);
        grid.add(new Label("Xác nhận mật khẩu:"), 0, 2);
        grid.add(confirmPassword, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Xử lý kết quả
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String oldPass = oldPassword.getText();
            String newPass = newPassword.getText();
            String confirmPass = confirmPassword.getText();

            // Kiểm tra mật khẩu
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Đổi mật khẩu", "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Đổi mật khẩu", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
                return;
            }

            try {
                // Lấy tài khoản của người dùng
                TaiKhoan taiKhoan = generalDAOImpl.findOb(TaiKhoan.class, currentUser.getMaNhanVien());

                if (taiKhoan != null) {
                    // Kiểm tra mật khẩu cũ
                    if (!taiKhoan.getMatKhau().equals(oldPass)) {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Đổi mật khẩu", "Mật khẩu hiện tại không đúng.");
                        return;
                    }

                    // Cập nhật mật khẩu mới
                    taiKhoan.setMatKhau(newPass);
                    generalDAOImpl.updateOb(taiKhoan);

                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đổi mật khẩu", "Mật khẩu đã được cập nhật thành công.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Đổi mật khẩu", "Không tìm thấy tài khoản của người dùng.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Đổi mật khẩu", "Có lỗi xảy ra: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleEditProfile() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể chỉnh sửa thông tin", "Không tìm thấy thông tin người dùng.");
            return;
        }

        // Hiển thị thông báo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chỉnh sửa thông tin");
        alert.setHeaderText("Tính năng đang phát triển");
        alert.setContentText("Tính năng chỉnh sửa thông tin cá nhân đang được phát triển và sẽ được cập nhật trong phiên bản tới.");
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
