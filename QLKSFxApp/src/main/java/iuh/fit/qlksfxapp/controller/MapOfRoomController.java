package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.DonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.Impl.ChiTietDonDatPhongDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.DonDatPhongDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.PhongDAOImpl;
import iuh.fit.qlksfxapp.DAO.PhongDAO;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.EventBusManager;
import iuh.fit.qlksfxapp.controller.ItemController.InfoDetailBookingRoomController;
import iuh.fit.qlksfxapp.controller.ItemController.RoomItemController;
import iuh.fit.qlksfxapp.controller.EventBus.EmptyViewManager;
import iuh.fit.qlksfxapp.util.ManageTrangThaiPhong;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MapOfRoomController {

    @FXML private ScrollPane scrollPane;
    @FXML private GridPane gridPane;
    private ManageTrangThaiPhong manageTrangThaiPhong;
    private List<Phong> allPhong;
    private int currentPage = 0;
    private final int ITEMS_PER_PAGE = 9; // 3x3 grid
    private DonDatPhongDAOImpl donDatPhongDAO;
    private boolean isSearchFailed = false;
    private Popup popup;
    private ChiTietDonDatPhongDAOImpl chiTietDonDatPhongDAO;
    private PhongDAOImpl phongDAO;
    @FXML private DatePicker ngayDenInp,ngayDiInp;
    @FXML private TextField soNguoiLonInp,soTreEmInp,tenDoanInp,gioDenInp,gioDiInp;
    @FXML private ComboBox<String> loaiPhongCmb,viTriCmb;
    @FXML private StackPane stackPane;
    @FXML private Label trongNumber,dangSuDungNumber,dangDonDepNumber , datTruocNumber,
            suaChuaNumber,khongSudungNumber;

    @FXML
    public void initialize() {
        // Initialize the grid pane with room items
        donDatPhongDAO = new DonDatPhongDAOImpl();
        chiTietDonDatPhongDAO=new ChiTietDonDatPhongDAOImpl();
        phongDAO = new PhongDAOImpl();
        popup  = new Popup();
        popup.setAutoHide(true);
        allPhong=new ArrayList<>();

        // them view empty
        Node emptyView = EmptyViewManager.getEmptyView();
        EmptyViewManager.setMessage("Không có phòng nào");
        if (!stackPane.getChildren().contains(emptyView)) {
            stackPane.getChildren().add(emptyView);
            emptyView.toFront();
            emptyView.setVisible(false); // Ẩn view khi có dữ liệu
        }

        // Initialize formatters
        initFormater();

        // Load tất cả các phòng và khởi tạo giao diện
        showAllRooms();

        // Initialize comboboxes after loading all rooms
        initComboBox();

        // Initialize legend
        initLegend();

        // Load room items
        loadRoomItems();

        // Add scroll listener
        scrollPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) { // Cuộn đến cuối
                loadMoreItems();
            }
        });

        // Load initial items
        loadMoreItems();
    }

    private void initComboBox(){
        // Load room types directly from the database
        try {
            // Create a new LoaiPhongDAOImpl to get all room types
            iuh.fit.qlksfxapp.DAO.Impl.LoaiPhongDAOImpl loaiPhongDAO = new iuh.fit.qlksfxapp.DAO.Impl.LoaiPhongDAOImpl();
            List<String> loaiPhong = loaiPhongDAO.getAllLoaiPhong().stream()
                    .map(lp -> lp.getTenLoaiPhong())
                    .distinct()
                    .toList();

            // Lấy danh sách vị trí từ tất cả các phòng trong cơ sở dữ liệu
            iuh.fit.qlksfxapp.DAO.Impl.PhongDAOImpl phongDAOForViTri = new iuh.fit.qlksfxapp.DAO.Impl.PhongDAOImpl();
            List<String> viTri = phongDAOForViTri.getAllPhong().stream()
                    .map(Phong::getViTri)
                    .distinct()
                    .toList();
            // Đóng kết nối sau khi sử dụng
            phongDAOForViTri.closeEntityManager();

            // Populate room type combobox
            loaiPhongCmb.getItems().clear();
            loaiPhongCmb.getItems().add("--- Tất cả ---");
            loaiPhongCmb.getItems().addAll(loaiPhong);
            loaiPhongCmb.setValue("--- Tất cả ---");

            // Populate location combobox
            viTriCmb.getItems().clear();
            viTriCmb.getItems().add("--- Tất cả ---");
            viTriCmb.getItems().addAll(viTri);
            viTriCmb.setValue("--- Tất cả ---");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi", "Không thể tải danh sách loại phòng: " + e.getMessage());
        }
    }
    private void initFormater(){
        // Định dạng cho các TextField
        soNguoiLonInp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                soNguoiLonInp.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        soTreEmInp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                soTreEmInp.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    // Xóa tất cả các node con và constraints cột/hàng
    private void clearGridPane() {
        gridPane.getChildren().clear(); // Xóa tất cả các node con
        gridPane.getColumnConstraints().clear(); // Xóa constraints cột
        gridPane.getRowConstraints().clear(); // Xóa constraints hàng
        currentPage = 0; // Reset trang hiện tại
    }
    // load all phong
    private void loadRoomItems() {
        // Xóa constraints cũ nếu có
        clearGridPane(); // Xóa toàn bộ nội dung cũ

        // Kiểm tra nếu allPhong rỗng
        if (allPhong == null || allPhong.isEmpty()) {
            updateEmptyState();
            return;
        }

        // Tạo constraints cho 3 cột
        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 3);
            colConst.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(colConst);
        }

        // Tính số hàng cần thiết
        int totalRows = (int) Math.ceil((double) allPhong.size() / 3);

        // Tạo constraints cho các hàng
        for (int i = 0; i < totalRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConst);
        }

        // Load các item đầu tiên
        loadMoreItems();
        // an empty va hien thi gridpane
        updateEmptyState();
    }
    // load 9 phong
    private void loadMoreItems() {
        if (allPhong == null || allPhong.isEmpty()) return;

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allPhong.size());

        if (startIndex >= allPhong.size()) return;

        for (int i = startIndex; i < endIndex; i++) {
            Phong phong = allPhong.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/RoomItem.fxml"));
                Node item = loader.load();
                RoomItemController itemController = loader.getController();

                // tìm kiem trong manageTrangThaiPhong
                DonDatPhong donDatPhong = manageTrangThaiPhong.getDonDatPhongByMaPhong(phong.getMaPhong());
                itemController.setManageTrangThaiPhong(manageTrangThaiPhong);
                itemController.setMapOfRoomController(this);
                itemController.setData(phong, donDatPhong,this::showDonDatPhongPopup);

                item.setUserData(new ManageTrangThaiPhong.PhongDonDatWrapper(phong, donDatPhong));
                int row = i / 3;
                int col = i % 3;

                gridPane.add(item, col, row);
                GridPane.setHgrow(item, Priority.ALWAYS);
                GridPane.setVgrow(item, Priority.ALWAYS);

            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi tốt hơn thay vì throw RuntimeException
            }
        }
        currentPage++;
    }
    // Xử lý sự kiện khi nhấn nút tìm kiếm
    @FXML
    private void handleSearch(ActionEvent actionEvent) {
        isSearchFailed = false;

        // Lấy giá trị từ các trường nhập liệu
        String tenDoan = tenDoanInp.getText();
        LocalDate ngayDen = ngayDenInp.getValue();
        LocalDate ngayDi = ngayDiInp.getValue();
        String gioDen = gioDenInp.getText();
        String gioDi = gioDiInp.getText();
        String soNguoiLon = soNguoiLonInp.getText();
        String soTreEm = soTreEmInp.getText();
        String loaiPhong = loaiPhongCmb.getValue();
        String viTri = viTriCmb.getValue();
        // Kiểm tra nếu không có bộ lọc nào được áp dụng
        if (tenDoan.isEmpty() && ngayDen == null && ngayDi == null && gioDen.isEmpty() && gioDi.isEmpty()
                && soNguoiLon.isEmpty() && soTreEm.isEmpty() && loaiPhong.equals("--- Tất cả ---")
                && viTri.equals("--- Tất cả ---")) {
            restartPage(actionEvent);
            return;
        }
        handleFilter();
    }
    // Xử lý sự kiện khi nhấn nút "restart"
    @FXML
    private void restartPage(ActionEvent actionEvent){
        // Reset các trường nhập liệu
        ngayDenInp.setValue(null);
        ngayDiInp.setValue(null);
        gioDenInp.setText("");
        gioDiInp.setText("");
        soNguoiLonInp.setText("");
        soTreEmInp.setText("");
        loaiPhongCmb.setValue("--- Tất cả ---");
        viTriCmb.setValue("--- Tất cả ---");
        tenDoanInp.setText("");
        isSearchFailed = false;

        // Sử dụng phương thức mới để hiển thị tất cả các phòng
        showAllRooms();
    }
    // xử lý loọc
    private void handleFilter(){
        List<DonDatPhong> filteredList = filterDonDatPhongTheoNgayDenVaNgayDi();
        // khi ngay den > ngay di
        if(isSearchFailed)
            return;
        filteredList = filterDonDatPhongTheoSoNguoiLonVaTreEm(filteredList);
        filteredList = filterDonDatPhongTheoTenDoan(filteredList);
        List<Phong> filteredPhong = new ArrayList<>();
        // neu co don dat phong theo filter thi thay doi trong legendtrong manageTrangThaiPhong
        if(filteredList != null){
            List<DonDatPhong> donDatPhongDangSuDung = new ArrayList<>();
            List<DonDatPhong> donDatPhongDatTruoc = new ArrayList<>();
            for (DonDatPhong d: filteredList){
               List <ChiTietDonDatPhong> ct = new ArrayList<>();
               try {
                   ct = chiTietDonDatPhongDAO.findChiTietDonDatPhongTheoMaDonDatPhong(d.getMaDonDatPhong());
               } catch (RemoteException e) {
                   e.printStackTrace();
                   showErrorAlert("Lỗi", "Không thể lấy danh sách chi tiết đơn đặt phòng: " + e.getMessage());
                   continue;
               }
                filteredPhong.addAll(ct.stream().map(ChiTietDonDatPhong::getPhong).toList());
               if(ct.getFirst().getNgayNhan()==null){
                    donDatPhongDatTruoc.add(d);
                }else if(ct.getFirst().getNgayNhan().isBefore(LocalDateTime.now()) && ct.getFirst().getNgayTra() == null){
                    donDatPhongDangSuDung.add(d);
                }
            }
            manageTrangThaiPhong.putPhongDonDatPhong(donDatPhongDangSuDung, TrangThaiPhong.DANG_SU_DUNG);
            manageTrangThaiPhong.putPhongDonDatPhong(donDatPhongDatTruoc, TrangThaiPhong.DAT_TRUOC);
            allPhong = filteredPhong;
            initLegend();
        }
        if(filterDonDatPhongTheoLoaiPhong()|| filterPhongTheoViTri())
            loadRoomItems();
        else{
            gridPane.getChildren().clear();
            updateEmptyState();
        }
    }
    // reset gridpane, hiển thị lại các phòng or empty
    private void updateEmptyState() {
        boolean hasData = !gridPane.getChildren().isEmpty();

        Node emptyView = EmptyViewManager.getEmptyView();
        emptyView.setVisible(!hasData);
        scrollPane.setVisible(hasData);

        // Đảm bảo thứ tự z-index
        if (!hasData) {
            emptyView.toFront();
        } else {
            scrollPane.toFront();
        }
    }
    // Chuyển đổi ngày và giờ thành LocalDateTime
    private LocalDateTime handleDayAndTime(LocalDate ngay, String gioVaPhut){
        if(ngay == null || gioVaPhut == null || gioVaPhut.isEmpty() )
            return null;
        LocalTime localTime = null;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            localTime = LocalTime.parse(gioVaPhut, timeFormatter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LocalDateTime.of(ngay, localTime);
    }
    private List<DonDatPhong> filterDonDatPhongTheoNgayDenVaNgayDi(){
        LocalDateTime ngayDen = handleDayAndTime(ngayDenInp.getValue(), gioDenInp.getText());
        LocalDateTime ngayDi = handleDayAndTime(ngayDiInp.getValue(), gioDiInp.getText());
        if(ngayDen == null && ngayDi == null)
            return null;
        if(ngayDen== null)
            ngayDen= LocalDateTime.now();
        if(ngayDi== null)
            ngayDi= LocalDateTime.now();
        if(ngayDen.isAfter(ngayDi)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Ngày đi phải sau ngày đến");
            alert.showAndWait();
            isSearchFailed = true;
            return null;
        }
       try {
           return donDatPhongDAO.getListDonDatPhongTheoNgayDenVaNgayDi(ngayDen, ngayDi);
       } catch (RemoteException e) {
           e.printStackTrace();
           showErrorAlert("Lỗi", "Không thể lấy danh sách đơn đặt phòng: " + e.getMessage());
           return new ArrayList<>();
       }

    }
    private List<DonDatPhong> filterDonDatPhongTheoSoNguoiLonVaTreEm( List<DonDatPhong> filtered){

        int soNguoiLon = Integer.parseInt(soNguoiLonInp.getText().isEmpty() ? "0" : soNguoiLonInp.getText());
        int soTreEm = Integer.parseInt(soTreEmInp.getText() .isEmpty() ? "0" : soTreEmInp.getText());
        // neu truoc do search theo ngay den va di thi tim kiem tiep tren filtered
        if(filtered!= null){
            if(soNguoiLon ==0 && soTreEm ==0){
                return null;
            }
            if(soNguoiLon >0 && soTreEm ==0){
                return filtered.stream().filter(d -> d.getSoLuongNguoiLon() == soNguoiLon)
                        .collect(Collectors.toList());
            }
            else if(soNguoiLon ==0 && soTreEm >0){
                return filtered.stream().filter(d -> d.getSoLuongTreEm() == soTreEm)
                        .collect(Collectors.toList());
            }
            else
                return filtered.stream().filter(d -> d.getSoLuongNguoiLon() == soNguoiLon && d.getSoLuongTreEm() == soTreEm)
                        .collect(Collectors.toList());
        }

        // Nếu không có bộ lọc nào được áp dụng trước đó
        if(soNguoiLon ==0 && soTreEm ==0){
            return filtered;
        } else if(soNguoiLon >0 && soTreEm ==0){
            try {
                return donDatPhongDAO.getListDonDatPhongTheoSoNguoiLon(soNguoiLon);
            } catch (RemoteException e) {
                e.printStackTrace();
                showErrorAlert("Lỗi", "Không thể lấy danh sách đơn đặt phòng: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        else if(soNguoiLon ==0 && soTreEm >0){
            try {
                return donDatPhongDAO.getListDonDatPhongTheoSoTreEm(soTreEm);
            } catch (RemoteException e) {
                e.printStackTrace();
                showErrorAlert("Lỗi", "Không thể lấy danh sách đơn đặt phòng: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        else
            try {
                return donDatPhongDAO.getListDonDatPhongTheoSoNguoiLonVaTreEm(soNguoiLon,soTreEm);
            } catch (RemoteException e) {
                e.printStackTrace();
                showErrorAlert("Lỗi", "Không thể lấy danh sách đơn đặt phòng: " + e.getMessage());
                return new ArrayList<>();
            }
    }
    private List<DonDatPhong> filterDonDatPhongTheoTenDoan(List<DonDatPhong> filtered){
        String tenDoan = tenDoanInp.getText();
        // neu truoc do search theo so nguoi lon va tre em thi tim kiem tiep tren filtered
        if(filtered != null){
            if(tenDoan == null || tenDoan.isEmpty())
                return null;
            return filtered.stream().filter(d -> d.getTenDoan().toLowerCase().contains(tenDoan.toLowerCase()))
                    .collect(Collectors.toList());
        }
        // Nếu không có bộ lọc nào được áp dụng trước đó
        if(tenDoan == null || tenDoan.isEmpty())
            return filtered;
        try {
            return donDatPhongDAO.getListDonDatPhongTheoTenDoan(tenDoan);
        } catch (RemoteException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi", "Không thể lấy danh sách đơn đặt phòng: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    private boolean filterDonDatPhongTheoLoaiPhong(){
        String loaiPhong = loaiPhongCmb.getValue();
        if(loaiPhong == null || loaiPhong.equals("--- Tất cả ---"))
            return false;
//        for(int i=allPhong.size()-1;i>=0; i--){
//            Phong phong = allPhong.get(i);
//            if(phong.getLoaiPhong().getTenLoaiPhong().equals(loaiPhong)){
//                allPhong.remove(i);
//                i--;
//            }
//        }
        allPhong = allPhong.stream()
                .filter(p -> p.getLoaiPhong().getTenLoaiPhong().equals(loaiPhong))
                .collect(Collectors.toList());
        return true;
    }
    private boolean filterPhongTheoViTri(){
        String viTri = viTriCmb.getValue();
        if(viTri == null || viTri.equals("--- Tất cả ---"))
            return false;
//        for(int i=allPhong.size()-1;i>=0; i--){
//            Phong phong = allPhong.get(i);
//            if(phong.getViTri().equals(viTri)){
//                allPhong.remove(i);
//                listMaDonDatPhongTheoPhong.remove(i);
//                i--;
//            }
//        }
        allPhong = allPhong.stream()
                .filter(p -> p.getViTri().equals(viTri))
                .collect(Collectors.toList());
        return true;
    }
    // Hiển thị popup thông tin đơn đặt phòng
    private void showDonDatPhongPopup(Node anchorNode) {
        int index = gridPane.getChildren().indexOf(anchorNode);
        if (index < 0) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/InfoDetailBookingRoom.fxml"));
            Parent popupContent = loader.load();

            // Xóa nội dung cũ nếu có
            popup.getContent().clear();
            popup.getContent().add(popupContent);

            // Cập nhật dữ liệu
            InfoDetailBookingRoomController controller = loader.getController();
            Object data = anchorNode.getUserData();
            if (data instanceof ManageTrangThaiPhong.PhongDonDatWrapper wrapper) {
                controller.setData(wrapper.getDonDatPhong());
            } else {
                controller.setData(null);
            }
            // Hiển thị
            Bounds bounds = anchorNode.localToScreen(anchorNode.getBoundsInLocal());
            popup.show(anchorNode.getScene().getWindow(), bounds.getMaxX()-20, bounds.getMinY()+20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initLegend(){
        trongNumber.setText(String.valueOf(manageTrangThaiPhong.getNumberOfPhongByTrangThai(TrangThaiPhong.TRONG)));
        dangDonDepNumber.setText(String.valueOf(manageTrangThaiPhong.getNumberOfPhongByTrangThai(TrangThaiPhong.DANG_DON_DEP)));
        dangSuDungNumber.setText(String.valueOf(manageTrangThaiPhong.getNumberOfPhongByTrangThai(TrangThaiPhong.DANG_SU_DUNG)));
        datTruocNumber.setText(String.valueOf(manageTrangThaiPhong.getNumberOfPhongByTrangThai(TrangThaiPhong.DAT_TRUOC)));
        suaChuaNumber.setText(String.valueOf(manageTrangThaiPhong.getNumberOfPhongByTrangThai(TrangThaiPhong.DANG_SUA_CHUA)));
        khongSudungNumber.setText(String.valueOf(manageTrangThaiPhong.getNumberOfPhongByTrangThai(TrangThaiPhong.KHONG_SU_DUNG)));
    }
    @FXML
    private void handleLegend(MouseEvent event) {
//        if (event.getClickCount() == 2) {
            HBox clickedHBox = (HBox) event.getSource();
            System.out.println("Double clicked on: " + clickedHBox.getId());
            // Bạn có thể so sánh ID hoặc chứa data riêng trong mỗi HBox
            switch (clickedHBox.getId()) {
                case "trongHBox":
                    allPhong = manageTrangThaiPhong.getPhongByTrangThai(TrangThaiPhong.TRONG);
                    break;
                case "dangSuDungHBox":
                    allPhong = manageTrangThaiPhong.getPhongByTrangThai(TrangThaiPhong.DANG_SU_DUNG);
                    break;
                case "dangDonDepHBox":
                    allPhong = manageTrangThaiPhong.getPhongByTrangThai(TrangThaiPhong.DANG_DON_DEP);
                    break;
                case "datTruocHBox":
                    allPhong = manageTrangThaiPhong.getPhongByTrangThai(TrangThaiPhong.DAT_TRUOC);
                    break;
                case "suaChuaHBox":
                    allPhong = manageTrangThaiPhong.getPhongByTrangThai(TrangThaiPhong.DANG_SUA_CHUA);
                    break;
                case "khongSudungHBox":
                    allPhong = manageTrangThaiPhong.getPhongByTrangThai(TrangThaiPhong.KHONG_SU_DUNG);
                    break;
            }
            loadRoomItems();
//        }
    }
    /**
     * Phương thức để hiển thị tất cả các phòng của mọi trạng thái
     */
    @FXML
    public void showAllRooms() {
        // Đảm bảo dữ liệu được cập nhật
        if(manageTrangThaiPhong != null) {
            manageTrangThaiPhong.refreshData();
        } else {
            manageTrangThaiPhong = new ManageTrangThaiPhong(this);
        }

        // Xóa danh sách phòng hiện tại và thêm tất cả các phòng của mọi trạng thái
        allPhong.clear();
        for (TrangThaiPhong trangThai : TrangThaiPhong.values()) {
            allPhong.addAll(manageTrangThaiPhong.getPhongByTrangThai(trangThai));
        }

        // Cập nhật giao diện
        loadRoomItems();
        updateEmptyState();
        initLegend();
    }

    public void closePage(){
        EventBusManager.unregister(this);
        donDatPhongDAO.getEm().close();
        chiTietDonDatPhongDAO.getEm().close();
        phongDAO.closeEntityManager();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

